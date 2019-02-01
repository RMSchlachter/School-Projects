import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class TestContinent {

    Continent northAmerica;

    @Before
    public void setUp() {

        Territory alaska = new Territory("alaska", "north america");
        Territory alberta = new Territory("alberta", "north america");
        Territory centralAmerica = new Territory("central america", "north america");
        Territory easternUnitedStates = new Territory("eastern united states", "north america");
        Territory greenland = new Territory("greenland", "north america");
        Territory northwestTerritory = new Territory("northwest territory", "north america");
        Territory ontario = new Territory("ontario", "north america");
        Territory quebec = new Territory("quebec", "north america");
        Territory westernUnitedStates = new Territory("western united states", "north america");

        northAmerica = new Continent("north america", 5, alaska, alberta, centralAmerica,
                easternUnitedStates, greenland, northwestTerritory, ontario, quebec, westernUnitedStates);
    }

    @Test
    public void makeNewContinent(){
        assertTrue(northAmerica != null);
    }

    @Test
    public void testGetName(){
        assertTrue(northAmerica.getName().equals("north america"));
    }

    @Test
    public void testGetTerritorySize(){
        assertEquals(9, northAmerica.getTerritories().size());
    }

    @Test
    public void testGetTerritory() throws Exception {
        assertEquals("alaska", northAmerica.getTerritory("alaska").getName());
        assertEquals("greenland", northAmerica.getTerritory("greenland").getName());
        assertEquals("quebec", northAmerica.getTerritory("quebec").getName());
    }

    @Test
    public void testGetReinforcementVal(){
        assertEquals(5, northAmerica.getReinforcemntVal());
    }
}
