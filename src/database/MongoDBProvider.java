package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoDBProvider {


    private final String databaseName = "Test";
    private final String collectionName = "test";

    private final MongoCollection<Person> collection;
    private static MongoDBProvider instance;

    public static MongoDBProvider getInstance() {
        if (instance == null) {
            instance = new MongoDBProvider();
        }
        return instance;
    }

    protected MongoDBProvider() {

        collection = new MongoClient(new MongoClientURI(MongoDBKey.MongoURI))
                .getDatabase(databaseName)
                .withCodecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())))
                .getCollection(collectionName, Person.class);//подключаем провайдер для работы с pojo моделями
        System.out.println("Connected");
    }

    protected MongoDBProvider(String databaseName, String collectionName) {
        collection = new MongoClient(new MongoClientURI(MongoDBKey.MongoURI))
                .getDatabase(databaseName)
                .withCodecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())))
                .getCollection(collectionName, Person.class);//подключаем провайдер для работы с pojo моделями
        System.out.println("Connected");
    }

    public void insertPersonData(Person person) {
        this.collection.insertOne(person);
    }

    public Person findInDatabaseByID(int ID) {
        return collection.find(Filters.eq("tagID", ID)).first();
    }

    public boolean editBalanceByID(int ID, int balance) {
        Person oldPerson = collection.find(Filters.eq("tagID", ID)).first();
        if (oldPerson != null) {
            oldPerson.setBalance(balance);
            collection.findOneAndReplace(Filters.eq("tagID", ID), oldPerson);
            return true;
        } else {
            return false;
        }
    }

    public boolean editNameByID(int ID, String name) {
        Person oldPerson = collection.find(Filters.eq("tagID", ID)).first();
        if (oldPerson != null) {
            oldPerson.setName(name);
            collection.findOneAndReplace(Filters.eq("tagID", ID), oldPerson);
            return true;
        } else {
            return false;
        }
    }

    public void deletePerson(int ID) {
        collection.findOneAndDelete(Filters.eq("tagID", ID));
    }
}
