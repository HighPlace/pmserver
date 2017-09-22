package com.highplace.service.oauth.domain;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.*;

public class User implements UserDetails {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private Long id;

    @NotNull
    @Length(min = 3, max = 20)
    private String username;

    @NotNull
    @Length(min = 6, max = 40)
    private String password;

    private Integer age;

    //private Collection<? extends GrantedAuthority> authorities;

    public User() {
        super();
    }

    public User(String username, String password, Integer age) {
        super();
        this.username = username;
        this.password = password;
        this.age = age;

    }

    public User(Long id, String username, String password, Integer age) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public User(Long id, String username, String password, Integer age,
                Collection<UserRole> authorities) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //public Collection<UserRole> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority a  = new SimpleGrantedAuthority("ADMIN");
        list.add(a);

        return list;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "MyUserDetails [id=" + id + ", username=" + username
                + ", password=" + password + ", age=" + age + "]";
              //  + ", authorities=" + authorities + "]";
    }

}
/*
class GrantedAuthorityComparators implements Comparator<GrantedAuthority> {

    public static final Comparator<GrantedAuthority> DEFAULT = new GrantedAuthorityComparators();

    public static final Comparator<GrantedAuthority> REVERSE = new Comparator<GrantedAuthority>() {
        public int compare(GrantedAuthority o1, GrantedAuthority o2) {
            return - DEFAULT.compare(o1, o2);
        }
    };

    private GrantedAuthorityComparators() { super(); }

    public int compare(GrantedAuthority g1, GrantedAuthority g2) {
        return g1.getAuthority().compareTo(g2.getAuthority());
    }
}
*/