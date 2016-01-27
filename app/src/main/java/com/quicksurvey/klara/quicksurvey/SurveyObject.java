package com.quicksurvey.klara.quicksurvey;

public class SurveyObject {

    private int treppe;
    private int wolle;
    private long id;


    public SurveyObject(int treppe, int wolle, long id) {
        this.treppe = treppe;
        this.wolle = wolle;
        this.id = id;
    }


    public int getTreppe() {
        return treppe;
    }

    public void setTreppe(int treppe) {
        this.treppe = treppe;
    }


    public int getWolle() {
        return wolle;
    }

    public void setWolle(int wolle) {
        this.wolle = wolle;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    @Override
    public String toString() {
        String output;
        if(wolle == 0) {
            if (treppe == 0) {
                output = "nichts gewusst";
            } else {
                output = "nur Modell bekannt";
            }
        }else{
            if(treppe == 0){
                output = "nur Microskopbild bekannt";
            }else{
                output = "alles gewusst!";
            }
        }

        return output;
    }
}