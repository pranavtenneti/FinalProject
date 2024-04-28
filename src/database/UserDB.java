package database;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import Server.User;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import Server.User;

import java.sql.SQLOutput;

public class UserDB {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<User> collection;
    private static final String URI = "mongodb+srv://pranavtenneti:UBVpliHrCuPyUU3d@cluster0.9rufjnl.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "Users";

    public static void initializeUserDB(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, User.class);
    }

    public static void addUser(User user){
        MongoCursor cursor = collection.find(Filters.empty()).cursor();
        while(cursor.hasNext()) {
            if(collection.find(Filters.eq("username", user.getUsername())).first() != null && collection.find(Filters.eq("password", user.getPassword())).first() != null){
                //System.out.println("user added "+ user);
                System.out.println("User already exists");
                return;
            }
        }
        collection.insertOne(user);
        System.out.println("user added");
    }

    public static boolean getAllUsers(){
        MongoCursor cursor = collection.find(Filters.empty()).cursor();
        while(cursor.hasNext()) {
            System.out.println("ITERATING: " + (User)cursor.next());
        }
        return true;
    }

    public static boolean getUser(String username){

        User user = collection.find(Filters.eq("username", username)).first();
        System.out.println(user);
        return true;
    }

    public static void main(String[] args) {
        /*
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, Item.class);

        //CREATE
        Item newItem = new Item("Adidas Ultraboost", "this one is also cool", "", 0.0);
        collection.insertOne(newItem);

        //FIND AND READ
        Item item = collection.find(Filters.eq("name", "Adidas Ultraboost")).first();
        System.out.println(item);

        //UPDATE
        item.setName("Adidas Megaboost");
        collection.findOneAndReplace(Filters.eq("name", "Adidas Ultraboost"), item);

        //READ ALL
        MongoCursor cursor = collection.find(Filters.empty()).cursor();
        while(cursor.hasNext()) {
            System.out.println("ITERATING: " + ((Item)cursor.next()).toString());
        }
        /*
        //DELETE
        /*Document filterByItemId = new Document("_id", item.getId());
        collection.deleteOne(filterByItemId);*/

        //mongo.close();
    }

}
