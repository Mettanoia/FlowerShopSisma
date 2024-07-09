package com.itacademy.sigma_team.flowers.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public final class MongoDatabaseConnection {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "floristeria_db";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    private MongoDatabaseConnection() {
        // Constructor privado para evitar instanciación
    }

    public static MongoDatabase getDatabase() {
        if (database == null) {
            synchronized (MongoDatabaseConnection.class) {
                if (database == null) {
                    mongoClient = MongoClients.create(CONNECTION_STRING);
                    database = mongoClient.getDatabase(DATABASE_NAME);
                }
            }
        }
        return database;
    }
}
