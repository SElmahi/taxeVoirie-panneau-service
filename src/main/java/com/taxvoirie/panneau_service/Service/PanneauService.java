package com.taxvoirie.panneau_service.Service;


import com.taxvoirie.panneau_service.DTO.PanneauDto;
import com.taxvoirie.panneau_service.DTO.PanneauRequest;
import com.taxvoirie.panneau_service.Entity.Panneau;
import com.taxvoirie.panneau_service.Exception.PanneauNotFoundException;
import com.taxvoirie.panneau_service.Repository.PanneauRepository;
import com.taxvoirie.panneau_service.enums.Etat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PanneauService {
  private final PanneauRepository panneauRepository;


  //---------------------------------------------
  public Flux<PanneauDto> getAllPanneaux() {
    return panneauRepository.findAll()
       .map(this::mapToDto);
  }

  //--------------------------------------------------

  public Mono<PanneauDto> getPanneauById(Long id) {
    return panneauRepository.findById(id)
       .map(this::mapToDto)
       .switchIfEmpty(Mono.error(new PanneauNotFoundException("Panneau non trouvé avec l'ID: " + id)));

  }

  //-----------------------------------------------------

  public Mono<PanneauDto> createPanneau(PanneauRequest panneauRequest) {
    Panneau panneau = mapToEntity(panneauRequest);
    return panneauRepository.save(panneau).map(this::mapToDto);
  }
  //--------------------------------------------------
 public Mono<PanneauDto> updatePanneau(Long id, PanneauRequest panneauRequest) {
    return panneauRepository.findById(id)
       .switchIfEmpty(Mono.error(new PanneauNotFoundException("Panneau non trouvé avec l'ID: " + id)))
       .flatMap(existingPanneau -> {
         Panneau updatedPanneau = mapToEntity(panneauRequest);
         updatedPanneau.setId(existingPanneau.getId());
         return panneauRepository.save(updatedPanneau);
       })
       .map(this::mapToDto);
 }
  //--------------------------------------------------
  public Mono<Void> deletePanneau(Long id) {
    return panneauRepository.findById(id)
       .switchIfEmpty(Mono.error(new PanneauNotFoundException("Panneau non trouvé avec l'ID: " + id)))
       .flatMap(panneau -> panneauRepository.delete(panneau));
  }
  //-------------------------------------------------
  public Flux<PanneauDto> searchPanneau(String keyword) {
    if(keyword == null || keyword.isEmpty()) {
      return getAllPanneaux();
    }
    // Recherche par localisation
    Flux<Panneau> byLocalisation = panneauRepository.findByLocalisationContainingIgnoreCase(keyword);

    // Recherche par état
    Flux<Panneau> byEtat = Flux.empty();
    try {
      Etat etat = Etat.valueOf(keyword.toUpperCase()); // conversion String -> Enum
      byEtat = panneauRepository.findByEtat(etat);
    } catch (IllegalArgumentException e) {
      //keyword ne correspond pas à (DISPONIBLE ou OCCUPE) on ignore
    }

    return Flux.concat(byLocalisation, byEtat)
       .distinct(Panneau::getId)
       .map(this::mapToDto);
  }
  //-------------------------------------------------
private PanneauDto mapToDto(Panneau panneau) {
    return PanneauDto.builder()
       .id(panneau.getId())
       .longueur(panneau.getLongueur())
       .largeur(panneau.getLargeur())
       .hauteur(panneau.getHauteur())
       .surface(panneau.getSurface())
       .typePanneau(panneau.getTypePanneau())
       .prixParJour(panneau.getPrixParJour())
       .positionX(panneau.getPositionX())
       .positionY(panneau.getPositionY())
       .localisation(panneau.getLocalisation())
       .etat(panneau.getEtat()).build();
}
private Panneau mapToEntity(PanneauRequest request) {
  Float surface = null;
  if (request.getLongueur() != null && request.getLargeur() != null) {
    surface = request.getLongueur() * request.getLargeur();
  }
    return Panneau.builder()
       .longueur(request.getLongueur())
       .largeur(request.getLargeur())
       .hauteur(request.getHauteur())
       .surface(surface)
       .typePanneau(request.getTypePanneau())
       .prixParJour(request.getPrixParJour())
       .positionX(request.getPositionX())
       .positionY(request.getPositionY())
       .localisation(request.getLocalisation())
       .etat(request.getEtat())
       .build();
}
}
