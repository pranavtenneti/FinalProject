import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbPojoExample {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<Item> collection;
    private static final String URI = "mongodb+srv://pranavtenneti:NIE5mgpw10onHkw1@cluster0.9rufjnl.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "auction";
    private static final String COLLECTION = "items";

    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, Item.class);

        //CREATE
        Item newItem = new Item(dController.Username, "this one is also cool", "", 0.0);
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

        //DELETE
        //Document filterByItemId = new Document("_id", item.getId());
        //collection.deleteOne(filterByItemId);

        mongo.close();
    }

}
