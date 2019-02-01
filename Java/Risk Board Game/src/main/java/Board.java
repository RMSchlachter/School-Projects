import java.util.ArrayList;

public class Board
{
    private Continent southAmerica;
    private Continent northAmerica;
    private Continent europe;
    private Continent africa;
    private Continent asia;
    private Continent australia;
    private ArrayList<Continent> continents = new ArrayList<Continent>();

    public Board(){
        initialize();
    }

    private void initialize(){
        createNorthAmerica();
        createSouthAmerica();
        createEurope();
        createAfrica();
        createAsia();
        createAustralia();
    }

    public ArrayList<Continent> getContinents() {
        return continents;
    }

    public Continent getContinent(String name){
        for (Continent continent: continents){
            if (continent.getName().equalsIgnoreCase(name))
                return continent;
        }
        return null;
    }
    public Continent getSouthAmerica() {
        return southAmerica;
    }

    public Continent getNorthAmerica() {
        return northAmerica;
    }

    public Continent getEurope() {
        return europe;
    }

    public Continent getAfrica() {
        return africa;
    }

    public Continent getAsia() {
        return asia;
    }

    public Continent getAustralia() {
        return australia;
    }

    private void createNorthAmerica(){

        Territory alaska = new Territory("alaska", "north america");
        Territory alberta = new Territory("alberta", "north america");
        Territory centralAmerica = new Territory("central america", "north america");
        Territory easternUnitedStates = new Territory("eastern united states", "north america");
        Territory greenland = new Territory("greenland", "north america");
        Territory northwestTerritory = new Territory("northwest territory", "north america");
        Territory ontario = new Territory("ontario", "north america");
        Territory quebec = new Territory("quebec", "north america");
        Territory westernUnitedStates = new Territory("western united states", "north america");

        this.northAmerica = new Continent("north america", 5, alaska, alberta, centralAmerica,
                            easternUnitedStates, greenland, northwestTerritory, ontario, quebec, westernUnitedStates);
        this.continents.add(northAmerica);
    }

    private void createSouthAmerica(){

        Territory venezuela = new Territory("venezuela", "south america");
        Territory brazil = new Territory("brazil", "south america");
        Territory peru = new Territory("peru", "south america");
        Territory argentina = new Territory("argentina", "south america");

        this.southAmerica =  new Continent("south america", 2, venezuela, brazil, peru, argentina);
        this.continents.add(southAmerica);
    }

    private void createEurope(){

        Territory greatBritain = new Territory("great britain", "europe");
        Territory iceland = new Territory("iceland", "europe");
        Territory northernEurope = new Territory("northern europe", "europe");
        Territory scandinavia = new Territory("scandinavia", "europe");
        Territory southernEurope = new Territory("southern europe", "europe");
        Territory ukraine = new Territory("ukraine", "europe");
        Territory westernEurope = new Territory("western europe", "europe");

        this.europe = new Continent("europe", 5, greatBritain, iceland, northernEurope,
                                    scandinavia, southernEurope, ukraine, westernEurope);
        this.continents.add(europe);
    }

    private void createAfrica(){

        Territory congo = new Territory("congo", "africa");
        Territory eastAfrica = new Territory("east africa", "africa");
        Territory egypt = new Territory("egypt", "africa");
        Territory madagascar = new Territory("madagascar", "africa");
        Territory northAfrica = new Territory("north africa", "africa");
        Territory southAfrica = new Territory("south africa", "africa");

        this.africa = new Continent("africa", 3, congo, eastAfrica, egypt, madagascar, northAfrica, southAfrica);
        this.continents.add(africa);
    }

    private void createAsia(){

        Territory afghanistan = new Territory("afghanistan", "asia");
        Territory china = new Territory("china", "asia");
        Territory india = new Territory("india", "asia");
        Territory irkutsk = new Territory("irkutsk", "asia");
        Territory japan = new Territory("japan", "asia");
        Territory kamchatka = new Territory("kamchatka", "asia");
        Territory middleEast = new Territory("middle east", "asia");
        Territory mongolia = new Territory("mongolia", "asia");
        Territory siam = new Territory("siam", "asia");
        Territory siberia = new Territory("siberia", "asia");
        Territory ural = new Territory("ural", "asia");
        Territory yakutsk = new Territory("yakutsk", "asia");

        this.asia = new Continent("asia", 7, afghanistan, china, india, irkutsk, japan, kamchatka,
                                    middleEast, mongolia, siam, siberia, ural, yakutsk);
        this.continents.add(asia);
    }

    private void createAustralia(){

        Territory easternAustralia = new Territory("eastern australia", "australia");
        Territory indonesia = new Territory("indonesia", "australia");
        Territory newGuinea = new Territory("new guinea", "australia");
        Territory westernAustralia = new Territory("western australia", "australia");

        this.australia = new Continent("australia", 2, easternAustralia, indonesia, newGuinea, westernAustralia);
        this.continents.add(australia);
    }
}
