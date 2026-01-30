package com.taxvoirie.panneau_service.DTO;

import com.taxvoirie.panneau_service.enums.Etat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PanneauRequest {

  @NotNull(message = "La longueur est obligatoire")
  @Positive(message = "La longueur doit être positive")
  private Float longueur;

  @NotNull(message = "La largeur est obligatoire")
  @Positive(message = "La largeur doit être positive")
  private Float largeur;

  @NotNull(message = "La hauteur est obligatoire")
  @Positive(message = "La hauteur doit être positive")
  private Float hauteur;

  @NotBlank(message = "Le type de panneau est obligatoire")
  private String typePanneau;

  @NotNull(message = "Le prix par jour est obligatoire")
  @PositiveOrZero(message = "Le prix doit être >= 0")
  private Float prixParJour;
  @NotNull(message = "La position X est obligatoire")
  private Float positionX;

  @NotNull(message = "La position Y est obligatoire")
  private Float positionY;

  @NotBlank(message = "La localisation est obligatoire")
  private String localisation;
  private Etat etat;
}

