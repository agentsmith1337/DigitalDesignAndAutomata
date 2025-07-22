import java.lang.Thread;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Vector;
import java.util.Arrays;

public class NFAEngine {
    public static void main(String[] args) {
        /*
         * Define your NFA states here. Add corresponding transition containers below. 
         * In case of no transition containers, The state will either be a DUMP state or an unreachable state.
         */
        FAState q0 = new FAState("q0");
        FAState q1 = new FAState("q1");
        FAState q2 = new FAState("q2");
        FAState q3 = new FAState("q3");
        q3.isFinal=true;
        q0.isStart=true;
        q0.transition = new HashMap<Character,Vector<FAState>>();
        q1.transition = new HashMap<Character,Vector<FAState>>();
        q2.transition = new HashMap<Character,Vector<FAState>>();
        q3.transition = new HashMap<Character,Vector<FAState>>();
        /* 
        *  Implement the transition function here
        *  Note: This does not support epsilon transitions. 
        *  For Epsilon Transitions, refer to EpsilonNFAEngine class
        */
        q0.transition.put('a',new Vector<FAState>(Arrays.asList(q0,q1)));
        q0.transition.put('b', new Vector<FAState>(Arrays.asList(q1)));
        q1.transition.put('a', new Vector<FAState>(Arrays.asList(q2)));
        q1.transition.put('b', new Vector<FAState>(Arrays.asList(q3))); 
        q2.transition.put('a', new Vector<FAState>(Arrays.asList(q0,q2)));
        q2.transition.put('b', new Vector<FAState>(Arrays.asList(q3)));
        q3.transition.put('a', new Vector<FAState>(Arrays.asList(q3)));
        q3.transition.put('b', new Vector<FAState>(Arrays.asList(q3)));

        try (Scanner io = new Scanner(System.in)) {
            System.out.println("NFA Verification with Java");
            System.out.println("Use the exemplar specification in the code to define NFA.");
            System.out.println("Please enter the string to verify NFA:");
            String input = io.nextLine();
            NFAThread firstThread = new NFAThread(q0, input, "->");
            firstThread.setName("Starting");
            firstThread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
class FAState {
    String name;
    String description;
    HashMap<Character,Vector<FAState>> transition;
    boolean isFinal =false;
    boolean isStart=false;
    public FAState(String name) {
        this.name=name;
    }
    public FAState(FAState s) {
        this.name=s.name;
        this.description=s.description;
        this.transition=s.transition;
    }
}
class NFAThread extends Thread {
    private volatile boolean running=true;
    FAState currentState;
    String input;
    String path;
    NFAThread(FAState currentState, String input, String path) {
        this.currentState= new FAState(currentState);
        this.input=new String(input);
        this.path=new String (path);
    }
    @Override
    public void run() {
        //At each transition, preserve the first path and create another thread for other paths. 
        //Instead of printing the transitions on the go, create a separate string per thread
        for (int i=0; i<input.length()&&running==true; i++) {
            if (currentState.transition==null || currentState.transition.get(input.charAt(i))==null) {
                path+=" -> DUMP";
                // System.out.println(currentThread().getName()+":"+path);
                running=false;
                break;
            } 
           
           
            path+= currentState.name;
            if (currentState.isFinal) path+="*";
            path+=" -"+input.charAt(i)+"> " ;
            FAState previousState = currentState;
            currentState=currentState.transition.get(input.charAt(i)).get(0);
            NFAThread[] otherTransitions = new NFAThread[previousState.transition.get(input.charAt(i)).size()];
            for (int k=1; k<previousState.transition.get(input.charAt(i)).size(); k++) {
                otherTransitions[k] = new NFAThread(previousState.transition.get(input.charAt(i)).get(k), input.substring(i+1), path);
                otherTransitions[k].setName(input.charAt(i)+" from "+previousState.name);
                otherTransitions[k].start();
                }   
                            
        }
        //No string left to process
        path+=currentState.name;
        if (currentState.isFinal) path+="*";
        System.out.println(currentThread().getName()+":"+path+" END");
    }
}