package com.highplace.service.oauth.JpaTemplate;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class JpaUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private boolean expired = false;

    @Column
    private boolean locked = false;

    @Column
    private boolean credential_expired = false;

    @OneToMany
    //private final List<Role> roles = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();

    public JpaUser(){}

    public JpaUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Role> getRoles() {return this.roles; }

    public void setRoles( List<Role> roles ) { this.roles = roles; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        for ( Role role : roles ) {

            list.add(role);
            for ( Operation operation : role.getAllowedOperations()) {

                list.add(operation);
            }
        }
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credential_expired;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}