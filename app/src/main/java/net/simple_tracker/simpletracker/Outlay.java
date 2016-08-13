package net.simple_tracker.simpletracker;


public class Outlay {
    int id;
    int count;
    String categoryName;
    String date;

    public Outlay(String categoryName, String date, int count) {
        this.categoryName = categoryName;
        this.count = count;
        this.date = date;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
