package com.taxvoirie.panneau_service.Controller;

import com.taxvoirie.panneau_service.DTO.PanneauDto;
import com.taxvoirie.panneau_service.DTO.PanneauRequest;
import com.taxvoirie.panneau_service.Service.PanneauService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/panneaux")
@RequiredArgsConstructor
@Tag(name = "Panneau API", description = "API pour la gestion des panneaux")

public class PanneauController {
  private final PanneauService panneauService;


  @GetMapping
  @Operation(summary = "Récupérer tous les panneaux")
  public Flux<PanneauDto> getAllPanneaux(@RequestParam (required = false) String keyword) {
    if (keyword != null && !keyword.isEmpty()) {
      return panneauService.searchPanneau(keyword);
    }
    return panneauService.getAllPanneaux();
  }
  @GetMapping("/{id}")
  @Operation(summary = "Récupérer un panneau par son ID")
  public Mono<PanneauDto> getPanneauById(@PathVariable Long id) {
    return panneauService.getPanneauById(id);
  }
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Créer un nouveau panneau")
  public Mono<PanneauDto> createPanneau(@Valid @RequestBody PanneauRequest panneauRequest) {
    return panneauService.createPanneau(panneauRequest);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Mettre à jour un panneau existant")
  public Mono<PanneauDto> updatePanneau(@PathVariable Long id, @Valid @RequestBody PanneauRequest panneauRequest) {
    return panneauService.updatePanneau(id,panneauRequest);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Supprimer un Panneau")
  public Mono<Void> deletePanneau(@PathVariable Long id) {
    return panneauService.deletePanneau(id);
  }
}

