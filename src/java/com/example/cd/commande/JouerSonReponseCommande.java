package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import javafx.scene.media.AudioClip;

public class JouerSonReponseCommande extends Commande {
    private GlobalControleur globalControleur;

    public JouerSonReponseCommande(GlobalControleur globalControleur) {
        super(null, null, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        AudioClip player = new AudioClip(getClass().getResource(globalControleur.getCarte().getAudioReponse()).toExternalForm());
        player.play();
    }
}
