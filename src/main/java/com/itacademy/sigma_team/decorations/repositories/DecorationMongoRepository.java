package com.itacademy.sigma_team.decorations.repositories;


import com.itacademy.sigma_team.domain.Material;
import com.itacademy.sigma_team.decorations.use_cases.DecorationGateway;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.itacademy.sigma_team.dtos.DecorationDTO;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public final class DecorationMongoRepository implements DecorationGateway {

    private final MongoCollection<Document> collection;

    public DecorationMongoRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flower_shop");
        this.collection = database.getCollection("decorations");
    }

    @Override
    public void add(DecorationDTO decorationDTO) {
        Document document = new Document("id", decorationDTO.id())
                .append("name", decorationDTO.name())
                .append("material", decorationDTO.material().name())
                .append("price", decorationDTO.price())
                .append("stock", decorationDTO.stock());
        collection.insertOne(document);
    }

    @Override
    public DecorationDTO get(String decorationId) {
        Bson filter = eq("id", decorationId);
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(DecorationDTO decorationDTO) {
        Bson filter = eq("id", decorationDTO.id());
        collection.deleteOne(filter);
    }
}


