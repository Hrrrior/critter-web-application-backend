package ee.taltech.critter.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig { //external config for jwt

    private String secret;
    private int durationMin = 40;

    public int getDurationMillis() {
        return durationMin * 60 * 1000;
    }
}
