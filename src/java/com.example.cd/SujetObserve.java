package com.example.cd;

import java.util.ArrayList;

public class SujetObserve {
    private ArrayList<Observateur> observateurs;

    public SujetObserve(){
        this.observateurs=new ArrayList<>();
    }

    public void ajouterObservateur(Observateur observateur){
        observateurs.add(observateur);
    }

    public void notifierObservateur(){
        for(int i=0; i<observateurs.size(); i++) {
            observateurs.get(i).reagir();
        }
    }

}
