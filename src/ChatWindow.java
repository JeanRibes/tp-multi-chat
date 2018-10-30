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
		   ActionListener{
    String username;
    public static String message;
    public ChatConnector chat;
    JTextField messageField;
    JButton sendButton;
    JTextArea history;
    /*public static interface sendMessageInterface {
		public void sendMessage(String message);
		}
	public static void sendMessage(String message){sendMessageInterface.sendMessage(message);}
	*/
    public ChatWindow(String username) {
        this.username=username;
        System.out.println(username+" intializing");
        try {chat = new ChatConnector(this);}
        catch(URISyntaxException e){e.printStackTrace();}
        setLayout(new BorderLayout(0,0));
        setSize(500,600);
        setLocation(100,100);
        setTitle("Chat - "+username);

        messageField = new JTextField("votre message ici");
        //messageField.setBounds(3,50,230,30);
        //messageField.setSize(150,30);
        messageField.addActionListener(this); //c'est pratique car toutes mes actions ont la même source

        sendButton = new JButton("Envoyer");
        //sendButton.setSize(100,60);
        sendButton.setEnabled(false);
        sendButton.addActionListener(this); //c'est pratique car toutes mes actions ont la même source
        
        history = new JTextArea("historique:");
        JScrollPane scrollPane = new JScrollPane(history);
        JPanel scroll = new JPanel();
        scroll.setLayout(new BorderLayout());
        scroll.setBackground(Color.gray);
        scroll.add(scrollPane);
        history.setBackground(Color.white);
        history.setEditable(false);
		
		JPanel haut = new JPanel();
		GridLayout gl = new GridLayout(1,2);
		gl.setHgap(20);
		haut.setLayout(gl);
		haut.setBackground(Color.blue);
		haut.add(messageField,"1");
		haut.add(sendButton,"2");
		haut.setBorder(new EmptyBorder(10,10,10,10));
		scroll.setBorder(new EmptyBorder(10,10,10,10));
		add(haut, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
       
        setVisible(true);
        addWindowListener(this);
        
    }
    @Override
	public void receiveMsg(String message) {
		history.append("\n"+message);
	}
	@Override
	public void getReady(){
		sendButton.setEnabled(true);
		send(username+" a rejoint la discussion");
	}
    public void send(String message) {
		chat.send(message);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		message = username+" a quitté la discussion";
		send(message);
		chat.close();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		message = "["+username +"]> "+messageField.getText();
		send(message);
		System.out.println(messageField.getText());
		messageField.setText("");
	}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	
	public static void main(String[] args) {new ChatWindow("test username");}
}
