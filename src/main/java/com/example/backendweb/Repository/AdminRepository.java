package com.example.backendweb.Repository;

import com.example.backendweb.Entity.Admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> { // Renamed repository
    Admin findByUsername(String username);
}

