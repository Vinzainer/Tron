import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Tron{

    public static void main(String args[]) {
      Board board = new Board(20, 30);
      List<String> moves;

      board.setCase(1, 14, 29);
      board.setCase(2, 8, 16);
      board.printBoard();

      moves = board.getAvailableMoves(1);

      /*
      for(String move: moves){
        System.out.println(move);
      }
      */

      Minimax test = new Minimax();
      test.constructTree(board, 10);
    }
}

class Board {
    private int height;
    private int width;
    private int[][] grid;
    private Map<Integer, Integer[]> headPosition;

    public Board(int height, int width){
        this.height = height;
        this.width = width;
        grid = new int[height][width];
        headPosition = new HashMap<Integer, Integer[]>();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                grid[i][j] = 0;
            }
        }
    }

    public void printBoard(){
      System.out.println();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }

      System.out.println();
    }

    public void setCase(int player, int i, int j){
        //System.out.println(i + " " + j);
        grid[i][j] = player;
        Integer[] position = new Integer[2];
        position[0] = i;
        position[1] = j;
        headPosition.put(player, position);
    }

    public int getCase(int i, int j){
        return this.grid[i][j];
    }

    public void playMove(int player, String direction){
      //System.out.println(player);
      Integer[] head = headPosition.get(player);
      int i = head[0];
      int j = head[1];

      switch(direction){
        case "UP":
          setCase(player, i-1, j);
          break;
        case "DOWN":
          setCase(player, i+1, j);
          break;
        case "RIGHT":
          setCase(player, i, j+1);
          break;
        case "LEFT":
          setCase(player, i, j-1);
          break;
        default:
          System.out.println("ERROR : Wrong move");
      }
    }

    public List<String> getAvailableMoves(int player){
        List<String> moves = new ArrayList<String>();
        Integer[] head = headPosition.get(player);
        int i = head[0];
        int j = head[1];

        if((i-1 >= 0) && grid[i-1][j] == 0){
            moves.add("UP");
        }

        if((i+1 < height) && grid[i+1][j] == 0){
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

    public Board getBoardCopy(){
      Board newBoard = new Board(height, width);

      for(int i = 0; i < height; i++){
          for(int j = 0; j < width; j++){
              newBoard.grid[i][j] = grid[i][j];
          }
      }

      for(Map.Entry<Integer, Integer[]> entry: headPosition.entrySet()){
        newBoard.headPosition.put(entry.getKey(), entry.getValue());
      }

      return newBoard;
    }
}

class Node {
    Board board;
    boolean isMaxPlayer;
    int score;
    List<Node> children;

    public Node(Board board, boolean isMaxPlayer){
      this.board = board;
      this.isMaxPlayer = isMaxPlayer;
      children = new ArrayList<Node>();
    }

    public void addChild(Node newNode){
      children.add(newNode);
    }

    public void setScore(int score){
      this.score = score;
    }

    public Board getBoard(){
      return board;
    }

    public boolean getIsMaxPlayer(){
      return isMaxPlayer;
    }

    public int getScore(){
      return score;
    }

    public List<Node> getChildren(){
      return children;
    }
}

class Tree {
    Node root;

    public void setRoot(Node root){
      this.root = root;
    }

    public Node getRoot(){
      return root;
    }
}

class Minimax {
    Tree tree;

    public void constructTree(Board board, int depth){
      tree = new Tree();
      Node root = new Node(board, true);
      tree.setRoot(root);
      constructTree(root, depth);
    }

    public void constructTree(Node parentNode, int depth){
      if(depth > 0){
        boolean isMaxPlayer = parentNode.getIsMaxPlayer();
        Board board = parentNode.getBoard();
        List<String> availableMoves = board.getAvailableMoves(isMaxPlayer ? 1 : 2);

        boolean isChildMaxPlayer = (!isMaxPlayer);

        System.out.println(isChildMaxPlayer);
        board.printBoard();

        for(String move: availableMoves){
          Board newBoard = board.getBoardCopy();

          System.out.println(isChildMaxPlayer ? 1 : 2);
          System.out.println(move);

          newBoard.playMove(isChildMaxPlayer ? 1 : 2, move);
          Node newNode = new Node(newBoard, isChildMaxPlayer);
          parentNode.addChild(newNode);

          constructTree(newNode, depth - 1);
        }
      }
    }
}
