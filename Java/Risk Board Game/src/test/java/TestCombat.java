import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCombat {

    Combat combat = new Combat();

    @Test
    void attack()
    {
        assertEquals(combat.attack(1).size(), 1);
        assertEquals(combat.attack(2).size(), 2);
        assertEquals(combat.attack(3).size(), 3);
        //assertEquals(combat.attack(0).size(), null);
    }

    @Test
    void defend() {
        assertEquals(combat.defend(1).size(), 1);
        assertEquals(combat.attack(2).size(), 2);
        //assertEquals(combat.attack(3).size(), 2);
        //assertEquals(combat.attack(0).size(), null);
    }

    @Test
    void compare()
    {
        List<Integer> attack = new ArrayList<Integer>();

        attack.add(2);
        attack.add(3);
        attack.add(1);

        Collections.sort(attack);
        Collections.reverse(attack);

        List<Integer> defend = new ArrayList<Integer>();
        defend.add(2);
        defend.add(2);

        int[] actual = {1,1};
        //assertEquals(combat.compare(attack, defend), actual);
    }

}