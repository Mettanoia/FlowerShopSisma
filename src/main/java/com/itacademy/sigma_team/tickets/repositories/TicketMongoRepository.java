package com.itacademy.sigma_team.tickets.repositories;

import com.itacademy.sigma_team.domain.Ticket;
import com.itacademy.sigma_team.dtos.DecorationDTO;
import com.itacademy.sigma_team.dtos.FlowerDTO;
import com.itacademy.sigma_team.dtos.TicketItem;
import com.itacademy.sigma_team.dtos.TreeDTO;
import com.itacademy.sigma_team.domain.Material;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public final class TicketMongoRepository {

    private final MongoCollection<Document> collection;

    public TicketMongoRepository() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("flower_shop");
        this.collection = database.getCollection("tickets");
    }

    public void add(Ticket ticket) {
        Document document = new Document("id", ticket.getId())
                .append("dateTime", ticket.getDateTime().toString())
                .append("total", ticket.getTotal());

        List<Document> items = new ArrayList<>();
        for (TicketItem item : ticket.getItems()) {
            Document itemDoc = new Document("productId", getProductId(item.toString()))
                    .append("productType", getItemTicketType(item))
                    .append("name", getProductName(item))
                    .append("quantity", getProductStock(item))
                    .append("price", getProductPrice(item))
                    .append("material", getProductMaterial(item))
                    .append("color", getProductColor(item))
                    .append("height", getProductHeight(item));
            items.add(itemDoc);
        }
        document.append("items", items);
        collection.insertOne(document);
    }

    public Collection<Ticket> getAll() {
        List<Ticket> tickets = new ArrayList<>();
        for (Document doc : collection.find()) {
            List<TicketItem> items = new ArrayList<>();
            List<Document> itemDocs = (List<Document>) doc.get("items");
            for (Document itemDoc : itemDocs) {
                String productType = itemDoc.getString("productType");
                TicketItem item = switch (productType) {
                    case "Flower" -> new FlowerDTO(
                            itemDoc.getString("productId"),
                            itemDoc.getString("name"),
                            itemDoc.getString("color"),
                            itemDoc.getDouble("price"),
                            itemDoc.getInteger("quantity")
                    );
                    case "Tree" -> new TreeDTO(
                            itemDoc.getString("productId"),
                            itemDoc.getString("name"),
                            itemDoc.getDouble("height"),
                            itemDoc.getDouble("price"),
                            itemDoc.getInteger("quantity")
                    );
                    case "Decoration" -> new DecorationDTO(
                            itemDoc.getString("productId"),
                            itemDoc.getString("name"),
                            Material.valueOf(itemDoc.getString("material")),
                            itemDoc.getDouble("price"),
                            itemDoc.getInteger("quantity")
                    );
                    default -> throw new IllegalArgumentException("Unknown product type: " + productType);
                };
                items.add(item);
            }
            Ticket ticket = new Ticket(
                    doc.getString("id"),
                    LocalDateTime.parse(doc.getString("dateTime")),
                    items,
                    doc.getDouble("total")
            );
            tickets.add(ticket);
        }
        return tickets;
    }

    public static String getProductId(String item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.id();
            case FlowerDTO flowerDTO -> flowerDTO.id();
            case TreeDTO treeDTO -> treeDTO.id();
        };
    }

    private static String getProductName(TicketItem item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.name();
            case FlowerDTO flowerDTO -> flowerDTO.name();
            case TreeDTO treeDTO -> treeDTO.name();
        };
    }

    private static int getProductStock(TicketItem item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.stock();
            case FlowerDTO flowerDTO -> flowerDTO.stock();
            case TreeDTO treeDTO -> treeDTO.stock();
        };
    }

    private static double getProductPrice(TicketItem item) {
        return switch (item) {
            case DecorationDTO decorationDTO -> decorationDTO.price();
            case FlowerDTO flowerDTO -> flowerDTO.price();
            case TreeDTO treeDTO -> treeDTO.price();
        };
    }

    private static String getProductMaterial(TicketItem item) {
        return item instanceof DecorationDTO decorationDTO ? decorationDTO.material().name() : null;
    }

    private static String getProductColor(TicketItem item) {
        return item instanceof FlowerDTO flowerDTO ? flowerDTO.color() : null;
    }

    private static Double getProductHeight(TicketItem item) {
        return item instanceof TreeDTO treeDTO ? treeDTO.height() : null;
    }

    private static String getItemTicketType(TicketItem item) {
        return item instanceof FlowerDTO ? "Flower" :
                item instanceof TreeDTO ? "Tree" :
                        item instanceof DecorationDTO ? "Decoration" :
                                "None";
    }


}
