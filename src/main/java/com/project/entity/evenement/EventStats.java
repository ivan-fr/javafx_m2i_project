package com.project.entity.evenement;

import java.util.Map;

/**
 * Statistics for an event.
 * Tracks sales, revenue, and attendance.
 */
public class EventStats {
    private int eventId;
    private String nom;
    private int totalPlaces;
    private int totalVendus;
    private double chiffreAffairesTotal;

    private Map<String, Integer> ventesParCategorie;
    private Map<String, Integer> capaciteParCategorie;

    /**
     * Constructor to initialize statistics.
     * 
     * @param evenementId     The event ID.
     * @param nom             The event name.
     * @param totalPlaces     Total available seats.
     * @param totalVendus     Total tickets sold.
     * @param chiffreAffaires Total revenue generated.
     */
    public EventStats(int evenementId, String nom, int totalPlaces, int totalVendus, double chiffreAffaires) {
        this.eventId = evenementId;
        this.nom = nom;
        this.totalPlaces = totalPlaces;
        this.totalVendus = totalVendus;
        this.chiffreAffairesTotal = chiffreAffaires;
    }

    /**
     * Returns the event ID.
     * 
     * @return The event ID.
     */
    public int getEvenementId() {
        return eventId;
    }

    /**
     * Returns the event name.
     * 
     * @return The name of the event.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Returns the total number of places.
     * 
     * @return Total capacity.
     */
    public int getTotalPlaces() {
        return totalPlaces;
    }

    /**
     * Returns the total number of tickets sold.
     * 
     * @return Total sales count.
     */
    public int getTotalVendus() {
        return totalVendus;
    }

    /**
     * Returns the total revenue.
     * 
     * @return Total revenue.
     */
    public double getChiffreAffaires() {
        return chiffreAffairesTotal;
    }

    /**
     * Calculates the filling rate as a percentage.
     * 
     * @return The percentage of seats sold.
     */
    public double getTauxRemplissage() {
        if (totalPlaces == 0)
            return 0;
        return ((double) totalVendus / totalPlaces) * 100.0;
    }

    /**
     * Returns sales per category.
     * 
     * @return A map of category name to sales count.
     */
    public Map<String, Integer> getVentesParCategorie() {
        return ventesParCategorie;
    }

    /**
     * Sets sales per category.
     * 
     * @param ventesParCategorie Map of sales per category.
     */
    public void setVentesParCategorie(Map<String, Integer> ventesParCategorie) {
        this.ventesParCategorie = ventesParCategorie;
    }

    /**
     * Returns capacity per category.
     * 
     * @return A map of category name to capacity.
     */
    public Map<String, Integer> getCapaciteParCategorie() {
        return capaciteParCategorie;
    }

    /**
     * Sets capacity per category.
     * 
     * @param capaciteParCategorie Map of capacity per category.
     */
    public void setCapaciteParCategorie(Map<String, Integer> capaciteParCategorie) {
        this.capaciteParCategorie = capaciteParCategorie;
    }
}