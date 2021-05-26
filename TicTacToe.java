import java.util.Scanner;

/**
* The TicTacToe program implements an application that
* will always win or tie the user in TicTacToe 
* using recursion.
*
* @author  Marcus A. Mosley
* @version 1.0
* @since   2021-05-26
*/
public class TicTacToe {
  /**
  * The Main method receives input and checks viability.
  */
  public static void main(String[] args) {
    // main stub, get user input here
    boolean boardFull = false;
    boolean checkWinnerX = false;
    boolean checkWinnerO = false;

    Scanner input = new Scanner(System.in);
    String[] board = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    System.out.println("Welcome to tic tac toe!\n");

    do {
      printBoard(board);
      System.out.println("\nWhich space would you like to the X? ");
      if (input.hasNextInt()) {
        int space = input.nextInt();

        if (space > 9 || space < 0) {
          System.out.println("That spot is invalid!");
        } else if (board[space - 1].equalsIgnoreCase("X")
            || board[space - 1].equalsIgnoreCase("O")) {
          System.out.println("That spot's taken!");
        } else if (isNumeric(board[space - 1])) {
          board[space - 1] = "X";
          checkWinnerX = winOrLost(board, "X");
          if (checkWinnerX == true) {
            System.out.println("\nX has won.");
            break;
          }
          // place a function call here to get the best move for O
          if (isFull(board) == false) {
            int goodComputerMove = findMove(board);
            board[goodComputerMove] = "O";
            
            checkWinnerO = winOrLost(board, "O");
            if (checkWinnerO == true) {
              System.out.println("\nO has won.");
              break;
            }
          }
          
        } else {
          System.out.println("Error");
          break;
        }
      } else {
        System.out.println("Error");
        break;
      }

      checkWinnerX = winOrLost(board, "X");
      checkWinnerO = winOrLost(board, "O");
      if (checkWinnerX == true) {
        System.out.println("\nX has won.");
        break;
      } else if (checkWinnerO == true) {
        System.out.println("\nO has won.");
        break;
      }

      boardFull = isFull(board);
    } while (boardFull == false);
    
    input.close();
    printBoard(board);
    System.out.println("\nGame Over.");
  }

  /**
  * The winOrLost method checks for a winner.
  */
  public static boolean winOrLost(String[] board, String playerCheck) {
    // returns true or false for the player to see if they won
    if ((board[0] == playerCheck && board[1] == playerCheck && board[2] == playerCheck)
        || (board[3] == playerCheck && board[4] == playerCheck && board[5] == playerCheck)
        || (board[6] == playerCheck && board[7] == playerCheck && board[8] == playerCheck)
        || (board[0] == playerCheck && board[3] == playerCheck && board[6] == playerCheck)
        || (board[1] == playerCheck && board[4] == playerCheck && board[7] == playerCheck)
        || (board[2] == playerCheck && board[5] == playerCheck && board[8] == playerCheck)
        || (board[0] == playerCheck && board[4] == playerCheck && board[8] == playerCheck)
        || (board[2] == playerCheck && board[4] == playerCheck && board[6] == playerCheck)) {
      return true;
    } else {
      return false;
    }
  }

  /**
  * The miniMax method uses recursion to define the move
  * with the highest probability of winning.
  */
  public static int miniMax(String[] board, int depth, Boolean isMax) {
    // calculates the best next move for computer based on current board
    if (winOrLost(board, "O")) {
      return 1;
    } else if (winOrLost(board, "X")) {
      return -1;
    } else if (isFull(board)) {
      return 0;
    }
    
    if (isMax) {
      int bestScore = -1000;
      for (int boardCounter = 0; boardCounter < board.length; boardCounter++) {
        if (isNumeric(board[boardCounter])) {
          board[boardCounter] = "O";
          int score = miniMax(board, depth + 1, !isMax);
          board[boardCounter] = String.valueOf(boardCounter + 1);
          bestScore = Math.max(score, bestScore);
        }
      }
      return bestScore;
    } else {
      int bestScore = 1000;
      for (int boardCounter = 0; boardCounter < board.length; boardCounter++) {
        if (isNumeric(board[boardCounter])) {
          board[boardCounter] = "X";
          int score = miniMax(board, depth + 1, !isMax);
          board[boardCounter] = String.valueOf(boardCounter + 1);
          bestScore = Math.min(score, bestScore);
        }
      }
      return bestScore;
    }
  }

  /**
  * The findMove method finds thed best move.
  */
  public static int findMove(String[] board) {
    int bestScore = -1000;
    int bestMove = 0;
    for (int boardCounter = 0; boardCounter < board.length; boardCounter++) {
      // accessing each element of array
      if (isNumeric(board[boardCounter])) {
        board[boardCounter] = "O";
        int score = miniMax(board, 0, false);
        board[boardCounter] = String.valueOf(boardCounter + 1);
        if (score > bestScore) {
          bestScore = score;
          bestMove = boardCounter;
        }
      } 
    }
    return bestMove;
  }

  /**
  * The isFull method checks if the array is full.
  */
  public static boolean isFull(String[] board) {
    // returns whether board is full or not
    boolean full = true;
    for (int counter = 0; counter < board.length; counter++) {
      if (isNumeric(board[counter])) {
        full = false;
        break;
      }
    }
    return full;
  }

  /**
  * The printBoard method prints the array.
  */
  public static void printBoard(String[] board) {
    // prints current game state
    System.out.println("----+---+----");
    for (int count = 0; count < 9; count++) {
      if (count == 2 || count == 5 || count == 8) {
        System.out.print("| " + board[count] + " |\n");
        System.out.println("----+---+----");
      } else {
        System.out.print("| " + board[count] + " ");
      }
    }
  }

  /**
  * The isNumeric method checks if the element is an integer.
  */
  public static boolean isNumeric(String strNum) {
    if (strNum == null) {
      return false;
    }
    try {
      double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }
}