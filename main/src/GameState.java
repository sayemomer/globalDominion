import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameState {
    private int currentTurn;
    private Map gameMap;
    private List<Player> playerList;

    public GameState() {

        this.playerList = new ArrayList<>();

    }

    public void saveState(String filename) {

    }

    public void loadState(String filename) {

    }

    public void advanceTurn() {


    }

    public void addPlayer(Player player) {

        playerList.add(player);
    }

    public void assignCountries() {
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void showMap() {

    }


}
