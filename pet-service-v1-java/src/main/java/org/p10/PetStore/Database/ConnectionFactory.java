package org.p10.PetStore.Database;


import org.p10.PetStore.Database.Interfaces.IConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory implements IConnectionFactory {

    public ConnectionFactory() {
    }

    @Override
    public Connection createDBConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://host.docker.internal:5432/postgres",
                            "postgres", "12345");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return c;
    }
}
