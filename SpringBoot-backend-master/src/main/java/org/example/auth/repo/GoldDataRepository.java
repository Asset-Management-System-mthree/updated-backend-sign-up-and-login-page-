package org.example.auth.repo;

import org.example.auth.entity.GoldData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldDataRepository extends JpaRepository<GoldData, Long> {
}
