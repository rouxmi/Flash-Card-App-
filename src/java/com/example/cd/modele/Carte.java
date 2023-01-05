package com.example.cd.modele;

import com.example.cd.statistiques.StatsCarte;
import com.google.gson.annotations.Expose;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Carte {
    @Expose
    private String question;
    @Expose
    private String reponse;
    @Expose
    private StatsCarte statsCarte;
    @Expose
    private String imageQuestion = "";
    @Expose
    private String imageReponse = "";


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
    public String getImageQuestion() {
        return imageQuestion;
    }
    public String getImageReponse() {
        return imageReponse;
    }


    //setters
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    public void setMediaQuestion(String imageQuestion) {
        this.imageQuestion = imageQuestion;
    }
    public void setMediaReponse(String imageReponse) {
        this.imageReponse = imageReponse;
    }

}
