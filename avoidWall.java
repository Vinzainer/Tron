import java.util.*;
import java.io.*;
import java.math.*;
import java.util.Random;

import javax.swing.text.Position;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String move = "";
        String lastMove = "";

        // game loop
        while (true) {
            int iteration = 0;
            int[] myPosition = new int[2];

            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                if(i == 0){
                    myPosition[0] = X1;
                    myPosition[1] = Y1;
                }
            }

            move = avoidWall(myPosition, lastMove);
            System.out.println(move);
            lastMove = move;
        }
    }

    public static String avoidWall(int[] myPosition, String lastMove){
        if((myPosition[0] == 0 && lastMove == "LEFT") || (myPosition[0] == 29 && lastMove == "RIGHT")){
            if(myPosition[1] < 10){
                return "DOWN";
            }
            else{
                return "UP";
            }
        }
        else if((myPosition[1] == 0 && lastMove == "UP") || (myPosition[1] == 19 && lastMove == "DOWN")){
            if(myPosition[0] < 15){
                return "RIGHT";
            }
            else{
                return "LEFT";
            }
        }
        else{
            if(lastMove == ""){
                Random rand = new Random();
                String[] choice = {"UP", "DOWN", "RIGHT", "LEFT"};
                return choice[rand.nextInt(4)];
            }
            else{
                return lastMove;
            }
        }
    }
}
