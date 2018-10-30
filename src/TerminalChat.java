import java.util.Scanner;
import java.net.URISyntaxException;
public class TerminalChat implements ChatConnector.OnMessageListener,
									 ChatConnector.OnReadyListener {
	private Scanner sc;
	private ChatConnector chat;
	public String username;
	public boolean ready = false;
	
	public TerminalChat(){
		sc = new Scanner(System.in);
        System.out.print("Choisissez un pseudo ? >");
        setUsername(sc.nextLine());
        
		try {chat = new ChatConnector(this);} //initialisation du chat
        catch(URISyntaxException e){e.printStackTrace();}
	}
	
	@Override
	public void receiveMsg(String message){
	//	System.out.println(message);  //déjà dans CHatConnector
		System.out.print("> "); //pour afficher un curseur pour l'input (à ne pas faire dans le send sinnon il se faire enlever au prochain message)
	}
	@Override
	public void getReady(){
		chat.send(username+" a rejoint la discussion");
		new Thread(() -> {
			while(true) {
				String message = sc.nextLine();
				if(message.equals("/quit")){System.exit(0);}
				if(message.startsWith("/nick")){
				    if(message.startsWith("/nicks")){setUsername(message.substring(7));continue;}
                    chat.send(" * "+this.username+" a changé son pseudo en "+username+" * ");
                    setUsername(message.substring(6));
				} else {
					chat.send("["+username +"]: "+message);
				}
			}
		}).start();
	}
	public void setUsername(String username) {
	    this.username = username;
    }
	public static void main (String args[]) {
		new TerminalChat();
	}
}

