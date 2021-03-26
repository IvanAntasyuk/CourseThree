package Lesson6.serverSide.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private static final Logger log = LogManager.getLogger(ClientHandler.class);

    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    boolean isLogin;
    static boolean flag;

    private String name;

    public ClientHandler(MyServer myServer, Socket socket) throws InterruptedException {

        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.name = "";

            ExecutorService executorService = Executors.newFixedThreadPool(3);
            executorService.submit(Objects.requireNonNull(authentication()));
            executorService.submit(readMessage());
            executorService.submit(timeOutConnectionIsLogin());
            executorService.shutdown();

        } catch (IOException e) {
            throw new RuntimeException("Problem with ClientHandler");
        }

    }

    public Runnable readMessage() throws IOException {
        while (true) {
            String messageFromClient = dis.readUTF();


            log.fatal(name + " send message " + messageFromClient);

            if (messageFromClient.trim().startsWith("/")) {
                if (messageFromClient.startsWith("/w")) {
                    String message[] = messageFromClient.split(" ", 3);
                    myServer.sendMassageToCertainUser(this, message[1], name + " :" + message[2]);
                }
                if (messageFromClient.equalsIgnoreCase("/end")) {
                    flag = true;
                    return null;
                }
            } else {

                myServer.broadcastMessage(name + ": " + messageFromClient);
            }
            if (messageFromClient.startsWith("/change")) {
                String[] messageChangeNick = messageFromClient.split(" ", 3);
                BaseAuthService.changeNick(messageChangeNick[1], messageChangeNick[2]);
                sendMessage("Ник изменен на " + messageChangeNick[2]);
            }
        }
    }


    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException ignored) {

        }
    }

    private Runnable timeOutConnectionIsLogin() throws InterruptedException {
        Thread.sleep(12000);
        if (!isLogin) {
            sendMessage("Time for authorization is over");
            closeConnection();
        }
        return null;
    }

    private Runnable authentication() throws IOException {
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
                        isLogin = true;
                        myServer.subscribe(this);
                        return null;
                    } else {
                        sendMessage("Nick is busy");
                    }
                }
            } else {
                sendMessage("Wrong login or password");
            }
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMessage(name + " leave chat");
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public String getName() {
        return name;
    }
}