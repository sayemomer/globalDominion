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

    /**
     * The syntax for the savegame command
     */
    public static final String TOURNAMENT_SYNTAX = "tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns";

    /**
     * The tournament command
     */
    public static final String TOURNAMENT = "tournament";

    /**
     * The option for the tournament command to specify the maps to be used.
     */
    public static final String TOURNAMENT_MAPS_OPTION = "-M";

    /**
     * The option for the tournament command to specify the player strategies to be used.
     */
    public static final String TOURNAMENT_PLAYERS_OPTION = "-P";

    /**
     * The option for the tournament command to specify the number of games to be played.
     */
    public static final String TOURNAMENT_GAMES_OPTION = "-G";

    /**
     * The option for the tournament command to specify the maximum number of turns per game.
     */
    public static final String TOURNAMENT_TURNS_OPTION = "-D";


    /**
     * Constant representing the command to save a game.
     * This command is used to save the current game state for later retrieval.
     */
    public static final String SAVE_GAME = "savegame";


    /**
     * Constant representing the command to load a game.
     * This command is used to load a previously saved game state.
     */
    public static final String LOAD_GAME = "loadgame";



    /**
     * Constant representing the syntax for the save game command.
     * This syntax indicates the format for issuing the save game command, including the filename parameter.
     */
    public static final String SAVE_GAME_SYNTAX = "savegame filename";

    /**
     * Constant representing the syntax for the load game command.
     * This syntax indicates the format for issuing the load game command, including the filename parameter.
     */
    public static final String LOAD_GAME_SYNTAX = "loadgame filename";
    
}

