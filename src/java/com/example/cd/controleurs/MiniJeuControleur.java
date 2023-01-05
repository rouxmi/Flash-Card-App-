package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MiniJeuControleur extends SujetObserve implements Initializable, Observateur {

    private PaquetDeCartes paquet;
    private GlobalControleur globalControleur;

    private Queue<Carte> cartes;

    private boolean question = false;
    private boolean reponse = false;


    @FXML
    private GridPane grille;

    public MiniJeuControleur(PaquetDeCartes paquet, GlobalControleur globalControleur) {
        this.paquet = paquet;
        this.globalControleur = globalControleur;
        this.cartes = new LinkedList<>();
        Carte c = paquet.getApprentissageStrategie().getCarte(this.paquet, cartes);
        this.cartes.add(c);
        for (int i = 0; i < 7; i++) {
            this.cartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, cartes));
        }
        paquet.ajouterObservateur(this);
    }

    @FXML
    public void quitterAppli() {
        (new quitterApplicationCommande()).execute();
    }

    @FXML
    public void allerAccueil() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersAccueil();
    }

    @FXML
    public void voirPaquet() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersGestion();
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setPaquet(paquetActuel);
    }

    @Override
    public void reagir() {
        //get the two RadioButtons in the grille witch are selected and compare them
        for (Carte c : paquet.getCartes()) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    RadioButton rb = (RadioButton) grille.getChildren().get(i * 4 + j);
                    if (rb.isSelected()) {
                        if (Objects.equals(rb.getText(), c.getQuestion())) {
                            question = true;
                        }
                        if(Objects.equals(rb.getText(), c.getReponse())){
                            reponse = true;
                        }
                    }
                }
            }
            if(question && reponse){
                break;
            }
            else{
                question = false;
                reponse = false;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                RadioButton rb = (RadioButton) grille.getChildren().get(i * 4 + j);
                if (rb.isSelected()) {
                    if(question && reponse){
                        rb.setVisible(false);
                    }
                    else{
                        question = false;
                        reponse = false;
                    }
                }
            }
        }
        question = false;
        reponse = false;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int random;
        ArrayList<Integer> randomDejaTombes = new ArrayList<>();
        ArrayList<Carte> listeCartes = new ArrayList<>(cartes);
        ToggleGroup groupQ = new ToggleGroup();
        ToggleGroup groupR = new ToggleGroup();
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                do{
                    random = (int)(Math.random() * 16);
                }while(randomDejaTombes.contains(random));
                randomDejaTombes.add(random);
                RadioButton Bouton = new RadioButton();
                Bouton.setId(""+random);
                if(random-8 >= 0){
                    Bouton.setText(listeCartes.get(random%8).getQuestion());
                    Bouton.setToggleGroup(groupQ);
                }else{
                    Bouton.setText(listeCartes.get(random%8).getReponse());
                    Bouton.setToggleGroup(groupR);
                }
                Bouton.setOnAction(actionEvent -> reagir());
                Bouton.setPrefSize(200, 150);
                Bouton.setPadding(new Insets(10, 10, 10, 10));
                grille.setPadding(new Insets(10, 10, 10, 10));
                grille.add(Bouton, i, j);}
        }

    }
}
