import java.util.ArrayList;
import java.util.Arrays;

public class Continent {

    private String name;
    private int reinforcementVal;
    private Player owner;
    private ArrayList<Territory> territories;


    public Continent(String name, int val, Territory... territories){
        this.name = name;
        this.reinforcementVal = val;
        this.territories = new ArrayList(Arrays.asList(territories));
        this.owner = null;
    }


    public ArrayList<Territory> getTerritories() {
        return territories;
    }


    public String getName() {
        return name;
    }


    public int getReinforcemntVal() {
        return reinforcementVal;
    }


    public Territory getTerritory(String name) throws Exception{

        Territory returnTerritory = null;
        for (Territory territory : territories) {
            if (territory.getName().equals(name))
                returnTerritory = territory;
        }
        if (returnTerritory == null)
            throw new Exception("invalid territory name");

        return returnTerritory;
    }


    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public Player getOwner() {
        return owner;
    }
}
