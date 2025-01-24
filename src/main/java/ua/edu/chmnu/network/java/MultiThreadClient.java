package ua.edu.chmnu.network.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadClient {

    public static void main(String[] args) {
        final int NUM_CLIENTS = 1000;
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = 9556;

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < NUM_CLIENTS; i++) {
            executorService.execute(() -> {
                try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    String randomString = generateRandomString();
                    out.println(randomString);

                    String processedData = in.readLine();
                    String processingTime = in.readLine();

                    System.out.println("Client received: " + processedData);
                    System.out.println(processingTime);

                } catch (Exception e) {
                    System.err.println("Error in client session: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
    }

    private static String generateRandomString() {
        int length = 10 + new Random().nextInt(20);
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + random.nextInt(26)));
        }
        return sb.toString();
    }
}