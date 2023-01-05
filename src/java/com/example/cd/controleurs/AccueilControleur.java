package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.modele.apprentissage.AvancementApprentissage;
import com.example.cd.modele.apprentissage.ClassiqueApprentissage;
import com.example.cd.modele.apprentissage.MasterStrategie;
import com.example.cd.modele.apprentissage.RandomApprentissage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilControleur extends SujetObserve implements Initializable, Observateur {
    private ArrayList<PaquetDeCartes> paquets;

    private GlobalControleur globalControleur;

    private PaquetDeCartes paquetActuel;
    @FXML
    private GridPane table;
    @FXML
    private ToggleButton toggleBouton;
    @FXML
    private Button importPaquet;

    public AccueilControleur(ArrayList<PaquetDeCartes> paquets, GlobalControleur globalControleur){
        this.paquets = paquets;
        this.globalControleur = globalControleur;
        for(int i=0; i< paquets.size();i++){
            paquets.get(i).ajouterObservateur(this);
        }
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creationBoutons();
    }

    @FXML
    public void ajouterNouveauPaquet() throws Exception {
        paquets.add(new PaquetDeCartes());
        paquetActuel = paquets.get(paquets.size()-1);
        majPaquetGlobalControleur(paquetActuel);
        dialogBoxNouveauPaquet();
        if ( !paquetActuel.getTitre().equals("") ) {
            dialogBoxNouvelleDescription();
            globalControleur.changeSceneVersGestion();
        } else {
            globalControleur.supprimerPaquet(paquetActuel);
            majPaquetGlobalControleur(null);
        }
    }

    public void dialogBoxNouveauPaquet() {
        TextInputDialog infoTitre = new TextInputDialog();
        infoTitre.setTitle("Création nouveau paquet");
        infoTitre.setHeaderText("Renseigne le titre");
        infoTitre.showAndWait();
        paquetActuel.setTitre(infoTitre.getEditor().getText());
    }
    public void dialogBoxNouvelleDescription(){
        TextInputDialog infoDescription = new TextInputDialog();
        infoDescription.setTitle("Création nouveau paquet");
        infoDescription.setHeaderText("Renseigne la description");
        infoDescription.showAndWait();
        paquetActuel.setDescription(infoDescription.getEditor().getText());
    }

    @FXML
    public void majToggle() {
        // TODO : verifier que ca part pas quand on remet la toolbar
        if ( toggleBouton.isSelected() ) {
            toggleBouton.setText("Entrainement");
        } else {
            toggleBouton.setText("Gestion");
        }
    }
    @FXML
    public void quitterAppli() {
        // TODO : relier  toolbar quand remi aura fini le responsive
        (new quitterApplicationCommande()).execute();
    }

    @FXML
    public void visiterPaquet() throws Exception{
        majPaquetGlobalControleur(paquetActuel);
        if ( toggleBouton.isSelected() ) {
            globalControleur.changeSceneVersEntrainement("entrainement");
        } else {
            globalControleur.changeSceneVersGestion();
        }
    }
    @FXML
    public void importerPaquet() throws Exception {
        globalControleur.importerPaquets();
        globalControleur.changeSceneVersAccueil();
    }

    public void creationBoutons() {
        int nbBoutons = paquets.size()+1;
        int nbLignes = 0;
        int nbColonnes = 3;

        while (nbColonnes * nbLignes < nbBoutons) {
            nbLignes++;
        }
        if (nbLignes<3) {
            nbLignes = 3;
        }
        if (nbLignes > table.getRowConstraints().size()) {
            for (int j = table.getRowConstraints().size(); j < nbLignes; j++) {
                RowConstraints row = new RowConstraints();
                row.setVgrow(Priority.SOMETIMES);
                row.setMinHeight(250);
                row.setPrefHeight(400);
                table.getRowConstraints().add(row);
            }
        }

        for (int i = 0; i < nbBoutons; i++) {
            Button button;
            button = new Button();

            if (i == nbBoutons - 1) {
                VBox vbox = new VBox();
                Label label = new Label("+");
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-font-size: 50px;");
                vbox.getChildren().add(label);
                vbox.setPadding(new Insets(56, 10, 10, 12));
                vbox.setAlignment(Pos.CENTER);
                button.setGraphic(vbox);
                button.setFont(new javafx.scene.text.Font(60));
                button.setOnAction(event -> {
                    try {
                        ajouterNouveauPaquet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            else {
                String description = paquets.get(i).getDescription();
                Label labeltitre = new Label(paquets.get(i).getTitre());
                labeltitre.setWrapText(true);
                Label labeldesc = new Label(description);
                labeltitre.setFont(new javafx.scene.text.Font(16));
                labeldesc.setFont(new javafx.scene.text.Font(13.5));
                labeldesc.setWrapText(true);
                VBox vBoxhaut = new VBox();
                vBoxhaut.setPadding(new javafx.geometry.Insets(40, 0, 0, 15));
                vBoxhaut.setMaxSize(150, 30);
                vBoxhaut.getChildren().add(labeltitre);
                VBox vBoxbas = new VBox();
                vBoxbas.setMaxSize(270, 185);
                vBoxbas.setPadding(new javafx.geometry.Insets(25, 0, 0, 33));
                vBoxbas.setSpacing(10);
                vBoxbas.getChildren().add(labeldesc);
                VBox vBox = new VBox();
                vBox.getChildren().addAll(vBoxhaut, vBoxbas);
                button.setGraphic(vBox);
                button.getGraphic().setStyle("-fx-position: top-left;");

                int finalI = i;
                button.setOnAction(event -> {
                    try {
                        this.paquetActuel=paquets.get(finalI);
                        visiterPaquet();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
            button.setPrefSize(300, 300);
            button.setMinSize(300,300);
            button.setMaxHeight(1.7976931348623157E308);
            button.setMaxWidth(1.7976931348623157E308);
            button.setMnemonicParsing(false);
            button.setAlignment(Pos.CENTER);


            button.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("folder_icon.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(button.getPrefWidth(), button.getPrefHeight(), false, false, false, false))));
            table.add(button, i % nbColonnes, i / nbColonnes);
        }
        table.setHgap(20);
        table.setVgap(20);
    }

    @FXML
    public void random(){
        for (PaquetDeCartes paquet : paquets) {
            paquet.setApprentissageStrategie(new RandomApprentissage());
        }
    }

    @FXML
    public void classique(){
        for (PaquetDeCartes paquet : paquets) {
            paquet.setApprentissageStrategie(new ClassiqueApprentissage());
        }
    }

    @FXML
    public void avancement(){
        for (PaquetDeCartes paquet : paquets) {
            paquet.setApprentissageStrategie(new AvancementApprentissage());
        }
    }

    @FXML
    public void master(){
        for (PaquetDeCartes paquet : paquets) {
            paquet.setApprentissageStrategie(new MasterStrategie());
        }
    }

    public PaquetDeCartes getPaquetActuel() {
        return paquetActuel;
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setPaquet(paquetActuel);
    }
    public void majCarteGlobalControleur(Carte carteActuelle) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setCarte(carteActuelle);
    }
}
