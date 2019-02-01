import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTerritory {

    Board b = new Board();

    Color red = new Color(255, 255, 255);
    Color blue = new Color(0, 0, 255);

    Player player1 = new Player("one", red, 1);
    Player player2 = new Player("two", blue, 2);

    Continent africa = b.getAfrica();
    Continent europe = b.getEurope();
    Continent southAmerica = b.getSouthAmerica();
    Continent northAmerica = b.getNorthAmerica();
    Continent asia = b.getAsia();
    Continent australia = b.getAustralia();



    @Test
    public void testGetName() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            assert(alaska.getName().equals("alaska"));
        } catch (Exception e) {

        }
    }

    @Test
    public void testGetContinent() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            assert(alaska.getContinent().equals("north america"));
        } catch (Exception e) {

        }
    }

    @Test
    public void testGetOwner() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            alaska.setOwner(player1);
            assertEquals(alaska.getOwner(), player1);
        } catch (Exception e) {

        }
    }


    @Test
    public void testAddArmy() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            alaska.addArmy(10);
            assertEquals(alaska.armySize(), 10);
            alaska.removeArmy(5);
            assertEquals(alaska.armySize(), 5);

            alaska.addArmy(46);
            assertEquals(alaska.armySize(), 46);
            alaska.addArmy();
            assertEquals(alaska.armySize(), 47);
        } catch (Exception e) {

        }
    }


    @Test
    public void testAttackable() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            alaska.setOwner(player1);
            Territory victim = northAmerica.getTerritory("northwest territory");
            victim.setOwner(player2);
            assert(alaska.attackable(victim));
        } catch (Exception e) {

        }
    }


    @Test
    public void testMoveable() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            alaska.setOwner(player1);
            Territory moveTo = northAmerica.getTerritory("northwest territory");
            moveTo.setOwner(player1);
            assert(alaska.moveable(moveTo));
        } catch (Exception e) {

        }
    }


    @Test
    public void testAdjacentTerritories() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            assert (alaska.adjacentTerritories.contains("northwest territory"));
            assert (alaska.adjacentTerritories.contains("alberta"));
            assert (alaska.adjacentTerritories.contains("kamchatka"));
        } catch (Exception e) {

        }
    }


    @Test
    public void testGetOwnerColor() {
        try {
            Territory alaska = northAmerica.getTerritory("alaska");
            alaska.setOwner(player1);
            assertEquals(alaska.getOwnerColor(), red);
        } catch (Exception e) {

        }
    }

}
