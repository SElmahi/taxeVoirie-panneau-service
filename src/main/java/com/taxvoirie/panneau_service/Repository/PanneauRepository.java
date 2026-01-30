package com.taxvoirie.panneau_service.Repository;

import com.taxvoirie.panneau_service.Entity.Panneau;
import com.taxvoirie.panneau_service.enums.Etat;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PanneauRepository extends R2dbcRepository<Panneau, Long> {
  Flux<Panneau> findByLocalisationContainingIgnoreCase(String localisation);
  // ici etat est un enum donc ContainingIgnoreCase ne marche que sur les champs String
  Flux<Panneau> findByEtat(Etat etat);

}
