package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreationControleur extends GlobalControleur{
    private PaquetDeCartes paquet;
    private int indice;
    public CreationControleur(PaquetDeCartes paquet){
        this.paquet=paquet;
        this.indice=0;
        paquet.ajouterObservateur(this);
    }
    public CreationControleur(PaquetDeCartes paquet,int indice){
        this.paquet=paquet;
        this.indice=indice;
        paquet.ajouterObservateur(this);
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
