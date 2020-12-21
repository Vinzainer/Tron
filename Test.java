import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Test{

    public static void main(String args[]) {
      Board board = new Board(20, 30);
      List<String> moves;

      board.setCase(1, 12, 22);
      board.setCase(1, 11, 22);
      board.printBoard();
      moves = board.getAvailableMoves(1, 12, 22);

      for(String s: moves){
        System.out.println(s);
      }
    }
}

class Board {
    private int height;
    private int width;
    private int[][] grid;

    public Board(int height, int width){
        this.height = height;
        this.width = width;
        grid = new int[height][width];

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                grid[i][j] = 0;
            }
        }
    }

    public void printBoard(){

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public void setCase(int player, int i, int j){
        grid[i][j] = player;
    }

    public int getCase(int i, int j){
        return this.grid[i][j];
    }

    public List<String> getAvailableMoves(int player, int i, int j){
        List<String> moves = new ArrayList<String>();

        if((i+1 < height) && grid[i+1][j] == 0){
            moves.add("UP");
        }

        if((i-1 >= 0) && grid[i-1][j] == 0){
            moves.add("DOWN");
        }

        if((j+1 < width) && grid[i][j+1] == 0){
            moves.add("RIGHT");
        }

        if((j-1 >= 0) && grid[i][j-1] == 0){
            moves.add("LEFT");
        }

        return moves;
    }
}
