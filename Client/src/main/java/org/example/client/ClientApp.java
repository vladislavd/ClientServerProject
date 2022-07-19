package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Message;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ClientApp {

    private static final String EXIT = "exit";
    private static final String SERVER_URL = "http://localhost:8080/test";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String INPUT_MESSAGE_OR_EXIT = "Input message or exit > ";
    private static final String CLIENT_IS_READY_TO_SEND_REQUESTS = "Client is ready to send requests!";
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(CLIENT_IS_READY_TO_SEND_REQUESTS);
        var client = HttpClient.newHttpClient();
        while (true) {
            System.out.print(INPUT_MESSAGE_OR_EXIT);
            String message = scanner.nextLine();
            if (message.equals(EXIT)) {
                break;
            }
            HttpRequest request = createHttpRequest(message);
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
    }

    private static HttpRequest createHttpRequest(String body) throws JsonProcessingException {
        Message msg = new Message();
        msg.setContent(body);
        msg.setLength(body.length());
        final String bodyString = objectMapper.writeValueAsString(msg);
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(bodyString))
                .uri(URI.create(SERVER_URL))
                .build();
    }
}