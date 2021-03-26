package Lesson4.clientSide.one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {

    private final Integer SERVER_PORT = 8089;
    private final String SERVER_ADDRESS = "localhost";
    private Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    boolean isAuthorized = false;

    private JTextField msgInputField;
    private JTextArea chatArea;

    public Client() {
        try {
            connection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepareGUI();
    }

    public void connection() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        new Thread(() -> {
            try {
                while (true) {
                    String messageFromServer = dis.readUTF();
                    if (messageFromServer.startsWith("/authok")) {
                        isAuthorized = true;
                        chatArea.append(messageFromServer + "\n");
                        break;
                    }
                    chatArea.append(messageFromServer + "\n");
                }

                while (isAuthorized) {
                    String messageFromServer = dis.readUTF();
                    chatArea.append(messageFromServer + "\n");
                }
            } catch (IOException ignored) {

            }
        }).start();
    }

    public void send() {
        if (msgInputField.getText() != null && !msgInputField.getText().trim().isEmpty()) {
            try {
                history();
                dos.writeUTF(msgInputField.getText());
                if (msgInputField.getText().equals("/end")) {
                    isAuthorized = false;
                    closeConnection();
                }
                if (msgInputField.getText().equals("/h")) {
                    loadHistory();
                }
                msgInputField.setText("");
            } catch (IOException ignored) {
            }
        }
    }

    private void closeConnection() {
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }

    private void history() {
        try (PrintWriter out = new PrintWriter(new FileWriter("history.txt", true))) {
            if (!msgInputField.getText().startsWith("/")) {
                out.append("Message from u1:" + msgInputField.getText() + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("history.txt"))) {
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }

            dos.writeUTF(String.valueOf(sb));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void prepareGUI() {

        setBounds(600, 300, 500, 500);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon("Java.png");
        label.setIcon(icon);
       // add(label);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);

        btnSendMsg.addActionListener(e -> {
            send();
        });

        msgInputField.addActionListener(e -> {
            send();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Client();
        });
    }
}