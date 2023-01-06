package com.example.cd.commande;

public class QuitterApplicationCommande extends Commande {

    public QuitterApplicationCommande() {
        super(null, null, null);
    }

    @Override
    public void execute() {System.exit(0);
    }
}

