package Server;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LibraryItem implements Serializable {
    private String type;
    private String title;
    private String author;

    private int numPages;
    private String description;
    private ArrayList<User> checkedOutBy;
    private boolean isAvailable;

    public LibraryItem(){}
    public LibraryItem(String type, String title, String author, int numPages, String description, ArrayList<User> checkedOutBy, boolean isAvailable){
        this.type=type;
        this.title=title;
        this.author=author;
        this.numPages=numPages;
        this.description=description;
        this.checkedOutBy=checkedOutBy;
        this.isAvailable=isAvailable;
    }

    public String getType() {
        return type;
    }

    public void setType(String newType){
        type=newType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle){
        title=newTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String newAuthor){
        author=newAuthor;
    }

    public int getNumPages() {
        return numPages;
    }

    public void setNumPages(int newNumPages){
        numPages=newNumPages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription){
        description=newDescription;
    }


    public ArrayList<User> getCheckedOutBy() {
        return checkedOutBy;
    }

    public void setCheckedOutBy(ArrayList<User> newCheckedOutBy){
        checkedOutBy=newCheckedOutBy;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean newIsAvailable){
        isAvailable=newIsAvailable;
    }


    @Override
    public String toString(){
        return "Title: "+title+" Author: "+author;
    }
}
