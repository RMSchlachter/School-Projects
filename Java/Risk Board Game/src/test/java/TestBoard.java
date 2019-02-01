import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class TestBoard {

    Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void isBoardMade(){
        assertTrue(board != null);
    }

    @Test
    public void testNorthAmericaCreation(){
        assertTrue(board.getNorthAmerica() != null);
    }

    @Test
    public void testNothAmericaTerritories(){
        assertEquals(9, board.getNorthAmerica().getTerritories().size());
    }

    @Test
    public void testSouthAmericaCreation(){
        assertTrue(board.getSouthAmerica() != null);
    }

    @Test
    public void testSouthAmericaTerritories(){
        assertEquals(4, board.getSouthAmerica().getTerritories().size());
    }

    @Test
    public void testEuropeCreation(){
        assertTrue(board.getEurope() != null);
    }

    @Test
    public void testEuropeTerritories(){
        assertEquals(7, board.getEurope().getTerritories().size());
    }

    @Test
    public void testAsiaCreation(){
        assertTrue(board.getAsia() != null);
    }

    @Test
    public void testAsiaTerritories(){
        assertEquals(12, board.getAsia().getTerritories().size());
    }

    @Test
    public void testAfricaCreation(){
        assertTrue(board.getAfrica() != null);
    }

    @Test
    public void testAfricaTerritories(){
        assertEquals(6, board.getAfrica().getTerritories().size());
    }

    @Test
    public void testAustraliaCreation(){
        assertTrue(board.getAustralia() != null);
    }

    @Test
    public void testAustraliaTerritories(){
        assertEquals(4, board.getAustralia().getTerritories().size());
    }

    @Test
    public void testGetContinents(){
        assertEquals(6, board.getContinents().size());
    }

    @Test
    public void testGetContinent(){
        assertEquals("north america", board.getContinent("north America").getName());
    }
}
