package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import javafx.scene.media.AudioClip;

public class JouerSonCommande extends Commande {

    GlobalControleur globalControleur;
    String coteCarte;

    public JouerSonCommande(GlobalControleur globalControleur, String cote) {
        super(null, null, null);
        this.globalControleur = globalControleur;
        this.coteCarte = cote;
    }

    @Override
    public void execute() throws Exception {
        AudioClip player;
        if (coteCarte.equals("question")) {
            player = new AudioClip(getClass().getResource(globalControleur.getCarte().getAudioQuestion()).toExternalForm());
        } else {
            player = new AudioClip(getClass().getResource(globalControleur.getCarte().getAudioReponse()).toExternalForm());
        }
        player.play();
    }
}
