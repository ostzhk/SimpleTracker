package net.simple_tracker.simpletracker;


public class Outlay {
    int id;
    int count;
    String category;
    String date;

    public Outlay(String category, int count) {
        this.category = category;
        this.count = count;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
