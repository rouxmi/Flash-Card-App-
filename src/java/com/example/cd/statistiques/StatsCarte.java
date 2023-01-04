package com.example.cd.statistiques;

import java.util.ArrayList;
import java.util.Date;

public class StatsCarte {
    private ArrayList<String> DateReussite;
    private ArrayList<String> DateEchec;
    private EtatCarte etatCarte;

    public StatsCarte() {
        DateReussite = new ArrayList<String>();
        DateEchec = new ArrayList<String>();
        etatCarte = EtatCarte.NonVue;
    }
}
