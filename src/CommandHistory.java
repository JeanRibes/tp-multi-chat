import java.util.ArrayList;

public class CommandHistory extends ArrayList<String>{
    private ArrayList<String> commands;
    private int index;
    public CommandHistory(){
        super();
        reset();
    }

    public String currentCommand() {
        try{return get(index);}
        catch (IndexOutOfBoundsException e){return "";}
    }

    public String up(){
        index-=1;
        try{return get(index);}
        catch (IndexOutOfBoundsException ex) {
            //System.out.println(index+" -> error");
            if(index<0){index+=1;}//on annule l'erreur précédente
            return currentCommand();
        }
    }

    public String down(){
        index+=1;
        try{return get(index);}
        catch (IndexOutOfBoundsException ex) {
            //System.out.println(index+" -> error");
            if(index>=size()){index-=1;}//on annule l'erreur précédente
            return "";
        }
    }

    public void reset(){index=size();}
}
