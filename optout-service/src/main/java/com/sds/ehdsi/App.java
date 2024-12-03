package com.sds.ehdsi;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws IOException {
        OptOutService service = new OptOutService();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // OptOut Endpoint
        server.createContext("/optout", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                String id = new String(exchange.getRequestBody().readAllBytes());
                service.optOut(id);
                exchange.sendResponseHeaders(200, -1);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // IsEligible Endpoint
        server.createContext("/iseligible", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String id = exchange.getRequestURI().getQuery().split("=")[1];
                boolean eligible = service.isEligible(id);
                String response = eligible ? "true" : "false";
                exchange.sendResponseHeaders(200, response.length());
                exchange.getResponseBody().write(response.getBytes());
                exchange.getResponseBody().close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.setExecutor(Executors.newFixedThreadPool(4));
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }
}
