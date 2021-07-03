/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capitalizeserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Amina
 */
public class CapitalizeServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8888);
            while (true) {
                Socket socket = server.accept();
                Thread t = new Thread(() -> {
                    try {
                        Scanner scanner = new Scanner(socket.getInputStream());
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                        while (true) {
                            String lowerCase = scanner.nextLine();
                            String upperCase = lowerCase.toUpperCase();
                            writer.println(upperCase);
                        }
                    } catch (IOException e) {
                    }
                });
                t.start();
            }
        } catch (IOException e) {
            System.err.println("Error creating socket on port 8888");
        }
    }

}
