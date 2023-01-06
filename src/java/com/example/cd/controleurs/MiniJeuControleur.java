package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.AllerAccueilCommande;
import com.example.cd.commande.AllerGestionCommande;
import com.example.cd.commande.QuitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.modele.apprentissage.FreeApprentissage;
import com.example.cd.modele.apprentissage.MiniJeuApprentissage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MiniJeuControleur extends SujetObserve implements Initializable, Observateur {

    private PaquetDeCartes paquet;
    private GlobalControleur globalControleur;
    private Queue<Carte> cartes;
    private boolean question = false;
    private boolean reponse = false;
    private MiniJeuApprentissage strategie;
    private int cmpInvisible;
    private long startTime;

    @FXML
    private GridPane grille;


    public MiniJeuControleur(PaquetDeCartes paquet, GlobalControleur globalControleur) {
        this.cmpInvisible = 0;
        this.paquet = paquet;
        this.globalControleur = globalControleur;
        this.cartes = new LinkedList<>();
        strategie = new MiniJeuApprentissage();
        Carte c = strategie.getCarte(this.paquet, cartes);
        this.cartes.add(c);
        for (int i = 0; i < 7; i++) {
            this.cartes.add(strategie.getCarte(this.paquet, cartes));
        }
        paquet.ajouterObservateur(this);
    }

    @Override
    public void reagir(){
        AudioClip player = new AudioClip(getClass().getResource("/utiles/select.wav").toExternalForm());
        player.play();
        for (Carte c : paquet.getCartes()) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    ToggleButton rb = (ToggleButton) grille.getChildren().get(i * 4 + j);
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
                cmpInvisible++;
                break;
            }
            else{
                question = false;
                reponse = false;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ToggleButton rb = (ToggleButton) grille.getChildren().get(i * 4 + j);
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
        if(cmpInvisible==8){
            Alert victoire = new Alert(Alert.AlertType.INFORMATION);
            victoire .setTitle("Victoire !");
            long endTime = TimeUnit.SECONDS.convert((System.nanoTime()-startTime), TimeUnit.NANOSECONDS);
            victoire.setContentText("Victoire en "+ endTime+ " s");
            victoire.setHeaderText(null);
            victoire.showAndWait();
            victoire.close();
            try {
                voirPaquet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTime = System.nanoTime();
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
                ToggleButton Bouton = new ToggleButton();
                Bouton.setId(""+random);
                Bouton.getStyleClass().add("toggle-button1");
                if(random-8 >= 0){
                    Bouton.setText(listeCartes.get(random%8).getQuestion());
                    Bouton.setToggleGroup(groupQ);
                }else{
                    Bouton.setText(listeCartes.get(random%8).getReponse());
                    Bouton.setToggleGroup(groupR);
                }
                Bouton.setOnAction(actionEvent -> reagir());
                Bouton.setPrefSize(175, 150);
                Bouton.setPadding(new Insets(10, 10, 10, 10));
                grille.setMargin(Bouton, new Insets(10, 10, 10, 10));
                grille.add(Bouton, i, j);}
        }

    }

    // FXML Boutons Fonctions
    @FXML
    public void quitterAppli() {
        (new QuitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        new AllerAccueilCommande(globalControleur, paquet).execute();
    }
    @FXML
    public void voirPaquet() throws Exception {
        new AllerGestionCommande(globalControleur, paquet).execute();
    }
}
