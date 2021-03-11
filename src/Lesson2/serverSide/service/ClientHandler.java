package Lesson2.serverSide.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    boolean isLogin;
    static boolean flag;

    private String name;

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.name = "";

            new Thread(() -> {
                try {
                    authentication();
                    readMessage();
                } catch (IOException ignored) {
                } finally {
                    closeConnection();
                }

            }).start();
            new Thread(() -> {
                try {
                    Thread.sleep(120000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (!isLogin) {
                    sendMessage("Time for authorization is over");
                    closeConnection();
                }
            }

            ).start();

        } catch (IOException e) {
            throw new RuntimeException("Problem with ClientHandler");
        }

    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMessage(name + " leave chat");
        try {
            dis.close();
        } catch (IOException ignored) {

        }
        try {
            dos.close();

        } catch (IOException ignored) {

        }
        try {
            socket.close();
        } catch (IOException ignored) {

        }
    }

    public void readMessage() throws IOException {
        while (true) {
            String messageFromClient = dis.readUTF();
            System.out.println(name + " send message " + messageFromClient);
            if (messageFromClient.trim().startsWith("/")) {
                if (messageFromClient.startsWith("/w")) {
                    String message[] = messageFromClient.split(" ", 3);
                    myServer.sendMassageToCertainUser(this, message[1], name + " :" + message[2]);
                }
                if (messageFromClient.equalsIgnoreCase("/end")) {
                    flag = true;
                    return;
                }
            } else {

                myServer.broadcastMessage(name + ": " + messageFromClient);
            }
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException ignored) {

        }
    }

    private void authentication() throws IOException {
        while (true) {
            String str = dis.readUTF();
            if (str.startsWith("/auth")) {
                String[] arr = str.split("\\s");
                String nick = myServer
                        .getAuthService()
                        .getNickbyLoginandPassword(arr[1], arr[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMessage("Client login :  " + nick);
                        name = nick;
                        myServer.broadcastMessage("Hello " + name);
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMessage("Nick is busy");
                    }
                }
            } else {
                sendMessage("Wrong login or password");
            }
        }
    }

    public String getName() {
        return name;
    }
}