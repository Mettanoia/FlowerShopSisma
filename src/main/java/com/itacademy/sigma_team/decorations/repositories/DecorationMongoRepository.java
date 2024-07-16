package com.itacademy.sigma_team.decorations.repositories;

import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
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

public final class DecorationMongoRepository implements DecorationGateway {

    private final MongoCollection<Document> collection;

    public DecorationMongoRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flower_shop");
        this.collection = database.getCollection("decorations");
    }

    @Override
    public void add(DecorationDTO dto) throws IOException {
        Document document = new Document("id", dto.id())
                .append("name", dto.name())
                .append("material", dto.material().name())
                .append("price", dto.price())
                .append("stock", dto.stock());
        collection.insertOne(document);
    }

    @Override
    public void delete(DecorationDTO dto) throws IOException {
        Bson filter = eq("id", dto.id());
        collection.deleteOne(filter);
    }

    @Override
    public DecorationDTO get(String id) {
        Bson filter = eq("id", id);
        Document document = collection.find(filter).first();
        if (document != null) {
            return new DecorationDTO(
                    document.getString("id"),
                    document.getString("name"),
                    Material.valueOf(document.getString("material")),
                    document.getDouble("price"),
                    document.getInteger("stock")
            );
        }
        return null;
    }

    @Override
    public Collection<DecorationDTO> getAll() throws IOException {
        Collection<DecorationDTO> decorations = new ArrayList<>();
        for (Document doc : collection.find()) {
            DecorationDTO decoration = new DecorationDTO(
                    doc.getString("id"),
                    doc.getString("name"),
                    Material.valueOf(doc.getString("material")),
                    doc.getDouble("price"),
                    doc.getInteger("stock")
            );
            decorations.add(decoration);
        }
        return decorations;
    }

    // New method to decrement stock
    public void decrementStock(String decorationId, int quantityPurchased) {
        Bson filter = eq("id", decorationId);
        Bson update = new Document("$inc", new Document("stock", -quantityPurchased));
        Document result = collection.findOneAndUpdate(filter, update);
        if (result == null) {
            System.out.println("Not enough stock to decrement for decoration with ID: " + decorationId);
        } else {
            System.out.println("Stock decremented successfully for decoration with ID: " + decorationId);
        }
    }
}



