package com.ads.web.reclamos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ads.web.reclamos.entities.Reclamo;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
}

