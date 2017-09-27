package com.highplace.service.oauth.domain;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.*;

public class OldUser implements UserDetails {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(min = 6, max = 30)
    private String username;

    @NotNull
    @Length(min = 6, max = 40)
    private String password;

    private String mobile_no;
    private String email;
    private String wx_openid;

    private boolean account_expired;
    private boolean account_locked;
    private boolean credential_expired;
    private boolean enabled;

    private RegType reg_type; //注册用户类型,对应RegType枚举,注册用户时需要用到
    private String tenant_id;  //租户ID
    private String instance_id = ""; //实例ID,默认为空

    private Collection<? extends GrantedAuthority> authorities;
    private List<OldRole> oldRoles = new ArrayList<>();

    public OldUser() {
        super();
    }


    public RegType getReg_type() { return reg_type;    }
    public void setReg_type(RegType reg_type) {
        this.reg_type = reg_type;
    }

    public String getTenant_id() {
        return tenant_id;
    }
    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getInstance_id() {
        return instance_id;
    }
    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    private boolean getAccount_expired() {return account_expired; }
    public void setAccount_expired(boolean account_expired) {
        this.account_expired = account_expired;
    }

    private boolean getAccount_locked() {return account_locked; }
    public void setAccount_locked(boolean account_locked) {
        this.account_locked = account_locked;
    }

    private boolean getCredential_expired() {return credential_expired; }
    public void setCredential_expired(boolean credential_expired) {
        this.credential_expired = credential_expired;
    }

    private boolean getEnabled() {return enabled; }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private List<OldRole> getOldRoles() {return oldRoles; }
    public void setOldRoles(List<OldRole> oldRoles) {this.oldRoles = oldRoles; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oldRoles;
        /*
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for ( OldRole role : oldRoles ) {
            list.add(role);

            for ( Operation operation : role.getAllowedOperations()) {
                list.add(operation);
            }
        }
        return list;
        */
    }

    @Override
    public boolean isAccountNonExpired() {
        return !account_expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !account_expired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credential_expired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /*
    @Override
    public String toString() {
        return "MyUserDetails [id=" + id + ", username=" + username
                + ", password=" + password + ", age=" + age + "]";
              //  + ", authorities=" + authorities + "]";
    }
    */

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