package com.example.cd;

public class Carte {
    private String question;
    private String reponse;

    public Carte(String question, String reponse) {
        this.question = question;
        this.reponse = reponse;
    }

    public Carte(){
        this.question = "";
        this.reponse = "";
    }

    //getters
    public String getQuestion() {
        return question;
    }
    public String getReponse() {
        return reponse;
    }

    //setters
    public void setQuestion(String question) {
        this.question = question;
    }
    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

}
