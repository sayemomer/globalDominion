package controllers;

/**
 * This class contains all the commands that can be used in the game.
 * It also contains the syntax for each command.
 */

public class Command {

    /**
     * Constructor for the Command class
     */

    Command() {
    }

    /**
     * The showmap command "showmap"
     */
    public static final String SHOW_MAP = "showmap";

    /**
     * The validatemap command "validatemap"
     */
    public static final String VALIDATE_MAP = "validatemap";

    /**
     * The Assign countries command "assigncountries"
     */
    public static final String ASSIGN_COUNTRIES = "assigncountries";

    // arg commands

    /**
     * The Edit continent command "editcontinent"
     */
    public static final String EDIT_CONTINENT = "editcontinent";

    /**
     * The Edit country command "editcountry"
     */
    public static final String EDIT_COUNTRY = "editcountry";

    /**
     * The Edit neighbor command "editneighbor"
     */
    public static final String EDIT_NEIGHBOR = "editneighbor";

    /**
     * The Save map command "savemap"
     */

    public static final String SAVE_MAP = "savemap";

    /**
     * The Edit map command "editmap"
     */
    public static final String EDIT_MAP = "editmap";

    /**
     * The Load map command "loadmap"
     */
    public static final String LOAD_MAP = "loadmap";

    /**
     * The gameplayer command
     */

    public static final String GAME_PLAYER = "gameplayer";

    /**
     * The deploy command
     */
    public static final String DEPLOY = "deploy";

    /**
     * advance command
     */
    public static final String ADVANCE = "advance";


    /**
     * The add option
     */
    public static final String ADD = "-add";

    /**
     * The remove option
     */
    public static final String REMOVE = "-remove";
    
    // command syntax

    /**
     * The syntax for the gameplayer command
     */
    public static final String GAME_PLAYER_SYNTAX = "gameplayer -add playername -remove playername";

    /**
     * The syntax for the assigncountries command
     */
    public static final String ASSIGN_COUNTRIES_SYNTAX = "assigncountries";

    /**
     * The syntax for the deploy command
     */
    public static final String DEPLOY_SYNTAX = "deploy countryID num";

    /**
     * The syntax for the advance command
     */

    public static final String ADVANCE_SYNTAX = "advance countrynamefrom countynameto numarmies";

    /**
     * The syntax for the editcontinent command
     */
    public static final String EDIT_CONTINENT_SYNTAX = "editcontinent -add continentID controlvalue -remove continentID";

    /**
     * The syntax for the editcountry command
     */
    public static final String EDIT_COUNTRY_SYNTAX = "editcountry -add countryID continentID -remove countryID";

    /**
     * The syntax for the editneighbor command
     */
    public static final String EDIT_NEIGHBOR_SYNTAX = "editneighbor -add countryID neighborcountyID -remove countryID neighborcountryID";

    /**
     * The syntax for the savemap command
     */
    public static final String SAVE_MAP_SYNTAX = "savemap filename";

    /**
     * The syntax for the editmap command
     */
    public static final String EDIT_MAP_SYNTAX = "editmap filename";

    /**
     * The syntax for the showmap command
     */
    public static final String SHOW_MAP_SYNTAX = "showmap";

    /**
     * The syntax for the validatemap command
     */
    public static final String VALIDATE_MAP_SYNTAX = "validatemap";

    /**
     * The syntax for the loadmap command
     */
    public static final String LOAD_MAP_SYNTAX = "loadmap filename";

    /**
     * The syntax for the BOMB command
     */
    public static final String BOMB_SYNTAX = "bomb countryID";

    /**
     * The syntax for the Bomb command
     */
    public static final String BOMB = "bomb";

    /**
     * The syntax for the blockade command
     */
    public static final String BLOCKADE_SYNTAX = "blockade countryID";

    /**
     * The syntax for the blockade command
     */
    public static final String BLOCKADE = "blockade";

    /**
     * The syntax for the airlift command
     */
    public static final String AIRLIFT_SYNTAX = "airlift countrynamefrom countynameto numarmies";

    /**
     * The syntax for the airlift command
     */
    public static final String AIRLIFT = "airlift";

    /**
     * The syntax for the negotiate command
     */
    public static final String NEGOTIATE_SYNTAX = "negotiate playername";

    /**
     * The syntax for the negotiate command
     */
    public static final String NEGOTIATE = "negotiate";


    public static final String TOURNAMENT_SYNTAX = "tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns";

    public static final String TOURNAMENT = "tournament";

    public static final String TOURNAMENT_MAPS_OPTION = "-M";

    public static final String TOURNAMENT_PLAYERS_OPTION = "-P";

    public static final String TOURNAMENT_GAMES_OPTION = "-G";

    public static final String TOURNAMENT_TURNS_OPTION = "-D";
}

