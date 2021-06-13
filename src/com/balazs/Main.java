package com.balazs;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static Map<String,Clients> people = new HashMap<>();
    public static void main(String[] args) {
        try(ServerSocket serverSocket  = new ServerSocket(3000)){
            while(true){
                //CONNECT THE PERSON
                Socket socket = serverSocket.accept();

                //FIRST INPUT IS THE NAME
                String name = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                //SECOND IS THE PARTNERS
                String yourPartner = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();

                //SEARCH FOR PARTHER
                Clients partner = people.get(yourPartner);

                //CREATE CLIENT THREAD TO START MESSAGING PROCESS
                Clients client = new Clients(socket,name,partner,yourPartner);
                client.start();

                //STATUS MESSAGE-JSUT DEBUG
                System.out.println("Client Connected "+name);

                //PUT IN DATABASE
                people.put(name,client);





            }
        }catch(Exception e){
            System.out.println("Server Error "+e.getMessage());
        }
    }
}
