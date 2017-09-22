package com.highplace.service.oauth.JpaTemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<JpaUser, Long> {

    JpaUser findByUsername(String username);

    JpaUser findByUsernameAndPassword(String username, String password);

    @Query("from JpaUser u where u.username=:username")
    JpaUser findUser(@Param("username") String name);

}
