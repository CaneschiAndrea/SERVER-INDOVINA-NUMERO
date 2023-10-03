package com.example;

import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
    ServerSocket server = null;
    Socket client = null;
    int numeroDaIndovinare;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public Socket attendi() {
        try {
            System.out.println("SERVER in esecuzione...");
            server = new ServerSocket(3000);
            client = server.accept();
            server.close();

            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
            System.exit(1);
        }
        return client;
    }

    public void comunica() {
        try {
            generaNumeroCasuale();

            outVersoClient.writeBytes("Benvenuto! Indovina il numero (da 0 a 99):" + '\n');

            while (true) {
                String stringaRicevuta = inDalClient.readLine();
                int numeroIndovinato = Integer.parseInt(stringaRicevuta);

                if (numeroIndovinato < numeroDaIndovinare) {
                    outVersoClient.writeBytes("Il numero è troppo basso. Prova di nuovo:" + '\n');
                } else if (numeroIndovinato > numeroDaIndovinare) {
                    outVersoClient.writeBytes("Il numero è troppo alto. Prova di nuovo:" + '\n');
                } else {
                    outVersoClient.writeBytes("Congratulazioni! Hai indovinato il numero." + '\n');
                    break;
                }
            }

            System.out.println("Chiusura connessione");
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Qualcosa è andato storto!");
            System.exit(1);
        }
    }

    public void generaNumeroCasuale() {
        Random rand = new Random();
        numeroDaIndovinare = rand.nextInt(100); // Modifica il limite a seconda del tuo gioco
    }
}