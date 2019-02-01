//package risk;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Territory {

    private String name;
    private String continent;
    private Player owner;
    private int localTroops;    // total amount of troops on Territory

    public ArrayList<String> adjacentTerritories;
    //public Card card;


    public Territory(String name, String continent) {
        this.name = name;
        this.continent = continent;
        this.owner = null;
        this.adjacentTerritories = new ArrayList<String>();

        getAdjacentTerritories();
    }


    public void setOwner(Player p) {
        this.owner = p;
        owner.addTotalTerritories();
    }


    public String getName() {
        return name;
    }


    public String getContinent() {
        return continent;
    }


    public Player getOwner() {
        return owner;
    }
    
    public Color getOwnerColor() {
    		if(this.getOwner() == null) {
    			return Color.WHITE;
    		}
    		else return this.getOwner().getColor();
    }


    public void addArmy(int addme) {
        localTroops += addme;
        owner.setTotalTroops(addme);
    }


    public void addArmy() {
        localTroops++;
        owner.setTotalTroops(1);;
    }


    public void removeArmy(int cas) {
        localTroops = localTroops - cas;
        owner.removeTotalTroops(cas);
    }


    public int armySize() {
        return localTroops;
    }


    public boolean attackable(Territory toAttack) {
        // attacking Territory is different than defending Territory
        // and the defending Territory is in the attacking territories
        // list of possible countries to attack
        return (owner != toAttack.owner)
                && (adjacentTerritories.contains(toAttack.name));
    }


    public boolean moveable(Territory toMove) {
    	return (owner == toMove.owner)
                && (adjacentTerritories.contains(toMove.name));
    }


    public void getAdjacentTerritories() {

        /////////////////////// BEGIN NORTH AMERICA ///////////////////////
        //9 total territories
        int id;
        if(this.continent.equals("north america")) {
            // 1. alaska
            if (name.equals("alaska")) {
                id = 1;
                adjacentTerritories.addAll(Arrays.asList("northwest territory", "alberta", "kamchatka"));
            }

            // 2. alberta
            else if (name.equals("alberta")) {
                id = 2;
                adjacentTerritories.addAll(Arrays.asList("alaska", "northwest territory",
                        "ontario", "western united states"));
            }

            // 3. central america
            else if (name.equals("central america")) {
                id = 3;
                adjacentTerritories.addAll(Arrays.asList("western united states",
                        "eastern united states", "venezuela"));
            }

            // 4. eastern united states
            else if (name.equals("eastern united states")) {
                id = 4;
                adjacentTerritories.addAll(Arrays.asList("ontario",
                        "quebec", "western united states", "central america"));
            }

            // 5. greenland
            else if (name.equals("greenland")) {
                id = 5;
                adjacentTerritories.addAll(Arrays.asList("northwest territory",
                        "ontario", "quebec", "iceland"));
            }

            // 6. northwest Territory
            else if (name.equals("northwest territory")) {
                id = 6;
                adjacentTerritories.addAll(Arrays.asList("alaska",
                        "alberta", "ontario", "greenland"));
            }

            // 7. ontario
            else if (name.equals("ontario")) {
                id = 7;
                adjacentTerritories.addAll(Arrays.asList("alberta",
                        "northwest territory", "greenland", "quebec",
                        "eastern united states", "western united states"));
            }

            // 8. quebec
            else if (name.equals("quebec")) {
                id = 8;
                adjacentTerritories.addAll(Arrays.asList("eastern united states",
                        "greenland", "ontario"));
            }

            // 9. western united states
            else if (name.equals("western united states")) {
                id = 9;
                adjacentTerritories.addAll(Arrays.asList("alberta",
                        "ontario", "eastern united states", "central america"));
            }
        }


        /////////////////////// BEGIN SOUTH AMERICA ///////////////////////
        // 4 total territories
        else if(this.continent.equals("south america")) {
            // 1. argentina
            if (name.equals("argentina")) {
                id = 1;
                adjacentTerritories.addAll(Arrays.asList("brazil", "peru"));
            }

            // 2. brazil
            else if (name.equals("brazil")) {
                id = 2;
                adjacentTerritories.addAll(Arrays.asList("argentina", "peru",
                        "venezuela"));
            }

            // 3. peru
            else if (name.equals("peru")) {
                id = 3;
                adjacentTerritories.addAll(Arrays.asList("argentina", "brazil",
                        "venezuela"));
            }

            // 4. venezuela
            else if (name.equals("venezuela")) {
                id = 4;
                adjacentTerritories.addAll(Arrays.asList("peru", "brazil"));
            }
        }


        /////////////////////// BEGIN EUROPE ///////////////////////
        // 7 total territories
        else if(continent.equals("europe")) {
            // 1. great britain (2, 4, 3, 7)
            if (name.equals("great britain")) {
                id = 1;
                adjacentTerritories.addAll(Arrays.asList("iceland", "scandinavia",
                        "northern europe", "western europe"));
            }

            // 2. iceland (greenland, 1, 4)
            else if (name.equals("iceland")) {
                id = 2;
                adjacentTerritories.addAll(Arrays.asList("greenland", "great britain",
                        "scandinavia"));
            }

            // 3. northern europe (4, 6, 5, 7, 1)
            else if (name.equals("northern europe")) {
                id = 3;
                adjacentTerritories.addAll(Arrays.asList("scandinavia", "ukraine",
                        "southern europe", "western europe", "great britain"));
            }

            // 4. scandinavia (1, 2, 3, 5)
            else if (name.equals("scandinavia")) {
                id = 4;
                adjacentTerritories.addAll(Arrays.asList("great britain", "iceland",
                        "northern europe", "southern europe"));
            }

            // 5. southern europe (north africa, middle east, egypt, 3, 6, 7)
            else if (name.equals("southern europe")) {
                id = 5;
                adjacentTerritories.addAll(Arrays.asList("north africa", "middle east",
                        "egypt", "northern europe", "ukraine", "western europe"));
            }

            // 6. ukraine (ural, afghanistan, middle east, 3, 4, 5)
            else if (name.equals("ukraine")) {
                id = 6;
                adjacentTerritories.addAll(Arrays.asList("ural", "middle east",
                        "northern europe", "scandinavia", "southern europe"));
            }

            // 7. western europe (north africa, 1, 3, 5)
            else if (name.equals("western europe")) {
                id = 7;
                adjacentTerritories.addAll(Arrays.asList("ural", "middle east",
                        "northern europe", "scandinavia", "southern europe"));
            }
        }


        /////////////////////// BEGIN AFRICA ///////////////////////
        // 6 total territories
        else if(this.continent.equals("africa")) {
            // 1. congo (2, 3, 5, 6)
            if (name.equals("congo")) {
                id = 1;
                adjacentTerritories.addAll(Arrays.asList("east africa", "egypt",
                        "north africa", "south africa"));
            }

            // 2. east africa (middle east, 1, 3, 4, 5, 6)
            else if (name.equals("east africa")) {
                id = 2;
                adjacentTerritories.addAll(Arrays.asList("middle east", "congo",
                        "egypt", "madagascar", "north africa", "south africa"));
            }

            // 3. egypt (scandinavia, middle east, 2, 5)
            else if (name.equals("egypt")) {
                id = 3;
                adjacentTerritories.addAll(Arrays.asList("scandinavia", "middle east",
                        "east africa", "north africa"));
            }

            // 4. madagascar (2, 6)
            else if (name.equals("madagascar")) {
                id = 4;
                adjacentTerritories.addAll(Arrays.asList("east africa", "south africa"));
            }

            // 5. north africa (brazil, western europe, southern europe, 1, 2, 3)
            else if (name.equals("north africa")) {
                id = 5;
                adjacentTerritories.addAll(Arrays.asList("brazil", "western europe",
                        "southern europe", "congo", "east africa", "egypt"));
            }

            // 6. south africa (1, 2, 4)
            else if (name.equals("south africa")) {
                id = 5;
                adjacentTerritories.addAll(Arrays.asList("congo", "east africa",
                        "madagascar"));
            }
        }


        /////////////////////// BEGIN ASIA ///////////////////////
        // 12 total territories
        else if(this.continent.equals("asia")) {

            // 1. afghanistan (ukraine, 11, 2, 3, 7)
            if (name.equals("afghanistan")) {
                id = 1;
                adjacentTerritories.addAll(Arrays.asList("ukraine", "ural",
                        "china", "india", "middle east"));
            }

            // 2. china (1, 11, 10, 8, 9, 3)
            else if (name.equals("china")) {
                id = 2;
                adjacentTerritories.addAll(Arrays.asList("afghanistan", "ural",
                        "siberia", "mongolia", "siam", "india"));
            }

            // 3. india (7, 1, 2, 9)
            else if (name.equals("india")) {
                id = 3;
                adjacentTerritories.addAll(Arrays.asList("middle east", "afghanistan",
                        "china", "siam"));
            }

            // 4. irkutsk (10, 12, 6, 8)
            else if (name.equals("irkutsk")) {
                id = 4;
                adjacentTerritories.addAll(Arrays.asList("siberia", "yakutsk",
                        "kamchatka", "mongolia"));
            }

            // 5. japan (6, 8)
            else if (name.equals("japan")) {
                id = 5;
                adjacentTerritories.addAll(Arrays.asList("kamchatka", "mongolia"));
            }

            // 6. kamchatka (alaska, 12, 4, 8, 5)
            else if (name.equals("kamchatka")) {
                id = 6;
                adjacentTerritories.addAll(Arrays.asList("alaska", "yakutsk",
                        "irkutsk", "mongolia", "japan"));
            }

            // 7. middle east (southern europe, ukraine, 1, 3, egypt, east africa)
            else if (name.equals("middle east")) {
                id = 7;
                adjacentTerritories.addAll(Arrays.asList("southern europe", "ukraine",
                        "afghanistan", "india", "egypt", "east africa"));
            }

            // 8. mongolia (10, 4, 6, 5, 2)
            else if (name.equals("mongolia")) {
                id = 8;
                adjacentTerritories.addAll(Arrays.asList("siberia", "irkutsk",
                        "kamchatka", "japan", "china"));
            }

            // 9. siam (indonesia, 2, 3)
            else if (name.equals("siam")) {
                id = 9;
                adjacentTerritories.addAll(Arrays.asList("indonesia", "china",
                        "india"));
            }

            // 10. siberia (11, 2, 8, 4, 12)
            else if (name.equals("siberia")) {
                id = 10;
                adjacentTerritories.addAll(Arrays.asList("ural", "china",
                        "mongolia", "irkutsk", "yakutsk"));
            }

            // 11. ural
            else if (name.equals("ural")) {
                id = 11;
                adjacentTerritories.addAll(Arrays.asList("siberia", "irkutsk",
                        "kamchatka", "japan", "china"));
            }

            // 12. yakutsk (4, 6, 10)
            else if (name.equals("yakutsk")) {
                id = 12;
                adjacentTerritories.addAll(Arrays.asList("irkutsk", "kamchatka",
                        "siberia"));
            }
        }


        /////////////////////// BEGIN AUSTRALIA ///////////////////////
        // 4 total territories
        else if(this.continent.equals("australia")) {

            // 1. eastern australia (4, 3)
            if (name.equals("eastern australia")) {
                id = 1;
                adjacentTerritories.addAll(Arrays.asList("western australia", "new guinea"));
            }

            // 2. indonesia (siam, 3, 4)
            else if (name.equals("indonesia")) {
                id = 2;
                adjacentTerritories.addAll(Arrays.asList("siam", "new guinea",
                        "western australia"));
            }

            // 3. new guinea (1, 2, 4)
            else if (name.equals("new guinea")) {
                id = 3;
                adjacentTerritories.addAll(Arrays.asList("eastern australia", "indonesia",
                        "western australia"));
            }

            // 4. western australia (1, 2, 3)
            else if (name.equals("western australia")) {
                id = 4;
                adjacentTerritories.addAll(Arrays.asList("eastern australia", "indonesia",
                        "new guinea"));
            }
        }
    }
}
