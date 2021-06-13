package com.balazs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class Clients extends Thread{
    public Socket getSocket() {
        return socket;
    }


    public String getYourName() {
        return yourName;
    }
    Socket socket;
    String yourName;
    Clients partner;
    String partnerName;
    public Clients(Socket socket, String yourName, Clients partner,String partnerName){
        this.socket = socket;
        this.yourName = yourName;
        this.partner = partner;
        this.partnerName = partnerName;
    }


    @Override
    public void run() {
        Main a = new Main();

        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(partner == null){
                this.sleep(20000);
                partner = a.people.get(partnerName);
                System.out.println("Connected");
            }
            PrintWriter output = new PrintWriter(partner.getSocket().getOutputStream(),true);
            while(true){
                String line = input.readLine();
                if(line.equals("exit")){
                    break;
                }
                output.println("Message: "+line+"  FROM: "+yourName);
            }
            System.out.println("Client: "+this.getName()+" disconnected");
            a.people.remove(this.getName());


        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong: "+e.getMessage());
        }finally{
            try{
                socket.close();
            }catch (IOException e){
                System.out.println("Couldnt Close "+e.getMessage());
            }

        }

    }
}
