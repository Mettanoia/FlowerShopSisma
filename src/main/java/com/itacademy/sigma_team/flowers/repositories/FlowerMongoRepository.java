package com.itacademy.sigma_team.flowers.repositories;

import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.flowers.use_cases.FlowerGateway;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;

public final class FlowerMongoRepository implements FlowerGateway {

    private final MongoCollection<Document> collection;

    public FlowerMongoRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flower_shop");
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
    public Collection<FlowerDTO> getAll() throws IOException {
        Collection<FlowerDTO> flowers = new ArrayList<>();
        for (Document doc : collection.find()) {
            FlowerDTO flower = new FlowerDTO(
                    doc.getString("id"),
                    doc.getString("name"),
                    doc.getString("color"),
                    doc.getDouble("price"),
                    doc.getInteger("stock")
            );
            flowers.add(flower);
        }
        return flowers;
    }

    // New method to decrement stock
    public void decrementStock(String flowerId, int quantityPurchased) {
        Bson filter = eq("id", flowerId);
        Bson update = new Document("$inc", new Document("stock", -quantityPurchased));
        Document result = collection.findOneAndUpdate(filter, update);
        if (result == null) {
            System.out.println("Not enough stock to decrement for flower with ID: " + flowerId);
        } else {
            System.out.println("Stock decremented successfully for flower with ID: " + flowerId);
        }
    }
}

