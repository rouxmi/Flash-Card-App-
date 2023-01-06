package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import com.example.cd.statistiques.StatsCarte;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class CopierCarteCommande extends Commande {

    private GlobalControleur globalControleur;
    private Carte carte;

    public CopierCarteCommande(GlobalControleur globalControleur) {
        super(null, null, null);
        this.globalControleur = globalControleur;
        this.carte = carte;
    }

    @Override
    public void execute() throws Exception {
        Alert choixPaquet = new Alert(Alert.AlertType.INFORMATION);
        choixPaquet.setTitle("Vers quels paquets copier ?");
        choixPaquet.setHeaderText(null);
        ObservableList<String> paquets = FXCollections.observableArrayList();
        for(int i=0; i<globalControleur.getPaquets().size();i++){
            paquets.add(globalControleur.getPaquets().get(i).getTitre());
        }
        ListView<String> listView= new ListView<>(paquets);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                for(int i=0; i<paquets.size();i++){
                    if(t1.equals(paquets.get(i))){
                        Carte carteAColler = globalControleur.getCarte();
                        carteAColler.setStatsCarte(new StatsCarte());
                        globalControleur.getPaquets().get(i).ajouterCarte(carteAColler);
                    }
                }
            }
        });
        choixPaquet.getDialogPane().setContent(listView);
        choixPaquet.showAndWait();
    }

}
