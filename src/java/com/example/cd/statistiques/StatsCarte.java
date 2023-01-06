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

    public void addBeforeReussite(LocalDate date) {
        DateReussite.add(0,date.toString());
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
        return ((double)DateReussite.size() / (double)((DateReussite.size() + DateEchec.size()))) * 100;
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

    public boolean ValidableAujourdhui(){
        if (etatCarte==EtatCarte.ARevoir || etatCarte==EtatCarte.NonVue){
            return true;
        }
        if (etatCarte==EtatCarte.DebutApprentissage){
            if (getLastDateReussite().isBefore(LocalDate.now().minusDays(1))){
                return true;
            }
        }
        if (etatCarte==EtatCarte.FinApprentissage){
            return getLastDateReussite().isBefore(LocalDate.now().minusDays(3));
        }
        return false;
    }


    public void MajStatsCarteReussite() {
        switch (etatCarte) {
            case NonVue, ARevoir -> {
                etatCarte = EtatCarte.DebutApprentissage;
                addDateReussite(LocalDate.now());
            }
            case DebutApprentissage -> {
                if (getLastDateReussite().isBefore(LocalDate.now().minusDays(1))) {
                    etatCarte = EtatCarte.FinApprentissage;
                    addDateReussite(LocalDate.now());
                } else {
                    addBeforeReussite(LocalDate.now());
                }
            }
            case FinApprentissage -> {
                if (getLastDateReussite().isBefore(LocalDate.now().minusDays(3))) {
                    etatCarte = EtatCarte.AcquiseParfaite;
                    addDateReussite(LocalDate.now());
                } else {
                    addBeforeReussite(LocalDate.now());
                }
            }
            case AcquiseParfaite -> addBeforeReussite(LocalDate.now());
        }
    }

    public void MajStatsCarteEchec() {
        switch (etatCarte) {
            case NonVue, DebutApprentissage, ARevoir -> {
                etatCarte = EtatCarte.ARevoir;
                addDateEchec(LocalDate.now());
            }

            case FinApprentissage -> {
                etatCarte = EtatCarte.DebutApprentissage;
                addDateEchec(LocalDate.now());
            }
            case AcquiseParfaite -> {
                etatCarte = EtatCarte.FinApprentissage;
                addDateEchec(LocalDate.now());
            }
        }
    }



}
