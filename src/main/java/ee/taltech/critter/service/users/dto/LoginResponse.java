package ee.taltech.critter.service.users.dto;

import ee.taltech.critter.security.DbRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String username;
    private String token;
    private DbRole role;
}
