package com.taxvoirie.panneau_service.Exception;

public class PanneauNotFoundException extends RuntimeException {
  public PanneauNotFoundException(String message) {
    super(message);
  }
}
