package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.*;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.modele.apprentissage.FreeApprentissage;
import com.example.cd.modele.apprentissage.ClassiqueApprentissage;
import com.example.cd.modele.apprentissage.MasterStrategie;
import com.example.cd.modele.apprentissage.RandomApprentissage;
import com.example.cd.statistiques.EtatCarte;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AccueilControleur extends SujetObserve implements Initializable, Observateur {

    private ArrayList<PaquetDeCartes> paquets;
    private GlobalControleur globalControleur;
    private PaquetDeCartes paquetActuel;
    @FXML
    private SplitMenuButton tagMenu;
    @FXML
    private GridPane table;
    @FXML
    private ToggleButton toggleBouton;
    @FXML
    private Button importPaquet;

    @FXML
    private RadioMenuItem nb;

    @FXML
    private RadioMenuItem avancee;
    private ArrayList<String> tagsselectionnes = new ArrayList<String>();

    private Boolean colormode = false;
    private boolean nbmode;


    public AccueilControleur(ArrayList<PaquetDeCartes> paquets, GlobalControleur globalControleur){
        this.paquets = paquets;
        this.globalControleur = globalControleur;
        for(int i=0; i< paquets.size();i++){
            paquets.get(i).ajouterObservateur(this);
        }
    }


    @Override
    public void reagir() {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creationsmenu();
        creationBoutons();
    }

    private void creationsmenu() {
        ToggleGroup group = new ToggleGroup();
        nb.setToggleGroup(group);
        avancee.setToggleGroup(group);
        ArrayList<String> tags = new ArrayList<>();
        for (PaquetDeCartes paquet : paquets) {
            if (paquet.getTag() != null && !paquet.getTag().equals("")) {
                for (String tag : paquet.getlistTags()) {
                    String tagtemp;
                    tagtemp=paquet.getTag();
                    if (!tags.contains(tagtemp)) {
                        tags.add(tag);
                        tagsselectionnes.add(tag);
                    }
                }
            }
        }
        tags.add("Sans tag");
        tagsselectionnes.add("Sans tag");
        Menu menu = new Menu("Par Tags");
        for (String tag : tags) {
            RadioMenuItem item = new RadioMenuItem(tag);
            item.setText(tag);
            item.setSelected(true);
            item.setId(tag);
            item.setOnAction(event -> {
                if (tagsselectionnes.contains(tag)) {
                    tagsselectionnes.remove(tag);
                } else {
                    tagsselectionnes.add(tag);
                }
                table.getChildren().clear();
                creationBoutons();
            });
            menu.getItems().add(item);
        }
        menu.getStyleClass().add("tagmenu");
        tagMenu.getItems().add(menu);
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
        int nbajoute = 0;
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
                button.setPrefSize(300, 300);
                button.setMinSize(300, 300);
                button.setMaxHeight(1.7976931348623157E308);
                button.setMaxWidth(1.7976931348623157E308);
                button.setMnemonicParsing(false);
                button.setAlignment(Pos.CENTER);


                button.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/folder_icon.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(button.getPrefWidth(), button.getPrefHeight(), false, false, false, false))));
                table.add(button, nbajoute % nbColonnes, nbajoute / nbColonnes);
                nbajoute++;
            } else {
                if (checkifpaquetintaglist(paquets.get(i))) {
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
                            this.paquetActuel = paquets.get(finalI);
                            visiterPaquet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    button.setPrefSize(300, 300);
                    button.setMinSize(300, 300);
                    button.setMaxHeight(1.7976931348623157E308);
                    button.setMaxWidth(1.7976931348623157E308);
                    button.setMnemonicParsing(false);
                    button.setAlignment(Pos.CENTER);

                    String path;
                    if (colormode) {
                        path = getimagepath(paquets.get(i).getEtatMoyenPaquet());
                    }
                    else {
                        path= "utiles/folder_icon.png";
                    }
                    button.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image(path), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(button.getPrefWidth(), button.getPrefHeight(), false, false, false, false))));
                    table.add(button, nbajoute % nbColonnes, nbajoute / nbColonnes);
                    nbajoute++;
                }
            }
        }
        table.setHgap(20);
        table.setVgap(20);
    }
    public PaquetDeCartes getPaquetActuel() {
        return paquetActuel;
    }

    // FXML boutons fonctions
    @FXML
    public void ajouterNouveauPaquet() throws Exception {
        (new AjouterPaquetCommande(globalControleur)).execute();
    }
    @FXML
    public void quitterAppli() throws Exception {
        (new QuitterApplicationCommande()).execute();
    }
    @FXML
    public void visiterPaquet() throws Exception{
        if (toggleBouton.isSelected()) {
            (new AllerEntrainementCommande(globalControleur, paquetActuel)).execute();
        } else {
            (new AllerGestionCommande(globalControleur, paquetActuel)).execute();
        }
    }
    @FXML
    public void Partager() throws Exception {
        new PartagerCommande().execute();
    }
    @FXML
    public void importerPaquet() throws Exception {
        (new ImporterCommande(globalControleur, paquets)).execute();
    }
    // TODO : try to command pattern this
    @FXML
    public void majToggle() {
        if ( toggleBouton.isSelected() ) {
            toggleBouton.setText("Entrainement");
        } else {
            toggleBouton.setText("Gestion");
        }
    }
    // TODO : try to strategy pattern this
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
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Choisissez le pourcentage d'apparition des cartes");
        VBox vBox = new VBox();
        for(int i=0; i<5; i++){
            HBox hBox = new HBox();
            Slider slider = new Slider(1, 100, 20);
            slider.setId("slider "+EtatCarte.values()[i].toString());
            slider.setShowTickMarks(true);
            Label value = new Label("20");
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                value.setText(String.format("%.0f", newValue));
            });
            Label label = new Label(EtatCarte.values()[i].toString());
            label.setPrefWidth(150);
            hBox.getChildren().add(label);
            hBox.getChildren().add(slider);
            hBox.getChildren().add(value);
            vBox.getChildren().add(hBox);
        }
        DoubleBinding sumBinding = ((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).valueProperty().add(((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).valueProperty())
                .add(((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).valueProperty()).add(((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).valueProperty())
                .add(((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).valueProperty());

        sumBinding.addListener((obs, oldValue, newValue) -> {
            double sum = newValue.doubleValue();
            if (sum > 100) {
                double excess = sum - 100;
                ((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getValue() - excess / 5);
            }

        });

        dialog.getDialogPane().setContent(vBox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.showAndWait();

        double nonVue = ((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getValue();
        double debutApprentissage = ((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).getValue();
        double aRevoir = ((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).getValue();
        double finApprentissage = ((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).getValue();
        double acquiseParfaite = ((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getValue();
        for (PaquetDeCartes paquet : paquets) {
            paquet.setApprentissageStrategie(new FreeApprentissage(nonVue, debutApprentissage, aRevoir, finApprentissage, acquiseParfaite));
        }
    }
    @FXML
    public void master(){
        for (PaquetDeCartes paquet : paquets) {
            paquet.setApprentissageStrategie(new MasterStrategie());
        }
    }

    @FXML
    public void triercouleur(){
        colormode = !colormode;
        nbmode = false;
        if(colormode) {
            paquets.sort(Comparator.comparing(PaquetDeCartes::getEtatMoyenPaquet));
        }
        else{
            avancee.setSelected(false);
            paquets.sort(Comparator.comparing(PaquetDeCartes::getTitre));
        }
        table.getChildren().clear();
        creationBoutons();
    }

    private boolean checkifpaquetintaglist(PaquetDeCartes paquet){
        if (paquet.getTag() == null){
            return tagsselectionnes.contains("Sans tag");
        }
        if (paquet.getTag().equals("")){
            return tagsselectionnes.contains("Sans tag");
        }
        for (String tag : paquet.getlistTags()) {
            if (tagsselectionnes.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    private String getimagepath(int i){
        String base="utiles/folder_icon";
        if (0<=i && i<=4) {
            return base + Integer.toString(i) + ".png";
        }
        return base + ".png";
    }

    @FXML
    public void triernb(){
        nbmode = !nbmode;
        colormode = false;
        if(nbmode) {
            paquets.sort(Comparator.comparing(PaquetDeCartes::taillePaquet));
        }
        else{
            nb.setSelected(false);
            paquets.sort(Comparator.comparing(PaquetDeCartes::getTitre));
        }
        table.getChildren().clear();
        creationBoutons();
    }
}
