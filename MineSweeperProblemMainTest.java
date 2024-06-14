package com.sample.miniproject.seviceImp;// may need to change to your package

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class MineSweeperProblemMainTest {

	private static MineSweeperProblemMain mainService;
	Scanner scanner = mock(Scanner.class);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		// mainService= new MineSweeperProblemMain();
		mainService = mock(MineSweeperProblemMain.class);
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testUserInput() {
		assertFalse(mainService.ValidateGripAndMine("D", ConstantUtil.GRID_NUM, ConstantUtil.MAX_GRIP_NUM,
				ConstantUtil.MIN_GRIP_NUM));
		assertFalse(mainService.ValidateGripAndMine("1", ConstantUtil.GRID_NUM, ConstantUtil.MAX_GRIP_NUM,
				ConstantUtil.MIN_GRIP_NUM));
		assertFalse(mainService.ValidateGripAndMine("2", ConstantUtil.GRID_NUM, ConstantUtil.MAX_GRIP_NUM,
				ConstantUtil.MIN_GRIP_NUM));
		assertTrue(mainService.ValidateGripAndMine("3", ConstantUtil.GRID_NUM, ConstantUtil.MAX_GRIP_NUM,
				ConstantUtil.MIN_GRIP_NUM));
		assertFalse(mainService.ValidateGripAndMine("12", ConstantUtil.GRID_NUM, ConstantUtil.MAX_GRIP_NUM,
				ConstantUtil.MIN_GRIP_NUM));

		assertFalse(mainService.ValidateGripAndMine("D", ConstantUtil.MINE_NUM, 5.0, ConstantUtil.MIN_MINE_NUM));
		assertFalse(mainService.ValidateGripAndMine("0", ConstantUtil.MINE_NUM, 5.0, ConstantUtil.MIN_MINE_NUM));
		assertFalse(mainService.ValidateGripAndMine("6", ConstantUtil.MINE_NUM, 5.0, ConstantUtil.MIN_MINE_NUM));
		assertTrue(mainService.ValidateGripAndMine("5", ConstantUtil.MINE_NUM, 5.1, ConstantUtil.MIN_MINE_NUM));
	}
	

	@Test
    public void testMineLoading() {
        // Set up input stream with test data
        String input = "5\n3\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        try {
        	MineSweeperProblemMain.MineSweeperGame(ConstantUtil.Func.STEP_1_INITIAL_GAME, scanner);
        	}catch(NoSuchElementException e) {
        		//to cater for no input exception
        	}

        assertEquals(5, MineSweeperProblemMain.getSize());
        assertEquals(3, MineSweeperProblemMain.getNumberOfMines());
    }

	/*
	 * @Test public void testMineLoading() {
	 * when(MineSweeperProblemMain.getMinefieldSizeOrMineSize(scanner,
	 * ConstantUtil.CommonMSG.STEP_1_ENTER_GRID_MSG, ConstantUtil.GRID_NUM,
	 * ConstantUtil.MAX_GRIP_NUM, ConstantUtil.MIN_GRIP_NUM)).thenReturn(5);
	 * when(MineSweeperProblemMain.getMinefieldSizeOrMineSize(scanner,
	 * ConstantUtil.CommonMSG.STEP_2_ENTER_MINE_MSG, ConstantUtil.MINE_NUM,
	 * anyDouble(), ConstantUtil.MIN_MINE_NUM)).thenReturn(3);
	 * when(MineSweeperProblemMain.getSelectedFiled(scanner)).thenReturn("A1");
	 * when(MineSweeperProblemMain.isGameOver(anyInt(),any())).thenReturn(true);
	 * mainService.MineSweeperGame(ConstantUtil.Func.STEP_1_INITIAL_GAME, scanner);
	 * //getSelectedFiled assertEquals(5, mainService.getNumberOfMines());
	 * assertEquals(3, mainService.getSize()); //initialMineSweeperGame
	 * 
	 * }
	 */


}
