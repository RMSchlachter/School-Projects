import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {

    private Stack<Card> deck;

    public Deck() {
        initalize();
    }

    public void initalize() {
        deck = new Stack();
        Card afghanistan = new Card("afghanistan", "cavalry", false, "/Afghanistan.png");
        deck.push(afghanistan);
        Card alaska = new Card("alaska", "infantry", false, "/Alaska.png");
        deck.push(alaska);
        Card alberta = new Card("alberta", "cavalry", false, "/Alberta.png");
        deck.push(alberta);
        Card argentina = new Card("argentina", "infantry", false, "/Argentina.png");
        deck.push(argentina);
        Card brazil = new Card("brazil", "artillery", false, "/Brazil.png");
        deck.push(brazil);
        Card centralAfrica = new Card("central africa", "infantry", false, "/CentralAfrica.png");
        deck.push(centralAfrica);
        Card centralAmerica = new Card("central america", "artillery", false, "/CentralAmerica.png");
        deck.push(centralAmerica);
        Card china = new Card("china", "infantry", false, "/China.png");
        deck.push(china);
        Card eastAfrica = new Card("east africa", "infantry", false, "/EastAfrica.png");
        deck.push(eastAfrica);
        Card easternAustralia = new Card("eastern australia", "artillery", false, "/EasternAustrailia.png");
        deck.push(easternAustralia);
        Card easternCanada = new Card("easter canada", "cavalry", false, "/EasternCanada.png");
        deck.push(easternCanada);
        Card easternUnitedStates = new Card("eastern united states", "artillery", false, "/EasternUnitedStates.png");
        deck.push(easternUnitedStates);
        Card egypt = new Card("egypt", "infantry", false, "/Egypt.png");
        deck.push(egypt);
        Card greatBritian = new Card("great britian", "artillery", false, "/GreatBritian.png");
        deck.push(greatBritian);
        Card greenland = new Card("greenland", "cavalry", false, "/Greenland.png");
        deck.push(greenland);
        Card iceland = new Card("iceland", "infantry", false, "/Iceland.png");
        deck.push(iceland);
        Card india = new Card("india", "cavalry", false, "/India.png");
        deck.push(india);
        Card indonesia = new Card("indonesia", "artillery", false, "/Indonesia.png");
        deck.push(indonesia);
        Card irkutsk = new Card("eastern united states", "artillery", false, "/EasternUnitedStates.png");
        deck.push(irkutsk);
        Card japan = new Card("japan", "artillery", false, "/Japan.png");
        deck.push(japan);
        Card kamchatka = new Card("kamchatka", "infantry", false, "/Kamchatka.png");
        deck.push(kamchatka);
        Card madagascar = new Card("madagascar", "cavalry", false, "/Madagascar.png");
        deck.push(madagascar);
        Card middleEast = new Card("middle east", "infantry", false, "/MiddleEast.png");
        deck.push(middleEast);
        Card mongolia = new Card("mongolia", "infantry", false, "/Mongolia.png");
        deck.push(mongolia);
        Card newGuinea = new Card("new guinea", "infantry", false, "/NewGuinea.png");
        deck.push(newGuinea);
        Card northAfrica = new Card("north africa", "cavalry", false, "/NorthAfrica.png");
        deck.push(northAfrica);
        Card northernEurope = new Card("northern europe", "artillery", false, "/NorthernEurope.png");
        deck.push(northernEurope);
        Card northwestTerritory = new Card("northwest territory", "artillery", false, "/NorthwestTerritory.png");
        deck.push(northwestTerritory);
        Card ontario = new Card("ontario", "cavalry", false, "/Ontario.png");
        deck.push(ontario);
        Card peru = new Card("peru", "infantry", false, "/Peru.png");
        deck.push(peru);
        Card russia = new Card("russia", "cavalry", false, "/Russia.png");
        deck.push(russia);
        Card scandinavia = new Card("scandinavia", "cavalry", false, "/Scandinavia.png");
        deck.push(scandinavia);
        Card siberia = new Card("siberia", "cavalry", false, "/Siberia.png");
        deck.push(siberia);
        Card southAfrica = new Card("south africa", "artillery", false, "/SouthAfrica.png");
        deck.push(southAfrica);
        Card southeastAsia = new Card("southeast asia", "infantry", false, "/SoutheastAsia.png");
        deck.push(southeastAsia);
        Card southernEurope = new Card("southern europe", "artillery", false, "/SouthernEurope.png");
        deck.push(southernEurope);
        Card ural = new Card("ural", "cavalry", false, "/Ural.png");
        deck.push(ural);
        Card venezuela = new Card("venezuela", "infantry", false, "/Venezuela.png");
        deck.push(venezuela);
        Card westernAustralia = new Card("western australia", "artillery", false, "/WesternAustralia.png");
        deck.push(westernAustralia);
        Card westernEurope = new Card("western europe", "artillery", false, "/WesternAustralia.png");
        deck.push(westernEurope);
        Card westernUnitedStates = new Card("western united states", "artillery", false, "/WesternUnitedStates.png");
        deck.push(westernUnitedStates);
        Card yakutsk = new Card("yakutsk", "cavalry", false, "/Yakutsk.png");
        deck.push(yakutsk);
        Card wildCard1 = new Card("", "", true, "/Wildcard.png");
        deck.push(wildCard1);
        Card wildCard2 = new Card("", "", true, "/Wildcard.png");
        deck.push(wildCard2);

        shuffle();
    }


    public void shuffle(){
        Collections.shuffle(this.deck);
    }


    public Card drawCard(){
        return deck.pop();
    }

    public void addToBottom(ArrayList<Card> cards){
        this.deck.addAll(cards);
    }

    public Stack<Card> getDeck() {
        return deck;
    }
}
