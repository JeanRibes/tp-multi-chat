import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.net.URISyntaxException;

public class ChatWindow extends JFrame
        implements ChatConnector.OnMessageListener,
        ChatConnector.OnReadyListener,
        WindowListener,
        ActionListener {
    public static int openChats;

    public static String message;
    String username;
    public ChatConnector chat;
    public boolean ready = false;
    JTextField messageField;
    JButton sendButton;
    JTextArea history;
    JPanel scroll;
    JPanel haut;

    public ChatWindow(String username) {
        System.out.println(username + " is intializing");

        setLayout(new BorderLayout(0, 0));
        setSize(500, 600);
        setLocation(100, 100);
        setUsername(username);

        messageField = new JTextField("");
        messageField.addActionListener(this); //c'est pratique car toutes mes actions ont la même source

        sendButton = new JButton("Envoyer");
        sendButton.setEnabled(false);
        sendButton.addActionListener(this); //c'est pratique car toutes mes actions ont la même source

        history = new JTextArea("Lancement du chat...");
        JScrollPane scrollPane = new JScrollPane(history);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll = new JPanel();
        scroll.setLayout(new BorderLayout());
        scroll.setBackground(Color.darkGray);
        scroll.add(scrollPane);
        history.setBackground(Color.gray);
        history.setEditable(false);

        haut = new JPanel();
        GridLayout gl = new GridLayout(1, 2);
        gl.setHgap(20);
        haut.setLayout(gl);
        haut.setBackground(Color.red);
        haut.add(messageField, "1");
        haut.add(sendButton, "2");
        haut.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(haut, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
        openChats+=1;
        addWindowListener(this);
        try {
            receiveMsg("Connexion...");
            chat = new ChatConnector(this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMsg(String message) {
        history.append("\n" + message);
    }

    @Override
    public void getReady() {
        receiveMsg("Connecté !");
        //receiveMsg(new String(new char[history.getColumns()).replace("\0", "-"));
        receiveMsg("-------------------------------------------------------------------------------------------------------------------");
        ready = true;
        sendButton.setEnabled(true);
        scroll.setBackground(Color.gray);
        history.setBackground(Color.white);
        haut.setBackground(Color.blue);
        chat.send(" * "+username + " a rejoint la discussion ! *");
    }

    public void send(String message) {
        if(message.startsWith("/nick")){
            String username = message.substring(5);
            if(username.length()<2) {
                username = chooseUsername();
                if(username==null)
                    return;
            } else {username = username.substring(1);}
            chat.send(" * "+this.username+" a changé son pseudo en "+username+" * ");
            setUsername(username);
        } else {
            chat.send("[" + username + "]: " + message);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        message = " * "+username + " a quitté la discussion *";
        chat.send(message);
        chat.close();
        if(openChats==0) {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }else{
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
        openChats-=1;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        send(messageField.getText());
        messageField.setText("");
    }

    public void setUsername(String username) {
        this.username = username;
        setTitle("Chat - " + username);
    }
    public String chooseUsername(){
        String result = (String) JOptionPane.showInputDialog(
                this,
                "Choisissez un nouveau pseudo",
                "Changement de pseudo",
                JOptionPane.PLAIN_MESSAGE);
        return result;
    }
    public void windowOpened(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public static void main(String[] args) {
        new ChatWindow("test username");
    }
}
