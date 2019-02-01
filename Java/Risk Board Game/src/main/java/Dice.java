import java.awt.*;
import java.util.Random;

public class Dice
{
    Random random = new Random();

    Color color;
    int value;

    public Dice(Paint color)
    {
        this.color = (Color) color;
        this.value = 0;
    }

    public Color getColor()
    {
        return (Color) color;
    }

    public void setValue()
    {
        int val = random.nextInt(6) + 1;
        this.value = val;
    }

    public int getValue()
    {
        return value;
    }
}
