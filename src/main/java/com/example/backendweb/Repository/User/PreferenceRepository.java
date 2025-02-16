package com.example.backendweb.Repository.User;

import com.example.backendweb.Entity.User.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference,Integer> {
}
