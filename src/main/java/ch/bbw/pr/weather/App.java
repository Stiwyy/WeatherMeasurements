package ch.bbw.pr.weather;
import ch.bbw.pr.weather.model. Measurement;
import ch.bbw.pr.weather.model. Station;
import ch.bbw.pr.weather.model.WeatherMeasurement;
import com.mongodb.client.*;
import org.bson. Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo. PojoCodecProvider;
import java.util.Date;
import java.util.List;
import java.util.function. Consumer;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 *
 * @author Andrin Renggli
 * @version 7.1.2024
 */

//Funktioniert wieder fast gleich wie die anderen Varianten.
//Der Unterschied liegt darin, dass wir hier POJOs verwenden.
//POJOs sind Measurement.java, Station.Java, WeatherMeasurement.java also das was wir schon kennen.
public class App {
    public static void main(String[] args) {
        System.out.println("Hello Weather");

        //Formatiert POJOs richtig für MongoDB
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("ch.bbw.pr.weather.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        String connectionString = "mongodb://root:1234@localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);

        System.out.println("List all databases: ");
        mongoClient.listDatabases().forEach((Consumer<? super Document>)
                result -> System.out.println(result.toJson()));

        WeatherMeasurement weatherMeasurement = new WeatherMeasurement();
        weatherMeasurement.setType("Wettermessung");
        weatherMeasurement.setTimestamp(new Date().toString());

        Station station = new Station();
        station.setCity("Winterthur");
        station.setPlz("8400");
        weatherMeasurement.setStation(station);

        Measurement m1 = new Measurement();
        m1.setKind("temperature");
        m1.setValue(20.1);
        Measurement m2 = new Measurement();
        m2.setKind("windspeed");
        m2.setValue(2.3);
        weatherMeasurement.setMeasurements(List.of(m1, m2));

        MongoDatabase statisticDB = mongoClient.getDatabase("weathermeasuredb");
        MongoCollection<Document> statisticCollection = statisticDB.getCollection("measurements");

        statisticCollection = statisticCollection.withCodecRegistry(pojoCodecRegistry);

        Document doc = new Document();
        doc.append("weatermeasurement", weatherMeasurement);
        statisticCollection.insertOne(doc);
        mongoClient.close();

    }
}