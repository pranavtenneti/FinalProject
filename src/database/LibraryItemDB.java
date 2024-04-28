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
import Server.LibraryItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibraryItemDB {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<LibraryItem> collection;
    private static final String URI = "mongodb+srv://pranavtenneti:UBVpliHrCuPyUU3d@cluster0.9rufjnl.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DB = "library";
    private static final String COLLECTION = "LibraryItems";

    public static void initializeLibraryItemDB(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB).withCodecRegistry(pojoCodecRegistry);
        collection = database.getCollection(COLLECTION, LibraryItem.class);
    }

    public static void initializeItemsInDB(){
        List<LibraryItem> books = Arrays.asList(
                new LibraryItem("Book","1984", "George Orwell", 328, "1984 is a dystopian novel by George Orwell published in 1949. The novel is set in Airstrip One, a world of perpetual war, omnipresent government surveillance, and public manipulation.", new ArrayList<>(),true)
//                new LibraryItem("To Kill a Mockingbird", "Harper Lee", "Classic", 4.7, 2, 4, "Book", 9780061120084L, "To Kill a Mockingbird is a novel by Harper Lee published in 1960. It was immediately successful, winning the Pulitzer Prize, and has become a classic of modern American literature."),
//                new LibraryItem("The Great Gatsby", "F. Scott Fitzgerald", "Classic", 4.5, 5, 6, "Book", 9780743273565L, "The Great Gatsby is a novel by F. Scott Fitzgerald published in 1925. Set in the Jazz Age on Long Island, the novel depicts narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan."),
//                new LibraryItem("Sapiens", "Yuval Noah Harari", "Nonfiction", 4.9, 1, 1, "Book", 9780062316097L,  "Sapiens: A Brief History of Humankind is a book by Yuval Noah Harari first published"),
//                new LibraryItem("Guns, Germs, and Steel", "Jared Diamond", "Nonfiction", 4.2, 0, 1, "Book", 9780393061314L, "Guns, Germs, and Steel: The Fates of Human Societies is a transdisciplinary non-fiction book by Jared Diamond, professor of geography and physiology at the University of California, Los Angeles (UCLA). Published in 1997 by W. W. Norton & Company, the book won the Pulitzer Prize for General Non-Fiction in 1998."),
//                new LibraryItem("In Cold Blood", "Truman Capote", "Nonfiction", 4.6, 1, 2, "Book", 9780679745587L, "In Cold Blood is a non-fiction novel by American author Truman Capote, first published in 1966. It details the 1959 murders of four members of the Herbert Clutter family in the small farming community of Holcomb, Kansas."),
//                new LibraryItem("The Catcher in the Rye", "J.D. Salinger", "Fiction", 4.0, 2, 3, "Book", 9780316769488L, "The Catcher in the Rye is a novel by J. D. Salinger, partially published in serial form in 1945–1946 and as a novel in 1951. It was originally intended for adults but is often read by adolescents for its themes of angst and alienation, and as a critique on superficiality in society."),
//                new LibraryItem("The Hobbit", "J.R.R. Tolkien", "Fiction", 4.8, 2, 100, "Book", 9780547928227L, "The Hobbit, or There and Back Again is a children's fantasy novel by English author J. R. R. Tolkien. It was published on 21 September 1937 to wide critical acclaim, being nominated for the Carnegie Medal and awarded a prize from the New York Herald Tribune for best juvenile fiction."),
//                new LibraryItem("Sherlock Holmes", "Arthur Conan Doyle", "Mystery", 4.9, 5, 5, "Book", 9781853268960L,"Sherlock Holmes is a fictional private detective created by British author Sir Arthur Conan Doyle. Referring to himself as a consulting detective in the stories, Holmes is known for his proficiency with observation, deduction, forensic science, and logical reasoning that borders on the fantastic, which he employs when investigating cases for a wide variety of clients, including Scotland Yard."),
//                new LibraryItem("The Girl with the Dragon Tattoo", "Stieg Larsson", "Mystery", 4.5, 4, 6, "Book", 9780307949486L, "Best Book Forever for Girls")
        );

        collection.insertMany(books);
    }

    public static boolean addLibraryItem(LibraryItem libraryItem){
        collection.insertOne(libraryItem);
        System.out.println("Library Item added "+ libraryItem);
        return true;
    }

    public static List<LibraryItem> getAllLibraryItems(){
        List<LibraryItem> items = new ArrayList<>();
        MongoCursor cursor = collection.find(Filters.empty()).cursor();
        while(cursor.hasNext()) {
            items.add((LibraryItem)cursor.next());
        }
        return items;
    }

    public static boolean getLibraryItem(){
        LibraryItem libraryItem = collection.find(Filters.eq("title", "1984")).first();
        System.out.println(libraryItem);
        return true;
    }
}
