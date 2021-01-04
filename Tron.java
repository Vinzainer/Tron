import java.util.*;
import java.io.*;
import java.math.*;

//Tron class to test locally
class Tron{

    public static void main(String args[]) {
      Board board = new Board(20, 30);
      List<String> moves;

      board.setCase(1, 18, 29);
      board.setCase(1, 18, 28);
      board.setCase(1, 18, 27);

      board.setCase(2, 19, 29);
      board.setCase(2, 19, 28);

      Minimax test = new Minimax();

      test.constructTree(board, 6);


      System.out.println("Final choice : " + test.compute());
      Tree tree = test.getTree();
      tree.printTree();
    }
}

//Player class for Codingame
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

            test.constructTree(board, 3);

            System.out.println(test.compute());
        }
    }
}

//Store the board
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
        grid[i][j] = player;
        Integer[] position = new Integer[2];
        position[0] = i;
        position[1] = j;
        headPosition.put(player, position);
    }

    public int getCase(int i, int j){
        if(i >= 0 && i < height && j >= 0 && j < width){
            return this.grid[i][j];
        }
        else{
            return -1;
        }
    }

    public void playMove(int player, String direction){
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

    public Map<Integer, Integer[]> getHeadPosition(){
      return headPosition;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}

//Store one possibility of a board and its children
class Node {
    Board board;
    boolean isMaxPlayer;
    int score;
    String move;
    String choice;
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

    public void setChoice(String choice){
      this.choice = choice;
    }

    public String getChoice(){
      return choice;
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

//Store the tree of possible board
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

      System.out.println("Move :" + root.getMove());
      System.out.println("Score :" +root.getScore());
      System.out.println("Choice :" + root.getChoice());

      children = root.getChildren();

      for(Node child: children){
        printTree(child);
      }
    }
}

//Compute the minimax algorithm and the heuristic
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

    public int floodfill(Board board, int i, int j, int score){
        if(board.getCase(i, j) == 0 || board.getCase(i, j) == 1){
            score += 1;

            board.setCase(1, i, j);

            floodfill(board, i+1, j, score);
            floodfill(board, i, j+1, score);
            floodfill(board, i-1, j, score);
            floodfill(board, i, j-1, score);
        }

        return score;
    }

    public int dist(int x0, int y0, int x1, int y1){
        return Math.round(Math.abs(x1 - x0) + Math.abs(y1 - y0));
    }

    public int reachable(Board board, int x0, int y0, int x1, int y1){
        int score = 0;

        for(int i = 0; i < board.getHeight(); i++){
            for(int j = 0; j < board.getWidth(); j++){
                if(dist(x0, y0, i, j) < dist(x1, y1, i, j)){
                    score += 1;
                }
                else if(dist(x0, y0, i, j) > dist(x1, y1, i, j)){
                    score -= 1;
                }
            }
        }

        return score;
    }

    //Try to get closer to enemy
    public int heuristic(Node node){
        int score = 0;
        Board board = node.getBoard();
        List<String> availableMovesMaxPlayer = board.getAvailableMoves(1);
        List<String> availableMovesMinPlayer = board.getAvailableMoves(2);

        Map<Integer, Integer[]> position = board.getHeadPosition();
        Integer[] player = position.get(1);
        Integer[] op = position.get(2);

        if(node.getChildren().size() == 0){
          if(availableMovesMaxPlayer.size() > 0){
              return 100;
          }
          else if(availableMovesMinPlayer.size() > 0){
              return -100;
          }
        }

        score -= Math.round(Math.abs(op[0] - player[0]) + Math.abs(op[1] - player[1]));
        //score += floodfill(board, player[0], player[1], 0);
        //score += reachable(board, player[0], player[1], op[0], op[1]);

        return score;
    }

    public int compute(Node node, int depth, boolean isMaxPlayer){
      if(depth == 0 || node.getChildren().size() == 0){
        node.setScore(heuristic(node));
        return heuristic(node);
      }

      if(isMaxPlayer){
        int value = -1000000;
        List<Node> children = node.getChildren();
        String choice = "";
        Collections.shuffle(children);
        for(Node child: children){
          int test = compute(child, depth-1, false);
          child.setScore(test);

          if(value < test){
            value = test;
            choice = child.getMove();
          }
        }

        node.setChoice(choice);

        return value;
      }
      else{
        int value = 1000000;
        List<Node> children = node.getChildren();
        String choice = "";
        Collections.shuffle(children);
        for(Node child: children){
          int test = compute(child, depth-1, false);
          child.setScore(test);

          if(value > test){
            value = test;
            choice = child.getMove();
          }
        }

        node.setChoice(choice);
        return value;
      }
    }

    public String compute(){
        compute(tree.getRoot(), depth-1, true);
        Node root =  tree.getRoot();
        String choice = root.getChoice();
        return choice;
    }

    public Tree getTree(){
      return tree;
    }
}
