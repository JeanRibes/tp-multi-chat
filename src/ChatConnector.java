import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
public class ChatConnector extends WebSocketClient{
	public String username;
	public String lastMessage;
	private OnMessageListener messageListener;
	private OnReadyListener	readyListener;
	public ChatConnector(Object context) throws URISyntaxException{
		super(new URI("wss://api.ribes.me/tchat/room"));
		System.out.println("Connexion au serveur de tchat");
		messageListener = (OnMessageListener) context;
		readyListener = (OnReadyListener) context;
		connect();
	}
	/*public void send(String message) {
		this.send(message);
	}*/
	public interface OnMessageListener{void receiveMsg(String message);}
	public interface OnReadyListener{void getReady();}
	
    public void onOpen(ServerHandshake serverHandshake) {
	  System.out.println("opened connection");
	  readyListener.getReady();
	}
	
	public void onMessage(String message){
		System.out.println("\r"+message);
		messageListener.receiveMsg(message);
		this.lastMessage = message;
	}
	
	public void onClose(int code, String reason, boolean remote) {
		if(remote){
			System.out.println("closed connection");
			System.out.println("Reason: " + reason + " code");
			System.out.print(code);
		}
	}
	public void onError(Exception ex) {
		System.out.println("ERREUR WEBSOCKET");
		ex.printStackTrace();
	}
}

