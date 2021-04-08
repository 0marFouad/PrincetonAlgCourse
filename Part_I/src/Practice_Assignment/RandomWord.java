package Practice_Assignment;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args){
        String winner = "";
        int i = 1;
        while(!StdIn.isEmpty()){
            String read = StdIn.readString();
            if(StdRandom.bernoulli(1.0/((double)i))){
                winner = read;
            }
            i++;
        }
        StdOut.println(winner);
    }
}
