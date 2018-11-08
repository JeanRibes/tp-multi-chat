public class Launcher {
    public static void main(String[] args) {
        String server = "ws://api.ribes.ovh";
        if(args.length>0){
            if(args[0].equals("-server")||args[0].equals("-host")) {
                server = args[1];
            }
            if(args[0].equals("-nogui")||args[0].equals("-cli")) {
		if(args.length>1)
		    new TerminalChat(args[1], server);
		else
		    new TerminalChat(server);
            } else {
                new ChatWindow(server, args[0]);
            }
        }else {
            MultiChat.main(args);
        }
    }
}
