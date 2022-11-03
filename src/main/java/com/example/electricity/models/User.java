package com.example.electricity.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "oauth_user")
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = -4744753522696645871L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Column(name = "account_expired")
    private boolean accountExpired;

    @Column(name = "account_locked")
    private boolean accountLocked;

    @Column(name = "credentials_expired")
    private boolean credentialsExpired;

    @Column(name = "enabled")
    private boolean enabled;


    /*@JoinTable can be used to map following associations to database table:
    bidirectional many-to-one/one-to-many,
    unidirectional many-to-one,
    and one-to-one (both bidirectional and unidirectional) associations.
    @JoinTable(name = "oauth_user_authorities",
            joinColumns = @JoinColumn(name = "user_id"//oauth_user_authorities.user_id,
            referencedColumnName = "id"//oauth_user.id),
    inverseJoinColumns = @JoinColumn(name = "authority_id"//oauth_user_authorities.authority_id,
    referencedColumnName = "id"//oauth_authority.id))*/

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "oauth_user_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    @OrderBy
    private Collection<Authority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}