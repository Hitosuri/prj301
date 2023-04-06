package dal;

public class Category {

    private int categoryID;
    private String categoryName;
    private String description;
    private byte[] picture;

    public Category(int categoryID, String categoryName, String description, byte[] picture) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
        this.picture = picture;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryID=" + categoryID + ", categoryName=" + categoryName + ", description=" + description + ", picture=" + picture + '}';
    }

}
