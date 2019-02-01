import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Game {

    private static ArrayList<Player> players;
    private static Queue<Player> turnQue;
    private static Queue<String> phases;

    Game(){
        players = new ArrayList<Player>();
        turnQue = new LinkedList<Player>();
        phases = new LinkedList<String>();
        phases.add("reinforcement");
        phases.add("attack");
        phases.add("fortification");
    }


    void addPlayer(String name) throws Exception {

        Color color;
        int playerNum;
        // this is shit code
        switch (players.size()){
            case 0:
                color = new Color(216, 62, 62);
                playerNum = 1;
                Player Player1 = new Player(name, color, playerNum);
                players.add(Player1);
                turnQue.add(Player1);
                break;
            case 1:
                color = new Color(80, 107, 238);
                playerNum = 2;
                Player Player2 = new Player(name, color, playerNum);
                players.add(Player2);
                turnQue.add(Player2);
                break;
            case 2:
                color = new Color(40, 198, 92);
                playerNum = 3;
                Player Player3 = new Player(name, color, playerNum);
                players.add(Player3);
                turnQue.add(Player3);
                break;
            case 3:
                color = new Color(176, 74, 243);
                playerNum = 4;
                Player Player4 = new Player(name, color, playerNum);
                players.add(Player4);
                turnQue.add(Player4);
                break;
            case 4:
                color = new Color(58, 157, 183);
                playerNum = 5;
                Player Player5 = new Player(name, color, playerNum);
                players.add(Player5);
                turnQue.add(Player5);
                break;
            case 5:
                color = new Color(220, 121, 64);
                playerNum = 6;
                Player Player6 = new Player(name, color, playerNum);
                players.add(Player6);
                turnQue.add(Player6);
                break;
            default:
                throw new Exception("Maximum number of players exceeded.");
        }
    }

    public void removePlayer(String name){
        for (Player player : turnQue){
            if (player.getName().equalsIgnoreCase(name))
                turnQue.remove(player);
        }
    }


    Player checkWhosTurn(){
        return turnQue.peek();
    }


    String getPhase(){
        return phases.peek();
    }


    void nextPhase(){
        String toBack = phases.remove();
        phases.add(toBack);
    }


    void nextTurn(){
        Player toBack = turnQue.remove();
        turnQue.add(toBack);
    }


    public ArrayList<Player> getPlayers() {
		return players;
	}


	public void setInitialTroopCount(){
        int numTroops = 0;
        switch (players.size()){
            case 3: numTroops = 35; break;
            case 4: numTroops = 30; break;
            case 5: numTroops = 25; break;
            case 6: numTroops = 20; break;
        }

        for (Player player : players){
            player.addTroopsToPlace(numTroops);
        }
    }

  
    public boolean allReinforcementsPlaced(){
        for (Player player : players){
            if (player.troopsToPlace > 0)
                return false;
        }
        return true;
    }

    public boolean allTerritoriesOccupied (Board board){
        for (Continent continent : board.getContinents()){
            for (Territory territory : continent.getTerritories()){
                if (territory.armySize() < 1)
                    return false;
            }
        }
        return true;
    }

    public Territory getTerritory(Board board, String name){
        for (Continent continent: board.getContinents()){
            for (Territory territory : continent.getTerritories()){
                if (territory.getName().equalsIgnoreCase(name))
                    return territory;
            }
        }
        return null;
    }

    public boolean winCheck(){
        for (Player player : turnQue){
            if (player.getTotalTerritories() == 42)
                return true;
        }

        return false;
    }

	public static void main(String[] args) throws Exception {
        Game risk = new Game();
        Board board = new Board();
        System.out.println("Size of Players: " + players.size());
        risk.addPlayer("logan");
        risk.addPlayer("mitch");
        risk.addPlayer("ryan");
        risk.addPlayer("rudy");
        System.out.println("Size of Players: " + players.size());
        System.out.println(turnQue);
        risk.nextTurn();
        System.out.println(turnQue);
        System.out.println("Who's turn? " + risk.checkWhosTurn());
        System.out.println(phases);
        risk.nextPhase();
        System.out.println(phases);
        System.out.println("What phase? " + risk.getPhase());
    }
}
