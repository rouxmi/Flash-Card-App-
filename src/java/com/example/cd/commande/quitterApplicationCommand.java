package com.example.cd.commande;

public class quitterApplicationCommand extends Commande {

    public quitterApplicationCommand() {
        super(null, null, null);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}

