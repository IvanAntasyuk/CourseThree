package Lesson3.serverSide.service;

import Lesson3.serverSide.interfaces.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private final int PORT = 8082;
    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return this.authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
              //  System.out.println("Севрвер ожидает подключения");
                Socket socket = server.accept();
              //  System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
       } finally {
           if (authService != null) {
            authService.stop();
            }
        }
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMessage(message);
        }
    }

    public synchronized void getOnlineClientsList(ClientHandler clientHandler) {
        StringBuilder listSb = new StringBuilder(" ");
        for (ClientHandler c : clients) {
            if (!c.equals(clientHandler)) {
                listSb.append(c.getName()).append(" , ");
            }
        }
        int sizeListSb = listSb.length();
        listSb.deleteCharAt(sizeListSb - 1);
        listSb.deleteCharAt(sizeListSb - 2);
        clientHandler.sendMessage(listSb.toString());

    }

    public synchronized void sendMassageToCertainUser(ClientHandler from, String toWhom, String message) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(toWhom)) {
                c.sendMessage(message);
                from.sendMessage(message);
            }
        }
    }

    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

}
