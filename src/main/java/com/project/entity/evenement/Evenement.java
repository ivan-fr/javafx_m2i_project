package com.project.entity.evenement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing an event.
 * Contains common properties like name, date, location, and categories.
 */
public abstract class Evenement {
    private int id;
    private String nom;
    private LocalDateTime date;
    private String lieu;
    private int organisateurId;
    private List<CategoryPlace> categories = new ArrayList<>();

    /**
     * Constructor to initialize an event.
     * 
     * @param nom            Name of the event.
     * @param date           Date and time of the event.
     * @param lieu           Location of the event.
     * @param organisateurId ID of the organizer.
     */
    public Evenement(String nom, LocalDateTime date, String lieu, int organisateurId) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.organisateurId = organisateurId;
    }

    /**
     * Returns the event ID.
     * 
     * @return The event ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the event ID.
     * 
     * @param id The new event ID.
     */
    public void setId(int id) {
        this.id = id;
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
     * Sets the event name.
     * 
     * @param nom The new name of the event.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Returns the event date.
     * 
     * @return The date and time of the event.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the event date.
     * 
     * @param date The new date and time.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Returns the event location.
     * 
     * @return The location of the event.
     */
    public String getLieu() {
        return lieu;
    }

    /**
     * Sets the event location.
     * 
     * @param lieu The new location.
     */
    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    /**
     * Returns the organizer's ID.
     * 
     * @return The ID of the organizer.
     */
    public int getOrganisateurId() {
        return organisateurId;
    }

    /**
     * Sets the organizer's ID.
     * 
     * @param organisateurId The new organizer ID.
     */
    public void setOrganisateurId(int organisateurId) {
        this.organisateurId = organisateurId;
    }

    /**
     * Returns the list of seat categories.
     * 
     * @return A list of CategoryPlace objects.
     */
    public List<CategoryPlace> getCategories() {
        return categories;
    }

    /**
     * Sets the list of seat categories.
     * 
     * @param categories The new list of categories.
     */
    public void setCategories(List<CategoryPlace> categories) {
        this.categories = categories;
    }

    /**
     * Adds a new category to the event.
     * 
     * @param c The category to add.
     */
    public void ajouterCategorie(CategoryPlace c) {
        categories.add(c);
    }

    /**
     * Finds a category by its name.
     * 
     * @param nom The name of the category.
     * @return The CategoryPlace object if found, otherwise null.
     */
    public CategoryPlace getCategorieParNom(String nom) {
        return categories.stream().filter(c -> c.getCategorie().equals(nom)).findFirst().orElse(null);
    }

    /**
     * Returns a string representation of the event.
     * Used for display in lists.
     * 
     * @return A string with name, date, and location.
     */
    @Override
    public String toString() {
        // Texte qui sera affiché dans la ListView
        return nom + " - " + date.toLocalDate() + " - " + lieu;
    }

}
