package com.kodnest.universityOTPproject.repositories;

import com.kodnest.universityOTPproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityUserRepository extends JpaRepository<User, Integer>  {
    User findByEmail(String name);
}
