package com.example.cd.statistiques;

public class StatsPaquet {
    private int nbCartes;
    private int nbCartesApprises;
    private int nbCartesNonApprises;
    private int nbCartesEnCours;
    private int nbCartesNonVues;

    public StatsPaquet( int nbCartes, int nbCartesApprises, int nbCartesNonApprises, int nbCartesEnCours, int nbCartesNonVues) {
        this.nbCartes = nbCartes;
        this.nbCartesApprises = nbCartesApprises;
        this.nbCartesNonApprises = nbCartesNonApprises;
        this.nbCartesEnCours = nbCartesEnCours;
        this.nbCartesNonVues = nbCartesNonVues;
    }


    public int getNbCartes() {
        return nbCartes;
    }

    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }

    public int getNbCartesApprises() {
        return nbCartesApprises;
    }

    public void setNbCartesApprises(int nbCartesApprises) {
        this.nbCartesApprises = nbCartesApprises;
    }

    public int getNbCartesNonApprises() {
        return nbCartesNonApprises;
    }

    public void setNbCartesNonApprises(int nbCartesNonApprises) {
        this.nbCartesNonApprises = nbCartesNonApprises;
    }

    public int getNbCartesEnCours() {
        return nbCartesEnCours;
    }

    public void setNbCartesEnCours(int nbCartesEnCours) {
        this.nbCartesEnCours = nbCartesEnCours;
    }

    public int getNbCartesNonVues() {
        return nbCartesNonVues;
    }

    public void setNbCartesNonVues(int nbCartes) {
        this.nbCartesNonVues = nbCartes;
    }
}
