import org.junit.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDice {

    Paint red = Color.RED;
    Dice d = new Dice(red);

    @Test
    public void testGetColor() {
        assertEquals(d.getColor(), Color.RED);
    }


    @Test
    public void testSetValue() {
        d.setValue();
        assert(d.getValue() >= 1 && d.getValue() <= 6);
    }
}
