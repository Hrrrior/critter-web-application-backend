package ee.taltech.critter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration // this marks this class as configuration, so spring knows to look for config
@EnableWebSecurity//(debug = true) // enables the whole thing, turn on debug to see filters applied to your requests
@EnableGlobalMethodSecurity(securedEnabled = true) //this makes spring use @Secured
public class SecurityConfig extends WebSecurityConfigurerAdapter { //WebSecurityConfigurerAdapter is the main spring security class you implement

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    private static final String[] SWAGGER_URLS = {
// -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() //cross site request forgery, it's a must if we use cookies
                .headers().httpStrictTransportSecurity().disable()
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .headers().frameOptions().disable() // h2
                .and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
//                this is for url based security
                .antMatchers("/").permitAll()
                .antMatchers("/users/register").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/genres").permitAll()
                .antMatchers("/platforms").permitAll()
                .antMatchers("/h2-console/**").permitAll() // h2
                .antMatchers(SWAGGER_URLS).permitAll()
                .antMatchers(HttpMethod.GET, "/games/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/games/**").hasRole("ADMIN")
                .antMatchers("/igdb").hasRole("ADMIN")
                .antMatchers("/reviews").hasRole("USER")
                .antMatchers("/reviews").hasRole("ADMIN")
                .anyRequest().fullyAuthenticated()
        ; //if this is not disabled your https frontend must have https (not http) on backend;
    }

    /**
     * password encoder, bcrypt is one of the algorithms
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * authentication manager is used as entrance to creating authentication
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
