import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatWindow extends JFrame {
    String username;
    public static String message;
    public WebSocketClient sync;
    JTextField messageField;
    JButton sendButton;
    JTextArea history;
    public ChatWindow(String username) {
        this.username=username;
        System.out.println(username+" intializing");
        try {setupWebSocket();}
        catch(URISyntaxException e){e.printStackTrace();}
        setLayout(null);
        setSize(500,600);
        setLocation(100,100);
        setTitle("Chat - "+username);

        messageField = new JTextField("votre message ici");
        messageField.setBounds(3,50,230,30);
        messageField.addActionListener(new Action() {
            public Object getValue(String key) {return null;}public void putValue(String key, Object value) {}public void setEnabled(boolean b) {}public boolean isEnabled() {return false;}public void addPropertyChangeListener(PropertyChangeListener listener) {}public void removePropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void actionPerformed(ActionEvent e) {
                message = "["+username +"]> "+messageField.getText();
                sync.send(message);
                System.out.println(messageField.getText());
                messageField.setText("");
            }
        });

        sendButton = new JButton("Envoyer");
        sendButton.setSize(100,30);
        sendButton.setLocation(250,50);
        sendButton.addActionListener(new Action() {
            public Object getValue(String key) {return null;}public void putValue(String key, Object value) {}public void setEnabled(boolean b) {}public boolean isEnabled() {return false;}public void addPropertyChangeListener(PropertyChangeListener listener) {}public void removePropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void actionPerformed(ActionEvent e) {
                message = "["+username +"]> "+messageField.getText();
                sync.send(message);
                System.out.println(messageField.getText());
                messageField.setText("");
            }
        });
        history = new JTextArea("historique:");
        JScrollPane scrollPane = new JScrollPane(history);
        JPanel scroll = new JPanel();
        scroll.setLayout(new BorderLayout());
        scroll.setSize(500,500);
        scroll.setLocation(3,100);
        scroll.add(scrollPane);
        history.setEditable(false);

        add(scroll);
        add(sendButton);
        add(messageField);
        
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				message = username+" a quitté la discussion";
				sync.send(message);
				sync.close();
			}
		});
        setVisible(true);
    }
    
    public void setupWebSocket() throws URISyntaxException {
		this.sync = new WebSocketClient(new URI("wss://api.ribes.me/tchat/room")) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                System.out.println("opened connection");
                sync.send(username+" a rejoint la discussion");
            }

            @Override
            public void onMessage(String message) {
                System.out.println(message);
                history.append("\n"+message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("closed connection");
                System.out.println("Reason: " + reason + " code");
                System.out.print(code);
            }

            @Override
            public void onError(Exception ex) {
                System.out.println("ERREUR WEBSOCKET");
                ex.printStackTrace();
            }
        };
        sync.connect(); //ne pas l'oublier ...
	}
}
