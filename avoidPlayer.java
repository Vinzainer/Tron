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
    static int[][] grid = new int[30][20];

    public static void main(String args[]) {
        initializeGrid();
        Scanner in = new Scanner(System.in);
        String move = "";
        String lastMove = "";

        // game loop
        while (true) {
            int iteration = 0;
            int[] myPosition = new int[2];
            int[] otherPosition = new int[2];

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
                else if(i == 1){
                    otherPosition[0] = X1;
                    otherPosition[0] = Y1;
                }
            }

            updateGrid(myPosition, otherPosition);
            move = avoidPlayer(myPosition, lastMove);
            System.out.println(move);
            lastMove = move;
        }
    }

    public static void initializeGrid(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j] = 0;
            }
        }
    }
    public static void updateGrid(int[] myPosition, int[] otherPosition){
        grid[myPosition[0]][myPosition[1]] = 1;
        grid[otherPosition[0]][otherPosition[1]] = 2;
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

    public static String avoidPlayer(int[] myPosition, String lastMove){
        if(lastMove == "RIGHT"){
            if(grid[myPosition[0] + 1][myPosition[1]] != 0){
                return avoidWall(myPosition, "UP");
            }
        }
        else if(lastMove == "LEFT"){
            if(grid[myPosition[0] - 1][myPosition[1]] != 0){
                return avoidWall(myPosition, "UP");
            }
        }
        else if(lastMove == "DOWN"){
            if(grid[myPosition[0]][myPosition[1] + 1] != 0){
                return avoidWall(myPosition, "RIGHT");
            }
        }
        else if(lastMove == "UP"){
            if(grid[myPosition[0]][myPosition[1] - 1] != 0){
                return avoidWall(myPosition, "RIGHT");
            }
        }

        return avoidWall(myPosition, lastMove);
    }
}
