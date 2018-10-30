import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MultiChat extends JFrame{
    ChatWindow[] clients;
    public MultiChat(){
		clients = new ChatWindow[0];
        setTitle("Chat manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(250,150);
        setLocation(10,30);
        setBackground(Color.red);
        JLabel monText = new JLabel("ajoutez des utilisateurs");
        monText.setBackground(Color.yellow);

        JTextField monChampsTexte = new JTextField("Entrez ici le nom d'utilisateur");
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
        
        panneau.setBorder(new EmptyBorder(10,10,10,10));
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (UnsupportedLookAndFeelException e) {}catch (ClassNotFoundException e) {}catch (InstantiationException e) {}catch (IllegalAccessException e) {}
        new MultiChat();
    }
}
