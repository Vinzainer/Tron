import java.util.*;
import java.io.*;
import java.math.*;
import java.util.Random;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String move = "";

        // game loop
        while (true) {
            int iteration = 0;

            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

            }

            String direction = getDirection(move);
            move = randomChoice(direction);
            System.out.println(move);
        }
    }

    public static String getDirection(String move){
        if(move == "DOWN" || move == "UP"){
            return "Y";
        }
        else if(move == "LEFT" || move == "RIGHT"){
            return "X";
        }
        else{
            return "ALL";
        }
    }

    public static String randomChoice(String direction){
        Random rand = new Random();
        String[] choice = {"UP", "DOWN", "LEFT", "RIGHT"};
        String[] choiceX = {"LEFT", "RIGHT"};
        String[] choiceY = {"UP", "DOWN"};

        if(direction == "X"){
            int i = rand.nextInt(2);
            return choiceY[i];
        }
        else  if(direction == "Y"){
            int i = rand.nextInt(2);
            return choiceX[i];
        }
        else{
            int i = rand.nextInt(4);
            return choice[i];
        }
    }
}
