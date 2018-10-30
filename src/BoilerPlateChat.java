import java.net.URISyntaxException;
/**
 * Les "implements" sont nécessaires. Cette classe peut cependant 
 * hériter d'une autre (genre JFrame).
 * Pour envoyer un message, il faudra faire `chat.send()`
 */
public class BoilerPlateChat 
implements ChatConnector.OnMessageListener,ChatConnector.OnReadyListener {
	
	private ChatConnector chat;
	public String username;
	public boolean ready = false;
	
	public BoilerPlateChat(){
		this.username = "changeme";
		try {chat = new ChatConnector(this);} //initialisation du chat
        catch(URISyntaxException e){e.printStackTrace();}
	}
	/**
	 * Implémentez ici les action qui suivent la réception d'un message
	 * (genre le rajouter dans une boite de texte, faire un pop up...)
	 */ 
	@Override
	public void receiveMsg(String message){
	//	System.out.println(message);  //déjà dans ChatConnector
	}
	
	/**
	 * Mettez ici les action à effectuer une fois le chat
	 * prêt à envoyer/recevoir des messages
	 */
	@Override
	public void getReady(){
		chat.send(username+" a rejoint la discussion");
		//activer différents truc de l'interface ... ou pas
	}
	
	public static void main (String args[]) {
	}
}

