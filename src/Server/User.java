package Server;

import java.io.Serializable;

import database.Item;
import org.bson.types.ObjectId;

import java.util.*;
public class User implements Serializable {
    private String username;
    private String password;
    private List<Item> items;

    public User(){}
    public User(String username, String password, List<Item> list){
        this.username=username;
        this.password=password;
        items = list;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public List<Item> getItems(){
        return items;
    }
    public void setItems(List<Item> items){
        this.items=items;
    }


    @Override
    public String toString() {
        return username+" "+password+" "+items.toString();
    }


}
