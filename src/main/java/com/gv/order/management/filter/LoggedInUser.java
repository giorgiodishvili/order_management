package com.gv.order.management.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class LoggedInUser extends User {
    private static final long serialVersionUID = -7098715909304249498L;
    private Long id;

    public LoggedInUser(
            final String username,
            final String password,
            final Collection<? extends GrantedAuthority> authorities,
            final Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public LoggedInUser(
            final String username,
            final String password,
            final boolean enabled,
            final boolean accountNonExpired,
            final boolean credentialsNonExpired,
            final boolean accountNonLocked,
            final Collection<? extends GrantedAuthority> authorities,
            final Long id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }
}
