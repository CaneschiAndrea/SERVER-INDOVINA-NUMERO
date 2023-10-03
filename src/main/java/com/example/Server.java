package com.example;

import java.net.*;
import java.io.*;
import java.util.Random;

public class Server {
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(3000);
            System.out.println("Server in attesa di connessione...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione effettuata con il client.");

                int numeroDaIndovinare = generaNumeroCasuale();
                int tentativi = 0;

                BufferedReader inDalClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outVersoClient = new DataOutputStream(clientSocket.getOutputStream());

                boolean indovinato = false;

                while (!indovinato) {
                    tentativi++;
                    outVersoClient.writeBytes("Inserisci il numero:" + '\n');
                    String stringaRicevuta = inDalClient.readLine();
                    int numeroIndovinato = Integer.parseInt(stringaRicevuta);

                    if (numeroIndovinato < numeroDaIndovinare) {
                        outVersoClient.writeBytes("1\n"); // Numero troppo piccolo
                    } else if (numeroIndovinato > numeroDaIndovinare) {
                        outVersoClient.writeBytes("2\n"); // Numero troppo grande
                    } else {
                        outVersoClient.writeBytes("3\n"); // Numero indovinato
                        indovinato = true;
                    }
                }

                outVersoClient.writeBytes("4\n"); // Invio segnale di fine
                System.out.println("HAI INDOVINATO IN " + tentativi + " tentativi");

                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Errore durante l'esecuzione del server: " + e.getMessage());
        }
    }

    public static int generaNumeroCasuale() {
        Random rand = new Random();
        return rand.nextInt(100) + 1; // Genera un numero tra 1 e 100
    }
}