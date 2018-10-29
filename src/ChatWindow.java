import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

public class ChatWindow extends JFrame {
    String username;
    public static String message;
    JTextField messageField;
    JButton sendButton;
    JTextArea history;
    public ChatWindow(String username, Runnable sendMessage) {
        this.username=username;
        System.out.println(username+" intializing");
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
                sendMessage.run();
                System.out.println(messageField.getText());
                messageField.setText("");
            }
        });

        sendButton = new JButton("update UI");
        sendButton.setText("Envoyer");
        sendButton.setSize(100,30);
        sendButton.setLocation(250,50);
        sendButton.addActionListener(new Action() {
            public Object getValue(String key) {return null;}public void putValue(String key, Object value) {}public void setEnabled(boolean b) {}public boolean isEnabled() {return false;}public void addPropertyChangeListener(PropertyChangeListener listener) {}public void removePropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void actionPerformed(ActionEvent e) {
                message = "["+username +"]> "+messageField.getText();
                sendMessage.run();
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
				sendMessage.run();
			}
		});
        setVisible(true);
    }
    public void receiveMessage() {
        this.history.append("\n"+message);
    }
}
