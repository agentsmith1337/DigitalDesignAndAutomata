import java.util.Scanner;
import java.util.HashMap;
public class Automata {
    public static void main(String[] args) {
        System.out.println("Automata Verification with Java 1.0");
        System.out.println("Please extend the Automata class and override evaluateString to define your automaton.");
        System.out.println("Enter the input string:");
        Scanner io = new Scanner(System.in);
        String input = io.nextLine();
        Automata M = new Automata();
        if (input.equals("")) System.out.println("The input string is empty!");
        else {
            try {
                M.evaluateString(input);
            } catch (NullPointerException npe) {
                System.out.println("\nThe input string has alphabet foreign to the language.");
            }
            }
        io.close();
        
    }
    void evaluateString(String input) {
        //Override this method to define your automaton
        State q0 = new State("q0",true,false);
        State q1 = new State("q1",false,true);
        State q2 = new State("q2",false,false);
        //Alphabet are mapped to array indices, or a map can be used
        HashMap<String,Integer> alphabet=new HashMap<>();
        alphabet.put("a",0);
        alphabet.put("b",1);
        q0.transition=new State[2]; //Two transitions for this FA/state (depending on DFA or NFA)        
        q0.transition[0]=q1;
        q0.transition[1]=q2;
        q1.transition=new State[2];
        q1.transition[0]=q1;
        q1.transition[1]=q1;
        q2.transition=new State[2];
        q2.transition[0]=q2;
        q2.transition[1]=q2;

        //Evaluation of string
        State currentState=q0;
        for (int i=0; i<input.length(); i++) {
            if (currentState.isStart) System.out.print("->"+currentState.name+" -"+input.charAt(i)+"-> ");
            else System.out.print(" -"+input.charAt(i)+"-> ");
            String readSymbol = String.valueOf(input.charAt(i));
            currentState=currentState.transition[alphabet.get(readSymbol)];
            System.out.print(currentState.name);
            if (currentState.isFinal) {
                System.out.print("*");
                if (i==input.length()-1) System.out.println("\n"+currentState.description);
            }     
        }

    } 
}
class State {
    String name;
    String description="Default Description";
    State[] transition; 
    boolean isFinal=false;
    boolean isStart=false;
    State(String name, boolean isStart, boolean isFinal) {
        this.name=name;
        this.isStart=isStart;
        this.isFinal=isFinal;
    }
}