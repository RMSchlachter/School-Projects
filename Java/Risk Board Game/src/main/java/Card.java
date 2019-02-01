import javax.swing.ImageIcon;

public class Card {

    private String territory;
    private String troopType;
    private boolean wild;
    java.net.URL cardURL;
	

    public Card(String territory, String troopType, boolean wild, String imagePath){
        this.territory = territory;
        this.troopType = troopType;
        this.wild = wild;
        this.cardURL = getClass().getResource(imagePath);
    }
   
    public ImageIcon getCardIcon() {
    		return new ImageIcon(cardURL);
    }

    public String getTerritory() {
        return territory;
    }

    public String getTroopType() {
        return troopType;
    }

    public boolean isWild() {
        return wild;
    }
}
