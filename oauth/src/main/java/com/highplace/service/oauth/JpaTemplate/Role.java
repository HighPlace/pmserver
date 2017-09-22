package com.highplace.service.oauth.JpaTemplate;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
class Role implements GrantedAuthority {
    @Id
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Operation> allowedOperations = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name;
    }

    public Role(){}

    public void setAllowedOperations( List<Operation> allowedOperations ) { this.allowedOperations = allowedOperations; }


    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Operation> getAllowedOperations() {
        return allowedOperations;
    }
}
