package com.example.reminder;

public class ReminderNormal {
    private int id;
    private String description;
    private String date;
    private String dateGregorian;
    private String time;




    public ReminderNormal(String description,String date,String dateGregorian,String time) {

        this.description = description;
        this.date=date;
        this.time=time;
        this.dateGregorian=dateGregorian;
    }



    public ReminderNormal(int id,String description,String date,String dateGregorian,String time) {

        this.description = description;
        this.date=date;
        this.time=time;
        this.dateGregorian=dateGregorian;
    }





    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateGregorian() {
        return dateGregorian;
    }

    public void setDateGregorian(String dateGregorian) {
        this.dateGregorian = dateGregorian;
    }



}

