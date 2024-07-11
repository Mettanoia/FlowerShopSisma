package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;

final class FlowerMongoRepository implements FlowerGateway {

    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public FlowerMongoRepository() {
        this.database = MongoDatabaseConnection.getDatabase();
        this.collection = database.getCollection("flowers");
    }

    @Override
    public void add(FlowerDTO dto) throws IOException {
        Document document = new Document("id", dto.id())
                .append("name", dto.name())
                .append("color", dto.color())
                .append("price", dto.price())
                .append("stock", dto.stock());
        collection.insertOne(document);
    }

    @Override
    public void delete(FlowerDTO dto) throws IOException {
        Bson filter = eq("id", dto.id());
        collection.deleteOne(filter);
    }

    @Override
    public FlowerDTO get(String id) {
        Bson filter = eq("id", id);
        Document document = collection.find(filter).first();
        if (document != null) {
            return new FlowerDTO(
                    document.getString("id"),
                    document.getString("name"),
                    document.getString("color"),
                    document.getDouble("price"),
                    document.getInteger("stock")
            );
        }
        return null;
    }

    @Override
    public Collection<FlowerDTO> getAll() {
        Collection<FlowerDTO> flowers = new ArrayList<>();
        for (Document document : collection.find()) {
            FlowerDTO flower = new FlowerDTO(
                    document.getString("id"),
                    document.getString("name"),
                    document.getString("color"),
                    document.getDouble("price"),
                    document.getInteger("stock")
            );
            flowers.add(flower);
        }
        return flowers;
    }
}
