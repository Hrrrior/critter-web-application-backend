package ee.taltech.critter.service.users;

import ee.taltech.critter.security.JwtTokenProvider;
import ee.taltech.critter.security.MyUser;
import ee.taltech.critter.service.users.dto.LoginDto;
import ee.taltech.critter.service.users.dto.LoginResponse;
import ee.taltech.critter.service.users.exception.UserException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginDto loginDto) {
        if (isBlank(loginDto.getUsername())) {
            throw new UserException("missing username");
        }
        if (isBlank(loginDto.getPassword())) {
            throw new UserException("missing password");
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        MyUser myUser = (MyUser) authenticate.getPrincipal(); //it is UserDetails and in our case it is MyUser
        String token = jwtTokenProvider.generateToken(myUser);
        return LoginResponse.builder()
                .username(myUser.getUsername())
                .token(token)
                .role(myUser.getDbRole())
                .build();

    }
}
