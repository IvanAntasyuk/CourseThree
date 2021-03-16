package Lesson3.serverSide.service;

public class Clients {
    private String login;
    private String password;
    private String nick;

    public Clients(String login, String password, String nick) {
        this.login = login;
        this.password = password;
        this.nick = nick;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNick() {
        return nick;
    }
}
