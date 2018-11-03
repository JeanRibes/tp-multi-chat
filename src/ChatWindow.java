import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ChatWindow extends JFrame
        implements ChatConnector.OnMessageListener,
        ChatConnector.OnReadyListener,
        WindowListener,
        ActionListener, KeyListener {
    public static int openChats;

    public static String message;
    private CommandHistory commands;
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
        setVisible(true);

        messageField = new JTextField("");
        messageField.addActionListener(this); //c'est pratique car toutes mes actions ont la même source
        messageField.addKeyListener(this);

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
        //scrollPane.setAutoscrolls(true);
        history.setLineWrap(true);
        //scroll.setAutoscrolls(true);
        //history.setAutoscrolls(true);
        history.setBackground(Color.gray);
        history.setEditable(false);

        haut = new JPanel();
        /*GridLayout gl = new GridLayout(1, 2);/
        gl.setHgap(20);
        haut.setLayout(gl);
        haut.add(messageField, "1");
        haut.add(sendButton, "2");*/
        haut.setBackground(Color.red);
        haut.setLayout(new BorderLayout());
        haut.add(messageField, BorderLayout.CENTER);
        haut.add(sendButton, BorderLayout.EAST);
        haut.setBorder(new EmptyBorder(10, 10, 10, 10));
        scroll.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(haut, BorderLayout.SOUTH);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
        openChats+=1;
        addWindowListener(this);
        commands = new CommandHistory();
        try {
            receiveMsg("Connexion...");
            chat = new ChatConnector(this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        messageField.grabFocus();
    }

    @Override
    public void receiveMsg(String message) {
        history.append("\n" + message);
        scroll.scrollRectToVisible(new Rectangle(0,0,10,10));
        history.setCaretPosition(history.getDocument().getLength());
    }

    @Override
    public void getReady() {
        receiveMsg("Connecté !");
        //receiveMsg(new String(new char[history.getColumns()).replace("\0", "-"));
        receiveMsg("-----------------------------------------------------------------------------------------------------------------");
        ready = true;
        sendButton.setEnabled(true);
        scroll.setBackground(Color.gray);
        history.setBackground(Color.white);
        haut.setBackground(Color.blue);
        chat.send(" * "+username + " a rejoint la discussion ! *");
    }

    public void send(String message) {
        commands.add(message);
        commands.reset();
        if(message.startsWith("/")) {
            dispatchCommand(message);
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
        return  (String) JOptionPane.showInputDialog(
                this,
                "Choisissez un nouveau pseudo",
                "Changement de pseudo",
                JOptionPane.PLAIN_MESSAGE);
    }
    public void dispatchCommand(String msg) {
        if(msg.startsWith("/nick")){
            String username = msg.substring(5);
            if(username.length()<2) {
                username = chooseUsername();
                if(username==null)
                    return;
            } else {username = username.substring(1);}
            chat.send(" * "+this.username+" a changé son pseudo en "+username+" * ");
            setUsername(username);
        }
        if(msg.startsWith("/me")) {
            chat.send("* "+this.username+msg.substring(3));
        }
        if(msg.equals("/quit")){
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            dispose();
            setVisible(false);
        }
        if(msg.equals("/new")){
            if(MultiChat.revoir()) //que du sale
                openChats+=1;
        }
        if(msg.startsWith("/help")) {
            receiveMsg("====Aide====\n * /nick pour changer de pseudo\n * /new pour ré-afficher le manager");
            receiveMsg(" * /ping pour tester la connectivité");
            receiveMsg(" * /me pour un status\n * /quit pour fermer\n============");
        }
        if(msg.startsWith("/ping")) {
            chat.testPing();
        }
    }

    public void windowOpened(WindowEvent e) {
        System.out.println("openned");
        messageField.grabFocus();
    }
    public void windowActivated(WindowEvent e) {
        messageField.grabFocus();
        //chat.send(" * "+this.username+" n'est plus afk *"); //spam trop
    }
    public void windowIconified(WindowEvent e) { //ne marche pas sous Enlightenment
        chat.send(" * "+this.username+" est maintenant afk *");
    }

    public void windowDeiconified(WindowEvent e) {
        chat.send(" * "+this.username+" n'est plus afk *");
    }

    public void windowDeactivated(WindowEvent e) { //marche
        //chat.send(" * "+this.username+" est maintenant afk *");
    }

    public void windowClosed(WindowEvent e) {}

    public static void main(String[] args) {
        new ChatWindow("test username");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==38) {
            messageField.setText(commands.up());
            return;
        }
        if(e.getKeyCode()==40){
            messageField.setText(commands.down());
            return;
        }
        if(e.getModifiers()==InputEvent.CTRL_MASK && e.getKeyCode()==67) {
            System.out.println("yay");
            messageField.setText("");
        }
        //System.out.println(e.getKeyCode());
    }public void keyReleased(KeyEvent e) {}
}
