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
    private String mediaQuestion;
    @Expose
    private String mediaReponse;

    public Carte(String question, String reponse) {
        this.question = question;
        this.reponse = reponse;
        statsCarte = new StatsCarte();
    }
    public Carte(){
        this.question = "";
        this.reponse = "";
        statsCarte = new StatsCarte();
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
    public String getMediaQuestion() {
        return mediaQuestion;
    }
    public String getMediaReponse() {
        return mediaReponse;
    }


    //setters
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    public void setMediaQuestion(String mediaQuestion) {
        this.mediaQuestion = mediaQuestion;
    }
    public void setMediaReponse(String mediaReponse) {
        this.mediaReponse = mediaReponse;
    }

}
