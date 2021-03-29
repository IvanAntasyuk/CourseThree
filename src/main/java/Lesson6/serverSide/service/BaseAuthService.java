package Lesson6.serverSide.service;

import Lesson3.serverSide.interfaces.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class BaseAuthService implements AuthService {
    private static final Logger logger = LogManager.getLogger(BaseAuthService.class);

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB = "jdbc:mysql://127.0.0.1/DBclients";
    static final String USER = "root";
    static final String PASSWORD = "rootroot";
    static Connection connection;
    static Statement statement;



    private Map<String, Clients> clientsHashMap = new HashMap<>();

    public BaseAuthService()  {
        try{
            start();
            String query = "SELECT login, password, nick FROM users;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String login = resultSet.getString(1);
                String password = resultSet.getString(2);
                String nick = resultSet.getString(3);
                clientsHashMap.put(login, new Clients(login, password, nick));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        // stop();
        }
    }
   static public void logPrintStart(){

        logger.info("AuthService start ");
    }


    @Override
    public void start(){
        logPrintStart();
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(DB, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (ClientHandler.flag = true) {
            logger.info("AuthService stop log");


            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getNickbyLoginandPassword(String login, String password) {
        for (Clients e : clientsHashMap.values()) {
            if (e.getLogin().equalsIgnoreCase(login) && e.getPassword().equalsIgnoreCase(password)) {
                return e.getNick();
            }
        }
        return null;
        }
      static public void changeNick(String nick,String newNick){
          try {
              statement.executeUpdate("UPDATE users SET nick=" + newNick + " WHERE nick = " + nick);
          } catch (SQLException e) {
              e.printStackTrace();
          }
      }
    }
