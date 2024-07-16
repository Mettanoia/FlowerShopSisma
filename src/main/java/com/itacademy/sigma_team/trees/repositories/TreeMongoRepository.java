package com.itacademy.sigma_team.trees.repositories;

import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.trees.use_cases.TreeGateway;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;

public class TreeMongoRepository implements TreeGateway {

    private final MongoCollection<Document> collection;

    public TreeMongoRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flower_shop");
        this.collection = database.getCollection("trees");
    }

    @Override
    public void add(TreeDTO treeDTO) {
        Document document = new Document("id", treeDTO.id())
                .append("name", treeDTO.name())
                .append("height", treeDTO.height())
                .append("price", treeDTO.price())
                .append("stock", treeDTO.stock()); // Include stock field
        collection.insertOne(document);
    }

    @Override
    public TreeDTO get(String treeId) {
        Bson filter = eq("id", treeId);
        Document document = collection.find(filter).first();
        if (document != null) {
            return new TreeDTO(
                    document.getString("id"),
                    document.getString("name"),
                    document.getDouble("height"),
                    document.getDouble("price"),
                    document.getInteger("stock") // Include stock field
            );
        }
        return null;
    }

    @Override
    public Collection<TreeDTO> getAll() {
        Collection<TreeDTO> trees = new ArrayList<>();
        for (Document doc : collection.find()) {
            TreeDTO tree = new TreeDTO(
                    doc.getString("id"),
                    doc.getString("name"),
                    doc.getDouble("height"),
                    doc.getDouble("price"),
                    doc.getInteger("stock") // Include stock field
            );
            trees.add(tree);
        }
        return trees;
    }

    @Override
    public void delete(TreeDTO treeDTO) {
        Bson filter = eq("id", treeDTO.id());
        collection.deleteOne(filter);
    }

    // New method to decrement stock
    public void decrementStock(String treeId, int quantityPurchased) {
        Bson filter = eq("id", treeId);
        Bson update = new Document("$inc", new Document("stock", -quantityPurchased));
        Document result = collection.findOneAndUpdate(filter, update);
        if (result == null) {
            System.out.println("Not enough stock to decrement for tree with ID: " + treeId);
        } else {
            System.out.println("Stock decremented successfully for tree with ID: " + treeId);
        }
    }
}
