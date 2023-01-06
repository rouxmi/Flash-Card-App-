package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;

public class AllerRevisionCommande extends Commande {

        private GlobalControleur globalControleur;

        public AllerRevisionCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
            super(null, paquet, null);
            this.globalControleur = globalControleur;
        }

        @Override
        public void execute() throws Exception {
            Alert choixTemps = new Alert(Alert.AlertType.INFORMATION);
            choixTemps.setTitle("Choisit la durée du timer");
            choixTemps.setHeaderText("A l'aide du curseur définit ton temps de réponse ");
            Slider curseur = new Slider(3,30,5);
            curseur.setMinorTickCount(3);
            curseur.setMajorTickUnit(5);
            curseur.setShowTickMarks(true);
            curseur.setShowTickLabels(true);
            curseur.setSnapToTicks(true);
            curseur.setBlockIncrement(1);
            choixTemps.getDialogPane().setContent(curseur);
            curseur.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    globalControleur.getPaquet().setDecompte(t1.intValue());
                }
            });
            choixTemps.showAndWait();
            new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
            globalControleur.changeSceneVersEntrainement("revision");
        }

}
