import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCard {

    Player p = new Player("Player1", Color.RED, 1);

    @Test
    public void testCard() {
        Card afghanistan = new Card("afghanistan", "cavalry", false, "");

        assertEquals("afghanistan", afghanistan.getTerritory());
        assertEquals("cavalry", afghanistan.getTroopType());
        assert(!afghanistan.isWild());
    }

    @Test
    public void TestRemoveCardFromHand() {
        Card a = new Card("afghanistan", "cavalry", false, "");
        Card b = new Card("alaska", "infantry", false, "");
        Card c = new Card("alberta", "cavalry", false, "");

        p.addCard(a);
        p.addCard(b);
        p.addCard(c);

        assertEquals(a.getTroopType(), "cavalry");

        //System.out.println(p.cards);
        //p.playCardSet(p.cards);

        Card w = new Card("", "", true, "");
        assert(w.isWild());
        //System.out.println(p.cards);
    }
}
