import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MultiChat extends JFrame implements ActionListener{
    ChatWindow[] clients;
    public static MultiChat that;
    public static boolean visible = true;
    public static String wsUrl;
    JTextField serverName;
    public MultiChat(String wsUrl){
        MultiChat.wsUrl = wsUrl;
		clients = new ChatWindow[0];
        setTitle("Chat manager");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(340,220);
        setLocation(10,30);
        setBackground(Color.red);
        JLabel monText = new JLabel("ajoutez des utilisateurs");
        monText.setBackground(Color.yellow);

        JTextField monChampsTexte = new JTextField("Entrez ici le nom d'utilisateur");
        monChampsTexte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(monChampsTexte.getText());
                addClient(monChampsTexte.getText());
                monChampsTexte.setText("");
            }
        });

        JButton update = new JButton("ajouter cet utilisateur");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultiChat.this.actionPerformed(e);
                String username = monChampsTexte.getText();
                monChampsTexte.setText("");
                addClient(username);
            }
        });

        JButton hide = new JButton("Cacher cette fenêtre");
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(ChatWindow.openChats>0) {
                    setVisible(false);
                    visible = false;
                    ChatWindow.openChats -=1;
                }
            }
        });

        serverName = new JTextField("wss://api.ribes.ovh");
        serverName.addActionListener(this);
        JButton setServer = new JButton("Définir le serveur de chat");
        setServer.addActionListener(this);

		JPanel panneau = new JPanel();
		JPanel haut = new JPanel();
		JPanel bas = new JPanel();
		panneau.setLayout(new FlowLayout());
        haut.setLayout(new FlowLayout());
        bas.setLayout(new FlowLayout());
        panneau.setBackground(Color.orange);
        haut.setBackground(Color.green);
        bas.setBackground(new Color(123, 233, 255));
        haut.add(monText);
        panneau.add(monChampsTexte);
        panneau.add(update);
        panneau.add(hide);
        bas.add(serverName);
        bas.add(setServer);
        //setContentPane(haut);
        add(haut, BorderLayout.NORTH);
        add(panneau, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);
        
        panneau.setBorder(new EmptyBorder(10,10,10,10));
        setVisible(true);
 }
    public void addClient(String username){
		ChatWindow[] nouveau = new ChatWindow[this.clients.length+1];
		for(int i=0;i<clients.length;i+=1) {
			nouveau[i]=clients[i];
		}
		nouveau[clients.length]=new ChatWindow(username, wsUrl);
		clients = nouveau;
		nouveau=null;
	}
	
	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) { //merci StackOverflow
                System.out.println(info.getClassName());
                if ("javax.swing.plaf.nimbus.NimbusLookAndFeel".equals(info.getClassName())) { //le plus beau
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                if ("com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) { //y'a des problèmes avec les bordures c'est un peu moche
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e) {}

        that = new MultiChat("ws://api.ribes.ovh");
    }
    public static boolean revoir(){
        if(!visible) {
            that.setVisible(true);
            return true;
        } else return false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        MultiChat.wsUrl = serverName.getText();
    }
}