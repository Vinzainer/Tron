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

      Minimax test = new Minimax();
      test.constructTree(board, 1);

      Tree tree = test.getTree();

      //tree.printTree();
      String choice = tree.checkWin();
      System.out.println(choice);
    }
}

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        Board board = new Board(20, 30);

        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3).
            for (int i = 0; i < N; i++) {
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

                if(P == i){
                    board.setCase(1, Y1, X1);
                }
                else{
                    board.setCase(2, Y1, X1);
                }
            }

            Minimax test = new Minimax();

            test.constructTree(board, 4);

            System.out.println(test.compute());
        }
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
    String move;
    List<Node> children;

    public Node(Board board, boolean isMaxPlayer){
      this.board = board;
      this.isMaxPlayer = isMaxPlayer;
      children = new ArrayList<Node>();
    }

    public Node(Board board, boolean isMaxPlayer, String move){
      this(board, isMaxPlayer);
      this.move = move;
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

    public String getMove(){
      return move;
    }

    public void setMove(String move){
      this.move = move;
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

    public void printTree(){
      printTree(root);
    }

    private void printTree(Node root){
      List<Node> children = new ArrayList<Node>();

      root.getBoard().printBoard();
      System.out.println(root.getMove());
      System.out.println(root.getScore());
      children = root.getChildren();

      for(Node child: children){
        printTree(child);
      }
    }
}

class Minimax {
    Tree tree;
    int depth;

    public void constructTree(Board board, int depth){
      this.depth = depth;
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

        for(String move: availableMoves){
          Board newBoard = board.getBoardCopy();
          newBoard.playMove(isMaxPlayer ? 1 : 2, move);
          Node newNode = new Node(newBoard, isChildMaxPlayer, move);
          parentNode.addChild(newNode);

          constructTree(newNode, depth - 1);
        }
      }
    }

    public int heuristic(Node node){
        int score = 0;
        Board board = node.getBoard();
        List<String> availableMovesMaxPlayer = board.getAvailableMoves(1);
        List<String> availableMovesMinPlayer = board.getAvailableMoves(2);

        if(availableMovesMaxPlayer.size() == 0){
            score = -100;
        }
        else if(availableMovesMinPlayer.size() == 0){
            score = 100;
        }

        score += availableMovesMaxPlayer.size() - availableMovesMinPlayer.size();
        return score;
    }

    public int compute(Node node, int depth, boolean isMaxPlayer){
      if(depth == 0 || node.getChildren().size() == 0){
        node.setScore(heuristic(node));
        return heuristic(node);
      }

      if(isMaxPlayer){
        int value = -1000;
        List<Node> children = node.getChildren();

        for(Node child: children){
          value = Math.max(value, compute(child, depth-1, false));
        }

        node.setScore(value);
        return value;
      }
      else{
        int value = 1000;
        List<Node> children = node.getChildren();

        for(Node child: children){
          value = Math.min(value, compute(child, depth-1, true));
        }
        node.setScore(value);
        return value;
      }
    }

    public String compute(){
      int score = compute(tree.getRoot(), depth, true);
      int max = -1000;
      String move = "";
      List<Node> children = tree.getRoot().getChildren();

      for(Node child: children){
        if(child.getScore() > max){
          max = child.getScore();
          move = child.getMove();
        }
      }

      return move;
    }

    public Tree getTree(){
      return tree;
    }
}
