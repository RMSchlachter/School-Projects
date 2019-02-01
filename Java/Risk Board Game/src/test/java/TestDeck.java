import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeck {

    Deck d = new Deck();
    Stack<Card> deck = d.getDeck();

    @Test
    public void testDeck() {
        for(Card c : deck) {
            assert(c != null);
        }
    }

    @Test
    public void testAddToBottom(){
        d = new Deck();
        ArrayList<Card> playerHand = new ArrayList<Card>();
        for (int i = 0; i < 6; ++i)
            playerHand.add(d.drawCard());
        int count = 0;
        for(Card card : deck)
            ++count;
        assertEquals(44, count);
    }
}
