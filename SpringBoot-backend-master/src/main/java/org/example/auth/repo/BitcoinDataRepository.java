package org.example.auth.repo;

import org.example.auth.entity.BitcoinData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitcoinDataRepository extends JpaRepository<BitcoinData, Long> {

}
