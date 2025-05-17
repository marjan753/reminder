package com.example.reminder;

public class ReminderNormal {
    private int id;
    private String description;
    private String date;
    private String dateGregorian;
    private String time;
<<<<<<< HEAD
    private int kindofrepeat;
    private int numberrepeat;
    private int repeat_id;
=======
>>>>>>> 77aff04ef85fb5482cb1dc970a147d9a39d00221




    public ReminderNormal(String description,String date,String dateGregorian,String time) {

        this.description = description;
        this.date=date;
        this.time=time;
        this.dateGregorian=dateGregorian;
    }



<<<<<<< HEAD
    public ReminderNormal(String description2, String date2, String dateGregorian2, String time2, int repeat_id2, int kindofrepeat2, int numberrepeat2) {
        this.description = description2;
        this.date = date2;
        this.time = time2;
        this.dateGregorian = dateGregorian2;
        this.repeat_id = repeat_id2;
        this.numberrepeat = numberrepeat2;
        this.kindofrepeat = kindofrepeat2;
    }





=======
>>>>>>> 77aff04ef85fb5482cb1dc970a147d9a39d00221
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


<<<<<<< HEAD
    public int getKindofrepeat() {
        return kindofrepeat;
    }

    public void setKindofrepeat(int kindofrepeat) {
        this.kindofrepeat = kindofrepeat;
    }

    public int getNumberrepeat() {
        return numberrepeat;
    }

    public void setNumberrepeat(int numberrepeat) {
        this.numberrepeat = numberrepeat;
    }

    public int getRepeat_id() {
        return repeat_id;
    }

    public void setRepeat_id(int repeat_id) {
        this.repeat_id = repeat_id;
    }
=======

>>>>>>> 77aff04ef85fb5482cb1dc970a147d9a39d00221
}

