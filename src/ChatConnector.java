import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
public class ChatConnector extends WebSocketClient{
	public String username;
	public String lastMessage;
	private OnMessageListener messageListener;
	private OnReadyListener	readyListener;
    private long pingSTartTime;
	public ChatConnector(Object context) throws URISyntaxException{
		//super(new URI("wss://api.ribes.me/tchat/room"));
		super(new URI("ws://5093937a.ngrok.io/tchat/room"));
		System.out.println("Connexion au serveur de tchat");
		messageListener = (OnMessageListener) context;
		readyListener = (OnReadyListener) context;
		connect();
	}
	public void send(String message) {
		try{super.send(message);}
		catch (WebsocketNotConnectedException e) {messageListener.receiveMsg("!! Erreur: aucune connexion au serveur");}
	}
	public interface OnMessageListener{void receiveMsg(String message);}
	public interface OnReadyListener{void getReady();}
	
    public void onOpen(ServerHandshake serverHandshake) {
	  System.out.println("opened connection");
	  readyListener.getReady();
	}
	
	public void onMessage(String message){
        if(pingSTartTime !=0 && message.startsWith("pong")){
            long ping = System.currentTimeMillis()- pingSTartTime;
            message = "ping: "+ping;
            pingSTartTime = 0;
        }
        if(message.startsWith("pong")){return;}
		System.out.println("\r"+message);
		messageListener.receiveMsg(message);
		this.lastMessage = message;
	}
	
	public void onClose(int code, String reason, boolean remote) {
	    messageListener.receiveMsg("!! connexion terminée, code "+code+", raison "+reason);
		if(remote){
			System.out.println("closed connection");
			System.out.println("Reason: " + reason + " code");
			System.out.print(code);
		}
	}
	public void onError(Exception ex) {
		messageListener.receiveMsg("!! Erreur de connexion au serveur !! ");
		System.out.println("ERREUR WEBSOCKET");
		ex.printStackTrace();
	}

	public void testPing(){
	    send("pong");
	    pingSTartTime = System.currentTimeMillis();
    }
}

