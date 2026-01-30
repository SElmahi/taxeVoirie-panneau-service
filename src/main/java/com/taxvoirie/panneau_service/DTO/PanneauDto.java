package com.taxvoirie.panneau_service.DTO;
import com.taxvoirie.panneau_service.enums.Etat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PanneauDto {
  private Long id;
  private Float longueur;
  private Float largeur;
  private Float hauteur;
  private Float surface;
  private String typePanneau;
  private Float prixParJour;
  private Float positionX;
  private Float positionY;
  private String localisation;

  private Etat etat; // "disponible" ou "occupe"
}
