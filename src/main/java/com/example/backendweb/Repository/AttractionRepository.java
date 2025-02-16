package com.example.backendweb.Repository;

import com.example.backendweb.Entity.Info.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction,Integer> {
    boolean existsByUuid(String uuid);
}
