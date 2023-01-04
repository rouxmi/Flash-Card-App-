package com.example.cd.statistiques;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.util.ArrayList;

public class StatsCarte {

    @Expose
    private final ArrayList<String> DateReussite;

    @Expose
    private final ArrayList<String> DateEchec;

    @Expose
    private EtatCarte etatCarte;

    public StatsCarte() {
        DateReussite = new ArrayList<String>();
        DateEchec = new ArrayList<String>();
        etatCarte = EtatCarte.NonVue;
    }

    public void setEtatCarte(EtatCarte etatCarte) {
        this.etatCarte = etatCarte;
    }

    public EtatCarte getEtatCarte() {
        return etatCarte;
    }

    public void addDateReussite(LocalDate date) {
        DateReussite.add(date.toString());
    }

    public void addDateEchec(LocalDate date) {
        DateEchec.add(date.toString());
    }

    public LocalDate getLastDateReussite() {
        if (DateReussite.size() == 0) {
            return null;
        }
        return LocalDate.parse(DateReussite.get(DateReussite.size() - 1));
    }

    public LocalDate getLastDateEchec() {
        if (DateEchec.size() == 0) {
            return null;
        }
        return LocalDate.parse(DateEchec.get(DateEchec.size() - 1));
    }

    public int getNbReussite() {
        return DateReussite.size();
    }

    public int getNbEchec() {
        return DateEchec.size();
    }

    public int getNbEssaie() {
        return DateReussite.size() + DateEchec.size();
    }

    public double getPourcentageReussite() {
        if (DateReussite.size() == 0) {
            return 0;
        }
        return (double) DateReussite.size() / (DateReussite.size() + DateEchec.size()) * 100;
    }

    public double getPourcentageEchec() {
        if (DateEchec.size() == 0) {
            return 0;
        }
        return (double) DateEchec.size() / (DateReussite.size() + DateEchec.size()) * 100;
    }

    public LocalDate getLastDate(){
        LocalDate lastDateReussite = getLastDateReussite();
        LocalDate lastDateEchec = getLastDateEchec();
        if (lastDateReussite == null) {
            return lastDateEchec;
        }
        if (lastDateEchec == null) {
            return lastDateReussite;
        }
        if (lastDateReussite.isAfter(lastDateEchec)) {
            return lastDateReussite;
        }
        return lastDateEchec;
    }


}
