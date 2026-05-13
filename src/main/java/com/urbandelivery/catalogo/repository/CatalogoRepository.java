package com.urbandelivery.catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.urbandelivery.catalogo.entity.Catalogo;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
}
