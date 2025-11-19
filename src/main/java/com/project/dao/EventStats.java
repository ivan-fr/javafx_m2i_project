package com.project.dao;

import java.util.Map;

public class EventStats {
    private int eventId;
    private String nom;
    private int totalPlaces;
    private int totalVendus;
    private double chiffreAffairesTotal;
    
    private Map<String, Integer> ventesParCategorie;
    private Map<String, Integer> capaciteParCategorie;
    
    public EventStats(int evenementId, String nom, int totalPlaces, int totalVendus, double chiffreAffaires) {
        this.eventId = evenementId;
        this.nom = nom;
        this.totalPlaces = totalPlaces;
        this.totalVendus = totalVendus;
        this.chiffreAffairesTotal = chiffreAffaires;
    }

    public int getEvenementId() {
        return eventId;
    }

    public String getNom() {
        return nom;
    }

    public int getTotalPlaces() {
        return totalPlaces;
    }

    public int getTotalVendus() {
        return totalVendus;
    }

    public double getChiffreAffaires() {
        return chiffreAffairesTotal;
    }

    public double getTauxRemplissage() {
        if (totalPlaces == 0) return 0;
        return ((double) totalVendus / totalPlaces) * 100.0;
    }

    public Map<String, Integer> getVentesParCategorie() {
        return ventesParCategorie;
    }

    public void setVentesParCategorie(Map<String, Integer> ventesParCategorie) {
        this.ventesParCategorie = ventesParCategorie;
    }

    public Map<String, Integer> getCapaciteParCategorie() {
        return capaciteParCategorie;
    }

    public void setCapaciteParCategorie(Map<String, Integer> capaciteParCategorie) {
        this.capaciteParCategorie = capaciteParCategorie;
    }
}