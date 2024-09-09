package com.unimater;

import com.sun.net.httpserver.HttpServer;
import com.unimater.controller.HelloWorldHandler;
import com.unimater.controller.ProductTypeHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main( String[] args ){


        try {
            HttpServer servidor = HttpServer.create(
                    new InetSocketAddress(8080),0
            );

//
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/your_db", "root", "root"
            );

            servidor.createContext("/helloWorld",
                    new HelloWorldHandler());

            servidor.createContext("/productType",
                    new ProductTypeHandler(connection));

            servidor.setExecutor(null);
            servidor.start();
            System.out.println("Servidor rodando na porta 8080");

        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
