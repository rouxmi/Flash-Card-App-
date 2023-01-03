package com.example.cd.commande;

public class quitterApplicationCommande extends Commande {

    public quitterApplicationCommande() {
        super(null, null, null);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}

