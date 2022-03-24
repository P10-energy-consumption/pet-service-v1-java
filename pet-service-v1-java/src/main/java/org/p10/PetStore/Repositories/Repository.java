package org.p10.PetStore.Repositories;

import org.p10.PetStore.Database.ConnectionFactory;

import java.sql.Connection;

public class Repository {
    private final ConnectionFactory connectionFactory;

    public Repository() {
        this.connectionFactory = new ConnectionFactory();
    }

    public Connection openConnection() {
        return connectionFactory.createDBConnection();
    }
}
