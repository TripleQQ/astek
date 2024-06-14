package com.sample.miniproject.seviceImp;// may need to change to your package

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


public class MineSweeperProblemMain {
	static boolean[][] mines = null;
	static boolean[][] revealedfield = null;
	static char[][] minefield = null;
	static int size = 0;
	static int numOfFieldLeft = 0;
	static char[] rowheader = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
	static String charToInt = "ABCDEFGHIJ";
	static String[] colArr = { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	public static void main(String[] args) {
		//main function
		System.out.println(ConstantUtil.CommonMSG.INITAL_MSG);
		Scanner sc = new Scanner(System.in);

		MineSweeperGame(ConstantUtil.Func.STEP_1_INITIAL_GAME, sc);
	}

	public static void MineSweeperGame(String step, Scanner sc) {

		switch (step) {
		case ConstantUtil.Func.STEP_1_INITIAL_GAME:
			//
			initialMineSweeperGame(sc);

			break;
		case ConstantUtil.Func.STEP_2_PLAY:
			playgame(sc);
			break;

		case ConstantUtil.Func.STEP_3_GAME_FAIL:
			reloadgame(ConstantUtil.CommonMSG.FAILURE_MSG, sc);

			break;
		case ConstantUtil.Func.STEP_4_GAME_SUCCESS:
			reloadgame(ConstantUtil.CommonMSG.WINNER_MSG, sc);
			break;
		}

	}
   //reload the game
	private static void reloadgame(String msg, Scanner sc) {
		displayMsg(msg);
		mines = null;
		revealedfield = null;
		minefield = null;
		size = 0;
		numOfFieldLeft = 0;
		if (sc.hasNext()) {
			String s = sc.nextLine();// need to print out?
			MineSweeperGame(ConstantUtil.Func.STEP_1_INITIAL_GAME, sc);
		}

	}

	private static void playgame(Scanner sc) {
		displayCurMineFiled();
		String userInput = getSelectedFiled(sc);
		int row = charToInt.indexOf(userInput.charAt(0));
		int col = Integer.parseInt(userInput.substring(1));
		if (isGameOver(row, col - 1)) {
			MineSweeperGame(ConstantUtil.Func.STEP_3_GAME_FAIL, sc);
		}
		if (numOfFieldLeft == 0) {
			MineSweeperGame(ConstantUtil.Func.STEP_4_GAME_SUCCESS, sc);

		} else {
			playgame(sc);
		}

	}
	
	public static void setMines(int row ,int col ) {
		mines[row][col]=true;

	}
   // to check if the selected field contain mine?
	private static boolean isGameOver(int row, int col) {
		if (mines[row][col]) {
			return true;
		} else {
			// set revealed=true so the system will display the number next time
			updateField(row, col);

		}
		return false;
	}

	private static void updateField(int row, int col) {
		if (minefield[row][col] == '0') {
			revealedfield[row][col] = true;
			numOfFieldLeft--;
			uncoveredSurroundingField(row, col);
		} else {
			revealedfield[row][col] = true;
			numOfFieldLeft--;
		}

	}

	private static void uncoveredSurroundingField(int row, int col) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int newRow = row + i;
				int newCol = col + j;
				// make sure the reveal field does not contain mine and no been revealed
				if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && !revealedfield[newRow][newCol]
						&& !mines[newRow][newCol]) {
					revealedfield[newRow][newCol] = true;
					numOfFieldLeft--;
					if (minefield[newRow][newCol] == '0') {
						uncoveredSurroundingField(newRow, newCol);
					}
				}
			}
		}

	}

	private static String getSelectedFiled(Scanner sc) {
		displayMsg(ConstantUtil.CommonMSG.STEP_4_SELECT_SQUARE_MSG);
		String userInput = sc.nextLine();
		userInput = userInput==null?"":userInput.toUpperCase();
		if (validateUserInput(userInput)) {
			return userInput;
		} else {
			displayMsg(ConstantUtil.ErrorMSG.ERROR_MSG_INCORRECT_INPUT);
			return getSelectedFiled(sc);
		}

	}

	private static boolean validateUserInput(String userInput) {
		if (userInput != null && userInput.length() > 1 && userInput.length() <= 3) {
			char c = userInput.charAt(0);

			userInput = userInput.substring(1);
			if (isCorrectRow(c) && isCorrectCol(userInput)) {
				return true;
			}

		}
		return false;
	}

	private static boolean isCorrectCol(String userInput) {
		if (isNumeric(userInput)) {
			try {
				int i = Integer.parseInt(userInput);
				if (i > 0 && i <= size) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	private static boolean isCorrectRow(char c) {
		for (int i = 0; i < size; i++) {
			if (rowheader[i] == c)
				return true;
		}
		return false;
	}
    //display current revealed fields
	private static void displayCurMineFiled() {
		displayMsg(ConstantUtil.CommonMSG.STEP_3_DISPLAY_MINE_FIELD_MSG);
		// print col header
		for (int i = 0; i <= size; i++) {
			System.out.printf("%3s ", colArr[i]);
		}
		System.out.println();//next line
		for (int row = 0; row < size; row++) {
			System.out.printf("%3c ", rowheader[row]);
			for (int col = 0; col < size; col++) {
				if (revealedfield[row][col]) {
					// been revealed;default false
					System.out.printf("%3c ", minefield[row][col]);
				} else {
					System.out.printf("%3c ", '_');
				}
			}
			System.out.println();//next line
		}

	}


	// get size and number of mines to load the fields
	private static void initialMineSweeperGame(Scanner sc) {
		size = getMinefieldSizeOrMineSize(sc, ConstantUtil.CommonMSG.STEP_1_ENTER_GRID_MSG, ConstantUtil.GRID_NUM,
				ConstantUtil.MAX_GRIP_NUM, ConstantUtil.MIN_GRIP_NUM);
		double maxMine = size * size * ConstantUtil.MAX_MINE_NUM;
		int mineNum = getMinefieldSizeOrMineSize(sc, ConstantUtil.CommonMSG.STEP_2_ENTER_MINE_MSG,
				ConstantUtil.MINE_NUM, maxMine, ConstantUtil.MIN_MINE_NUM);
		numOfFieldLeft = size * size - mineNum;
		revealedfield = new boolean[size][size];
		mines = new boolean[size][size];
		minefield = new char[size][size];
		//load the fields
		placeMineField(mines, mineNum, size);
		loadMinefield(minefield, size);
		
		//lets play 
		MineSweeperGame(ConstantUtil.Func.STEP_2_PLAY, sc);

	}



	private static void loadMinefield(char[][] minefield, int size) {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				minefield[row][col] = calculateNumOfMineSurround(row, col, size);
			}
		}

	}
    //calculate num of mine surrounding (9 cells )
	private static char calculateNumOfMineSurround(int row, int col, int maxSize) {
		// 9 field, 3 row(row-1 to row+1) and 3 col(col-1 to col+1),
		int count = 0;
		if (mines[row][col])
			return 'X';// cell with mine
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int newRow = row + i;
				int newCol = col + j;
				if (newRow >= 0 && newRow < maxSize && newCol >= 0 && newCol < maxSize && mines[newRow][newCol]) {
					count++;
				}
			}
		}
		return (char) ('0' + count);
	}
    //placed mines in field
	private static void placeMineField(boolean[][] mines, int mineNum, int maxValue) {
		Random r = new Random();
		int i = 0;
		while (i < mineNum) {
			int row = r.nextInt(maxValue);
			int col = r.nextInt(maxValue);
			if (!mines[row][col]) {
				mines[row][col] = true;
				i++;// mine place successfully
			}
		}

	}

	public static int getMinefieldSizeOrMineSize(Scanner sc, String msg, String gridNum, double max, int min) {
		displayMsg(msg);
		String userInput = sc.nextLine();
		if (ValidateGripAndMine(userInput, gridNum, max, min)) {
			return Integer.parseInt(userInput);
		} else {// re-process the function
			return getMinefieldSizeOrMineSize(sc, msg, gridNum, max, min);
		}

	}

	public static boolean ValidateGripAndMine(String userInput, String keyStr, double maxMineNum, int minNum) {
		boolean result = false;
		if (isNumeric(userInput)) {
			int num = Integer.parseInt(userInput);
			if (num <= minNum) {
				displayMsg(keyStr.equals(ConstantUtil.GRID_NUM)
						? String.format(ConstantUtil.ErrorMSG.ERROR_MSG_MAX_MIN_GRIP, "Minimum", minNum)
						: String.format(ConstantUtil.ErrorMSG.ERROR_MSG_MIN_MINE, minNum));
			} else if (num > maxMineNum) {
				displayMsg(keyStr.equals(ConstantUtil.GRID_NUM)
						? String.format(ConstantUtil.ErrorMSG.ERROR_MSG_MAX_MIN_GRIP, "Maximum", (int) maxMineNum)
						: String.format(ConstantUtil.ErrorMSG.ERROR_MSG_MAX_MINE,
								(int) (ConstantUtil.MAX_MINE_NUM * 100)));
			} else {
				result = true;
			}
		} else {
			displayMsg(ConstantUtil.ErrorMSG.ERROR_MSG_INCORRECT_INPUT);
		}
		return result;

	}

	private static boolean isNumeric(String userInput) {
		if (userInput == null) {
			return false;
		}
		return ConstantUtil.NUMERIC_PATTERN.matcher(userInput).matches();
	}

	private static void displayMsg(String step1EnterGridMsg) {
		System.out.print(step1EnterGridMsg);

	}
	// for Junit;
	public static int getNumberOfMines() {
		int count =0;
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(mines[i][j]) {
					count++;
				}
			}
		}
		return count;
	}
	
	public static int getSize() {
		return size;
	}

}



