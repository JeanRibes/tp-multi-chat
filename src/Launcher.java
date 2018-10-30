public class Launcher {
    public static void main(String[] args) {
        if(args.length>0){
            if(args[0].equals("-nogui")||args[0].equals("-cli")) {
                new TerminalChat();
            } else {
                new ChatWindow(args[0]);
            }
        }else {
            MultiChat.main(args);
        }
    }
}
