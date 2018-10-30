import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MultiChat extends JFrame{
    ChatWindow[] clients;
    public MultiChat(){
		clients = new ChatWindow[0];
        setTitle("Chat manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setLayout(null);
        setSize(400,90);
        setLocation(10,30);
        setBackground(Color.red);
        JLabel monText = new JLabel("ajoutez des utilisateurs");
        monText.setBackground(Color.yellow);
        //monText.setLocation(0,0);
        //monText.setSize(500,20);

        JTextField monChampsTexte = new JTextField("Entrez ici le nom d'utilisateur");
        //monChampsTexte.setLocation(0,20);
        //monChampsTexte.setSize(140,30);
        monChampsTexte.addActionListener(new Action() {
            public Object getValue(String key) {return null;}public void putValue(String key, Object value) {}public void setEnabled(boolean b) {}public boolean isEnabled() {return false;}public void addPropertyChangeListener(PropertyChangeListener listener) {}public void removePropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(monChampsTexte.getText());
                addClient(monChampsTexte.getText());
                monChampsTexte.setText("");
            }
        });

        JButton update = new JButton("ajouter cet utilisateur");
        //update.setSize(200,40);
        //update.setLocation(150,20);
        update.addActionListener(new Action() {
            public Object getValue(String key) {return null;}public void putValue(String key, Object value) {}public void setEnabled(boolean b) {}public boolean isEnabled() {return false;}public void addPropertyChangeListener(PropertyChangeListener listener) {}public void removePropertyChangeListener(PropertyChangeListener listener) {}
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(monChampsTexte.getText());
                addClient(monChampsTexte.getText());
                monChampsTexte.setText("");
            }
        });
		JPanel panneau = new JPanel();
		JPanel haut = new JPanel();
		panneau.setLayout(new FlowLayout());
        haut.setLayout(new FlowLayout());
        panneau.setBackground(Color.orange);
        haut.setBackground(Color.green);
        haut.add(monText);
        panneau.add(monChampsTexte);
        panneau.add(update);
        //setContentPane(haut);
        add(haut, BorderLayout.NORTH);
        add(panneau, BorderLayout.CENTER);
        setVisible(true);
 }
    public void addClient(String username){
		ChatWindow[] nouveau = new ChatWindow[this.clients.length+1];
		for(int i=0;i<clients.length;i+=1) {
			nouveau[i]=clients[i];
		}
		nouveau[clients.length]=new ChatWindow(username);
		clients = nouveau;
		nouveau=null;
	}
	
	public static void main(String[] args) {
        new MultiChat();
    }
}