///// move to same class file
class ConstantUtil {

	public static class Func {
		public static final String STEP_1_INITIAL_GAME = "INITIAL_GAME";
		public static final String STEP_2_PLAY = "PLAY_GAME";
		public static final String STEP_3_GAME_FAIL = "GAME_FAIL";
		public static final String STEP_4_GAME_SUCCESS = "GAME_SUCCESS";
	}

	public static class CommonMSG {
		public static final String INITAL_MSG = "Welcome to Minesweeper! \n";
		public static final String STEP_1_ENTER_GRID_MSG = "Enter the size of the grid (e.g. 4 for a 4x4 grid): \n";
		public static final String STEP_2_ENTER_MINE_MSG = "Enter the number of mines to place on the grid (maximum is 35% of the total squares): \n";
		public static final String STEP_3_DISPLAY_MINE_FIELD_MSG = "Here is your minefield \n";
		public static final String STEP_4_SELECT_SQUARE_MSG = "Select a square to reveal (e.g. A1): ";
		public static final String STEP_5_DISPLAY_SELECTED_SQUARE_MSG = "This square contains 0 adjacent mines. \n";
		public static final String STEP_6_DISPLAY_UPDATE_MINE_FIELD_MSG = "Here is your updated minefield: \n";
		public static final String WINNER_MSG = "Congratulations, you have won the game! \n Press any key to play again... \n";
		public static final String FAILURE_MSG = "Oh no, you detonated a mine! Game over.\nPress any key to play again...\n``` \n";

	}

	public static class ErrorMSG {
		public static final String ERROR_MSG_INCORRECT_INPUT = "Incorrect input. \n";
		public static final String ERROR_MSG_MAX_MIN_GRIP = "%s size of grip is %d \n";
		public static final String ERROR_MSG_MAX_MINE = "Maximum number is  %d%% of total sqaures. \n";
		public static final String ERROR_MSG_MIN_MINE = "There must be at least %d mine. \n";
	}

	public static final double MAX_GRIP_NUM = 10.0;
	public static final int MIN_GRIP_NUM = 2;
	public static final double MAX_MINE_NUM = 0.35;
	public static final int MIN_MINE_NUM = 1;
	public static final String GRID_NUM = "Grid";
	public static final String MINE_NUM = "Mine";
	public static final Pattern NUMERIC_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
}
