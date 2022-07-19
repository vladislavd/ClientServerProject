package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.model.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ServerApp {

    public static final String STARTING_SERVER = "Starting server...";
    public static final String SERVER_STARTED = "Server started!";
    public static final String TEST_ENDPOINT = "/test";
    public static final int PORT = 8080;
    public static final int BACKLOG = 0;

    public static void main(String[] args) throws IOException {
        System.out.println(STARTING_SERVER);
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
        server.createContext(TEST_ENDPOINT, new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println(SERVER_STARTED);
    }

    static class MyHandler implements HttpHandler {

        private static final String RESPONSE_FOR = "This is the response for ";
        private static final int HTTP_OK = 200;
        private static final String DELIMITER = "\n";
        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            final Message message = objectMapper.readValue(exchange.getRequestBody().readAllBytes(), Message.class);
            String text = getString(exchange);
            String response = RESPONSE_FOR + message;
            exchange.sendResponseHeaders(HTTP_OK, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private String getString(HttpExchange exchange) {
            return new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining(DELIMITER));
        }
    }
}