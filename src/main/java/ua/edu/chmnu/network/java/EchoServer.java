package ua.edu.chmnu.network.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        final int PORT = 9556;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Echo server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(() -> {
                    try (
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
                    ) {
                        String input = in.readLine();
                        if (input != null) {
                            long startTime = System.nanoTime();

                            String processedData = input.toUpperCase();

                            long duration = (System.nanoTime() - startTime) / 1_000_000;

                            out.println("Processed: " + processedData);
                            out.println("Processing time: " + duration + " ms");
                        }
                    } catch (Exception e) {
                        System.err.println("Error handling client: " + e.getMessage());
                    }
                }).start();
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}
