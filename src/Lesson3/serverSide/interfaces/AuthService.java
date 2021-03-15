package Lesson3.serverSide.interfaces;

import java.sql.SQLException;

public interface AuthService {
    void start() throws ClassNotFoundException, SQLException;
 void stop();
    String getNickbyLoginandPassword(String login, String password);
}
