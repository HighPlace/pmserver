package com.highplace.service.oauth.domain;

import com.highplace.service.oauth.JpaTemplate.Operation;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String tenant_id;
    private String instance_id;

    public Role(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String getAuthority() {
        return name;
    }

    /*
    @OneToMany(fetch = FetchType.EAGER)
    private List<Operation> allowedOperations = new ArrayList<>();
    */

    //public void setAllowedOperations( List<Operation> allowedOperations ) { this.allowedOperations = allowedOperations; }
    //public List<Operation> getAllowedOperations() {        return allowedOperations;    }
}
