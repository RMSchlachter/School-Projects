import java.awt.*;
import java.util.ArrayList;

public class Player {
    String name;
    Color color;
    int id;
    int troopsToPlace;
    int totalTerritories;
    int totalTroops = 0;
    int totalContinents;
    int numberOfCardSets;
    int reinforcements;
    int totalSets = 0;
    ArrayList<Card> cards;


    public Player(String name, Color color, int id) {
        this.name = name;
        this.color = color;
        this.id = id;
        this.troopsToPlace = 0;
        this.totalTerritories = 0;
        this.totalContinents = 0;
        this.totalTroops = 0;
        this.reinforcements = 0;
        this.numberOfCardSets = 0;
        this.cards = new ArrayList<Card>();
        this.totalSets = 0;
    }


    @Override
    public String toString() {
        return "Player" + id + ": " + name;
    }


    public String getName() {
        return name;
    }


    public Color getColor() {
        return color;
    }


    public int getId() {
        return id;
    }


    public int getTroopsToPlace() {
        return troopsToPlace;
    }


    public int getNumberOfCardSets() {
        return numberOfCardSets;
    }


    public int getTotalContinents() {
        return totalContinents;
    }


    public int getTotalTerritories() {
        return totalTerritories;
    }
    
    public void removeTotalTroops(int troops) {
    		this.totalTroops = this.totalTroops - troops;
    }


    public int getTotalTroops() {
        return totalTroops;
    }
    
    public void setTotalTroops(int troops) {
    		this.totalTroops = this.totalTroops + troops;
    }


    public void addTroopsToPlace(int num){
        this.troopsToPlace += num;
    }


    public void removeTroopsToPlace(int num){
        this.troopsToPlace -= num;
    }


    public void addCard(Card card){
        this.cards.add(card);
    }


    public void addCardSet() {
        this.numberOfCardSets += 1;
    }


    public void removeCardSet(){
        this.numberOfCardSets -= 1;
    }


    public void checkCardSets (){
        int wild = 0;
        int infantry = 0;
        int cavalry = 0;
        int artillery = 0;
        for (Card card : cards){
            if (card.isWild())
                ++wild;
            else if (card.getTroopType().equalsIgnoreCase("infantry"))
                ++infantry;
            else  if (card.getTroopType().equalsIgnoreCase("cavalry"))
                ++cavalry;
            else ++artillery;
        }

        // check for 3 of infantry
        if (infantry >= 3) {
            addCardSet();
        }

        //check for 3 of cavalry
        if (cavalry >= 3) {
            addCardSet();
        }

        // check for 3 of artillery
        if (artillery >= 3) {
            addCardSet();
        }

        //check for 1 of each kind
        if (infantry >= 1 && cavalry >= 1 && artillery >= 1) {
            addCardSet();
        }

        // check for any two plus a wild
        if ((infantry + cavalry + artillery) >= 2 && wild >= 1){
            addCardSet();
        }
    }

    public boolean checkCardSets (ArrayList<Card> cards){
        int wild = 0;
        int infantry = 0;
        int cavalry = 0;
        int artillery = 0;

        for (Card card : cards){
            if (card.isWild())
                ++wild;
            else if (card.getTroopType().equalsIgnoreCase("infantry"))
                ++infantry;
            else  if (card.getTroopType().equalsIgnoreCase("cavalry"))
                ++cavalry;
            else ++artillery;
        }

        // check for 3 of infantry, cavalry or artillery
        if (infantry >= 3 || cavalry >= 3 || artillery >= 3) {
            return true;
        }

        //check for 1 of each kind
        if (infantry >= 1 && cavalry >= 1 && artillery >= 1) {
            return true;
        }

        // check for any two plus a wild
        if ((infantry + cavalry + artillery) >= 2 && wild >= 1){
            return true;
        }

        return false;
    }

    public void playCardSet(ArrayList<Card> myCards){

        // first set traded in
        if(totalSets == 0) {
            reinforcements += 4;
        }

        // second set traded in
        if(totalSets == 1) {
            reinforcements += 6;
        }

        // third set traded in
        if(totalSets == 2) {
            reinforcements += 8;
        }

        // fourth set traded in
        if(totalSets == 3) {
            reinforcements += 10;
        }

        // fifth set traded in
        if(totalSets == 4) {
            reinforcements += 12;
        }

        // sixth set traded in
        if(totalSets >= 5) {
            reinforcements += 16;
        }

        removeCardFromHand(myCards.get(0));
        removeCardFromHand(myCards.get(1));
        removeCardFromHand(myCards.get(2));

        totalSets++;
    }


    private void removeCardFromHand(Card c) {
        cards.remove(c);
    }


    public void addContinentCount(){
        this.totalContinents++;
    }


    public void removeContinentCount(){
        this.totalContinents++;
    }


    public void addTotalTerritories(){
        this.totalTerritories++;
    }


    public void removeTotalTerritories(){
        this.totalTerritories--;
    }


    public boolean needToPlayCard() {
        if(cards.size() >= 6) {
            return true;
        }

        return false;
    }


    public void setReinforcements(Board board) {
        int contValues = 0;
        for(Continent continent : board.getContinents()) {
            if(continent.getOwner() == this) {
                contValues += continent.getReinforcemntVal();
            }
        }

        this.reinforcements = contValues + (totalTerritories / 3);
        totalTroops += reinforcements;
    }


    public int getReinforcements() {
        return this.reinforcements;
    }


    public void placeReinforcements(int amount) {
        reinforcements -= amount;
    }


    public boolean wonGame() {
        if(totalContinents == 6) {
            return true;
        }

        return false;
    }


    public void takeCards(Player a, Player b) {
        // player a won, takes player b's cards
        for(Card c : b.cards) {
            a.addCard(c);
            b.removeCardFromHand(c);
        }
    }

}
