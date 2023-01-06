package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import javafx.scene.media.AudioClip;

public class JouerSonCommande extends Commande {

    GlobalControleur globalControleur;
    String coteCarte;

    public JouerSonCommande(GlobalControleur globalControleur, String cote, Carte carte) {
        super(null, null, carte);
        this.globalControleur = globalControleur;
        this.coteCarte = cote;
    }

    @Override
    public void execute() throws Exception {
        AudioClip player;
        if (coteCarte.equals("question")) {
            player = new AudioClip(getClass().getResource(carte.getAudioQuestion()).toExternalForm());
        } else {
            player = new AudioClip(getClass().getResource(carte.getAudioReponse()).toExternalForm());
        }
        player.play();
    }
}
