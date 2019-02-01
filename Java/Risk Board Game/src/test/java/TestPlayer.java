import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPlayer {

    Board b;
    Player player1;
    Player player2;

    @Before
    public void setUp(){
        b = new Board();

        player1 = new Player("Player1", Color.RED, 1);
        player2 = new Player("Player2", Color.BLUE, 2);
    }

    @Test
    public void testToString() {
        assert(player1.toString().equals("Player1: Player1"));
    }


    @Test
    public void testGetName() {
        assert(player1.getName().equals("Player1"));
        assert(player2.getName().equals("Player2"));
    }


    @Test
    public void testGetColor() {
        assertEquals(Color.RED, player1.getColor());
        assertEquals(Color.BLUE, player2.getColor());
    }


    @Test
    public void testGetId() {
        assertEquals(player1.getId(), 1);
    }


    @Test
    public void testGetTroopsToPlace() {
        player1.addTroopsToPlace(10);
        assertEquals(player1.getTroopsToPlace(), 10);
    }


    @Test
    public void testCheckCardSets() {
        Deck d = new Deck();
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.checkCardSets();
        assertTrue( player1.getNumberOfCardSets() > 0);
    }

    @Test
    public void testCheckSetsArrayList(){
        Card a = new Card("alberta", "infantry", false, "");
        Card b = new Card("china", "infantry", false, "");
        Card c = new Card("japan", "infantry", false, "");
        ArrayList<Card> set = new ArrayList<Card>();
        set.add(a);
        set.add(b);
        set.add(c);
        assertTrue(player1.checkCardSets(set));

        a = new Card("alberta", "cavalry", false, "");
        b = new Card("china", "cavalry", false, "");
        c = new Card("japan", "cavalry", false, "");
        set = new ArrayList<Card>();
        set.add(a);
        set.add(b);
        set.add(c);
        assertTrue(player1.checkCardSets(set));

        a = new Card("alberta", "infantry", true, "");
        b = new Card("china", "cavalry", false, "");
        c = new Card("japan", "infantry", false, "");
        set = new ArrayList<Card>();
        set.add(a);
        set.add(b);
        set.add(c);
        assertTrue(player1.checkCardSets(set));
    }

    @Test
    public void testSetId() {
        /*player2.setId(2);
        assertEquals(player2.getId(), 2);*/
    }

    @Test
    public void testGetReinforcements() {

        Card afghanistan = new Card("afghanistan", "cavalry", false, "/Afghanistan.png");
        Card alaska = new Card("alaska", "cavalry", false, "/Alaska.png");
        Card alberta = new Card("alberta", "cavalry", false, "/Alberta.png");

        for(int i = 0; i < 21; i++) {
            player1.addTotalTerritories();
        }

        player1.setReinforcements(b);
        Assertions.assertEquals(player1.getReinforcements(), 7);
        ArrayList<Card> set = new ArrayList<Card>();
        set.add(afghanistan);
        set.add(alaska);
        set.add(alberta);
        player1.playCardSet(set);

        Assertions.assertEquals(player1.getReinforcements(), 11);


    }

    @Test
    public void testNeedToPlayCard(){
        Deck d = new Deck();
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        player1.addCard(d.drawCard());
        assertTrue(player1.needToPlayCard());
    }

}
