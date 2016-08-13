package net.simple_tracker.simpletracker;


public class Category {
    int id, icon;
    String categoryName;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Category(String categoryName, int icon) {
        this.categoryName = categoryName;
        this.icon = icon;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
