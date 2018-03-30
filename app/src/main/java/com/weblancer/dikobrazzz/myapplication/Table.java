package com.weblancer.dikobrazzz.myapplication;

public class Table {
    private String firstTime;
    private String secondTime;
    private String notes;
    private int id;
    private String firstClock;
    private String secondClock;

    Table(int id, String fClock, String sClock, String firstTime, String secondTime, String notes){
        this.firstClock = fClock;
        this.secondClock = sClock;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.notes = notes;
        this.id = id;
    }
    public String getFirstClock() {return firstClock;}
    public String getSecondClock() {return secondClock;}
    public String getFirstTimer(){return firstTime;}
    public String getSecondTimer(){return secondTime;}
    public void setNotes(String notes) {this.notes = notes;}
    public String getNotes()            {return notes;}
    public int getId()                  { return id; }
}
