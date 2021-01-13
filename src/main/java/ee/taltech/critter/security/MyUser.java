package ee.taltech.critter.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class MyUser extends User {

    private Long id;
    private DbRole dbRole;


    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id, DbRole dbRole) {
        super(username, password, authorities);
        this.id = id;
        this.dbRole = dbRole;
    }
}
