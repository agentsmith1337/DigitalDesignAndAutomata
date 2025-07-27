import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class EpsilonNFAEngine {
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
        *  Epsilon is represented by e 
        *  Make sure you have understanding of the subject matter 
        *  This is not fundamentally different from the NFAEngine class, 
        *  but had to be build separetely due to complexity.
        */
        q0.transition.put('a',new Vector<FAState>(Arrays.asList(q0,q1)));
        q0.transition.put('b', new Vector<FAState>(Arrays.asList(q1)));
        q0.transition.put('e',new Vector<FAState>(Arrays.asList(q1)));
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
            ENFAThread firstThread = new ENFAThread(q0, input, "->");
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
class ENFAThread extends Thread {
    private volatile boolean running=true;
    FAState currentState;
    String input;
    String path;
    ENFAThread(FAState currentState, String input, String path) {
        this.currentState= new FAState(currentState);
        this.input=new String(input);
        this.path=new String (path);
    }
    @Override
    public void run() {
        //At each transition, preserve the first path and create another thread for other paths. 
        //Instead of printing the transitions on the go, create a separate string per thread
        for (int i=0; i<input.length()&&running==true; i++) {
            if (currentState.transition==null || currentState.transition.get(input.charAt(i))==null && currentState.transition.get('e')==null) {
                path+=" -> DUMP";
                running=false;
                break;
            } 
           
           
            path+= currentState.name;
            if (currentState.isFinal) path+="*";
            // path+=" -"+input.charAt(i)+"> " ;
            FAState previousState = currentState;
            currentState=currentState.transition.get(input.charAt(i)).get(0);
            if (previousState.transition.get('e')!=null) {
                
                ENFAThread[][] EpsilonClosure = new ENFAThread[previousState.transition.get('e').size()][];
            for (int k=0; k<previousState.transition.get('e').size(); k++) {

                int epsSize = previousState.transition.get('e').get(k).transition.get(input.charAt(i)).size();
                EpsilonClosure[k] = new ENFAThread[epsSize];

                for (int m=0; m<epsSize; m++) {
                    String pass_path=path+" -eps-> "+previousState.transition.get('e').get(k).name+" -"+input.charAt(i)+"-> ";
                    EpsilonClosure[k][m] = new ENFAThread(previousState.transition.get('e').get(k).transition.get(input.charAt(i)).get(m), input.substring(i+1), pass_path);
                    EpsilonClosure[k][m].setName(input.charAt(i)+" from eps from "+previousState.name);
                    EpsilonClosure[k][m].start();
                }
                path+=" -"+input.charAt(i)+"-> ";
            }
            } else {
            path+=" -"+input.charAt(i)+"> " ;
            ENFAThread[] otherTransitions = new ENFAThread[previousState.transition.get(input.charAt(i)).size()];
            for (int k=1; k<previousState.transition.get(input.charAt(i)).size(); k++) {
                otherTransitions[k] = new ENFAThread(previousState.transition.get(input.charAt(i)).get(k), input.substring(i+1), path);
                otherTransitions[k].setName(input.charAt(i)+" from "+previousState.name);
                otherTransitions[k].start();
                
                 }
            
            }
               
                            
        }
        //No string left to process
        path+=currentState.name;
        if (currentState.isFinal) path+="*";
        System.out.println(currentThread().getName()+":"+path+" END");
    }
}