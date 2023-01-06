package com.example.cd.modele;

import com.example.cd.statistiques.StatsCarte;
import com.google.gson.annotations.Expose;

public class Carte {
    @Expose
    private String question;
    @Expose
    private String reponse;
    @Expose
    private StatsCarte statsCarte;
    @Expose
    private String imageQuestion;
    @Expose
    private String audioQuestion;


    public Carte(String question, String reponse) {
        this.question = question;
        this.reponse = reponse;
        statsCarte = new StatsCarte();
        this.imageQuestion = "";
        this.audioQuestion = "";
    }
    public Carte(){
        this.question = "";
        this.reponse = "";
        statsCarte = new StatsCarte();
        this.imageQuestion = "";
        this.audioQuestion = "";

    }

    //getters
    public String getQuestion() {
        return question;
    }
    public String getReponse() {
        return reponse;
    }
    public StatsCarte getStatsCarte() {
        return statsCarte;
    }
    public String getImageQuestion() {
        return imageQuestion;
    }

    public String getAudioQuestion() {
        return audioQuestion;
    }


    //setters
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    public void setImageQuestion(String imageQuestion) {
        this.imageQuestion = imageQuestion;
    }

    public void setAudioQuestion(String audioQuestion) {
        this.audioQuestion = audioQuestion;
    }

    public void setStatsCarte(StatsCarte statsCarte) {this.statsCarte = statsCarte;}
}
