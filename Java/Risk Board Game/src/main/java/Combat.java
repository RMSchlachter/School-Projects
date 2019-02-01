//We should be using the dice object
//Ryan and Rudy

//import javafx.scene.paint.Color;
//import javafx.scene.paint.Paint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Combat
{

    //ATTACKING DICE
    public List<Integer> attack(int soldiers)
    {
        Paint blue = Color.BLUE;

        if(soldiers > 2)
        {
            Dice dice = new Dice((java.awt.Paint) blue);
            Dice dice1 = new Dice((java.awt.Paint) blue);
            Dice dice2 = new Dice((java.awt.Paint) blue);

            dice.setValue();
            dice1.setValue();
            dice2.setValue();

            List<Integer> results = new ArrayList<Integer>();
            results.add(dice.getValue());
            results.add(dice1.getValue());
            results.add(dice2.getValue());

            Collections.sort(results);
            Collections.reverse(results);

            return results;

        }

        else if(soldiers == 2)
        {
            Dice dice = new Dice((java.awt.Paint) blue);
            Dice dice1 = new Dice((java.awt.Paint) blue);

            dice.setValue();
            dice1.setValue();

            List<Integer> results = new ArrayList<Integer>();
            results.add(dice.getValue());
            results.add(dice1.getValue());

            Collections.sort(results);
            Collections.reverse(results);

            return results;

        }

        else if(soldiers == 1)
        {
            Dice dice = new Dice((java.awt.Paint) blue);
            dice.setValue();

            List<Integer> results = new ArrayList<Integer>();
            results.add(dice.getValue());

            return results;
        }

        else
            return null;
    }

    //DEFENDING DICE
    public List<Integer> defend(int soldiers)
    {
        Paint red = Color.RED;

        if(soldiers > 1)
        {
            Dice dice = new Dice((java.awt.Paint) red);
            Dice dice1 = new Dice((java.awt.Paint) red);

            dice.setValue();
            dice1.setValue();

            List<Integer> results = new ArrayList<Integer>();
            results.add(dice.getValue());
            results.add(dice1.getValue());

            Collections.sort(results);
            Collections.reverse(results);

            return results;
        }

        else if(soldiers == 1)
        {
            Dice dice = new Dice((java.awt.Paint) red);
            dice.setValue();

            List<Integer> results = new ArrayList<Integer>();
            results.add(dice.getValue());

            return results;
        }

        else
            return null;
    }

    public int[] compare(List<Integer> attacker, List<Integer> defender)
    {
        int[] casualties = new int[2];
        casualties[0] = 0;
        casualties[1] = 0;

        //START WORKING ON THIS
        if(attacker.size() <= defender.size())
        {
            for(int i = 0; i < attacker.size(); i++)
            {
                if(defender.get(i) >= attacker.get(i))
                    casualties[0]++;

                else
                    casualties[1]++;
            }
        }

        else if(defender.size() < attacker.size())
        {
            for(int i = 0; i < defender.size(); i++)
            {
                if(defender.get(i) >= attacker.get(i))
                    casualties[0]++;

                else
                    casualties[1]++;
            }
        }

        return casualties;
    }

    public java.net.URL[] printURL(List<Integer> neutral)
    {
        java.net.URL[] urls = new java.net.URL[neutral.size()];

        for(int i = 0; i < neutral.size(); i++)
        {
            switch (neutral.get(i))
            {
                case 1:
                    urls[i] = getClass().getResource("/Dice1.png");
                    break;
                case 2:
                    urls[i] = getClass().getResource("/Dice2.png");
                    break;
                case 3:
                    urls[i] = getClass().getResource("/Dice3.png");
                    break;
                case 4:
                    urls[i] = getClass().getResource("/Dice4.png");
                    break;
                case 5:
                    urls[i] = getClass().getResource("/Dice5.png");
                    break;
                case 6:
                    urls[i] = getClass().getResource("/Dice6.png");
                    break;
            }
        }
        
        return urls;
    }

}
