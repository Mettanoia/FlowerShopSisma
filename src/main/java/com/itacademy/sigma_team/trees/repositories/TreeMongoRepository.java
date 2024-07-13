package com.itacademy.sigma_team.trees.repositories;


import com.itacademy.sigma_team.gateways.TreeGateway;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public final class TreeMongoRepository implements TreeGateway {

    private final MongoCollection<Document> collection;

    public TreeMongoRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flower_shop");
        this.collection = database.getCollection("trees");
    }

    @Override
    public void addTree(TreeDTO treeDTO) {
        Document document = new Document("id", treeDTO.id())
                .append("species", treeDTO.species())
                .append("height", treeDTO.height())
                .append("price", treeDTO.price());
        collection.insertOne(document);
    }

    @Override
    public TreeDTO getTree(Long treeId) {
        Bson filter = eq("id", treeId);
        Document document = collection.find(filter).first();
        if (document != null) {
            return new TreeDTO(Math.toIntExact(document.getLong("id")),
                    document.getString("species"),
                    document.getDouble("height"),
                    document.getDouble("price"));
        }
        return null;
    }

    @Override
    public void deleteTree(TreeDTO treeDTO) {
        Bson filter = eq("id", treeDTO.id());
        collection.deleteOne(filter);
    }

}




