package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        new Client().setupNetworking();
    }

    private void setupNetworking(){
        try {
            Socket socket = new Socket("11.21.15.5",1024);
            System.out.println("networking established");

            PrintWriter writer=new PrintWriter(socket.getOutputStream());
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String received;
                        while((received=reader.readLine())!=null){
                            System.out.println("I received: "+received);
                        }
                        System.out.println("I received back: "+received);


                    } catch (IOException e) {
                    }

                }
            });
            t.start();
            Scanner scanner = new Scanner(System.in);
            while(true){
                String input=scanner.nextLine();
                writer.println(input);
                writer.flush();

                String received=reader.readLine();
                System.out.println("received: "+received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
