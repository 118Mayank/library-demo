package com.demo.library.repository;

import com.demo.library.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails,Long> {
    Optional<UserDetails> findByMobile(String mobile);
}
