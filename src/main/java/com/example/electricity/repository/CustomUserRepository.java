package com.example.electricity.repository;

import com.example.electricity.models.CustomUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserRepository extends CrudRepository<CustomUser, Long>, JpaSpecificationExecutor<CustomUser> {

    Optional<CustomUser> findByEmail(String email);
    Optional<CustomUser> findByUserId(String userId);
    @Query("select  cu from CustomUser cu where cu.oauthUser.id = ?#{principal.id}")
    Optional<CustomUser> getLoggedInEmployee();

}
