package com.project.jarjamediaapp.Models;

public class GetTransaction {


    private String sno;
    private String name;
    private String date;
    private boolean isDone;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }


    public GetTransaction(String sno, String name, String date,boolean isDone) {

        this.sno = sno;
        this.name = name;
        this.date = date;
        this.isDone=isDone;
    }

}
