import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class TestGame {

    Game game;
    Board board;

    @Before
    public void setUp() {
        board = new Board();
        game = new Game();
    }

    @Test
    public void testAddPlayer() throws Exception {
        game.addPlayer("mitch");
        game.addPlayer("logan");
        assertEquals(2, game.getPlayers().size());
    }

    @Test
    public void testCheckTurn() throws Exception {
        game.addPlayer("mitch");
        game.addPlayer("logan");
        assertEquals("mitch", game.checkWhosTurn().getName());
    }

    @Test
    public void testNextTurn() throws Exception {
        game.addPlayer("mitch");
        game.addPlayer("logan");
        game.nextTurn();
        assertEquals("logan", game.checkWhosTurn().getName());
    }

    @Test
    public void testGetPhase(){
        assertEquals("reinforcement", game.getPhase());
    }

    @Test
    public void testNextPhase(){
        game.nextPhase();
        assertEquals("attack", game.getPhase());
    }

    @Test
    public void testGetPlayers() throws Exception {
        game.addPlayer("mitch");
        game.addPlayer("logan");
        game.addPlayer("ryan");
        game.addPlayer("rudy");
        ArrayList<Player> test = game.getPlayers();
        assertEquals(4,test.size());
        assertEquals("logan",test.get(1).getName());
    }

    @Test
    public void testInitalTroopCount() throws Exception {
        game.addPlayer("mitch");
        game.addPlayer("logan");
        game.addPlayer("ryan");
        game.addPlayer("rudy");
        game.setInitialTroopCount();
        assertEquals(30, game.getPlayers().get(0).getTroopsToPlace());
    }

    @Test
    public void allReinforcementsPlaced() throws Exception {
        game.addPlayer("mitch");
        game.addPlayer("logan");
        game.addPlayer("ryan");
        game.addPlayer("rudy");
        game.setInitialTroopCount();

        assertFalse(game.allReinforcementsPlaced());
    }

    @Test
    public void testAllTerritoriesOccupied(){
        assertFalse(game.allTerritoriesOccupied(board));
    }

    @Test
    public void testGetTerritory(){
        assertTrue(game.getTerritory(board, "congo").getName().equalsIgnoreCase("congo"));
    }

}
