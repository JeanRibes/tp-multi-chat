public class Launcher {
    public static void main(String[] args) {
        if(args.length>0){
            if(args[0].equals("-nogui")||args[0].equals("-cli")) {
		if(args.length>1)
		    new TerminalChat(args[1]);
		else
		    new TerminalChat();
            } else {
                new ChatWindow(args[0]);
            }
        }else {
            MultiChat.main(args);
        }
    }
}
