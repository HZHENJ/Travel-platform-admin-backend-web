package com.example.backendweb.Repository.User;

import com.example.backendweb.Entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
