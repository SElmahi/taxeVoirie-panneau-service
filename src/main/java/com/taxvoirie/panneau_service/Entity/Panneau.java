package com.taxvoirie.panneau_service.Entity;

import com.taxvoirie.panneau_service.enums.Etat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("panneau")
public class Panneau {

  @Id
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
  private Etat etat;

 //  mise a jour auto la surface
  public void setLongueur(Float longueur) {
    this.longueur = longueur;
    updateSurface();
  }
  public void setLargeur(Float largeur) {
    this.largeur = largeur;
    updateSurface();
  }
  private void updateSurface() {
    if (this.longueur != null && this.largeur != null) {
      this.surface = this.longueur * this.largeur;
    }
  }
}
