package com.peslayk.repository;

import com.peslayk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);

    public User findByEmail(String email);

    public User findByEmailAndPhoneNumber(String email, String phoneNumber);
    public long countByRole(String roleUser);
}

