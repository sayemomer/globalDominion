package controllers;

/**
 * This class contains all the commands that can be used in the game.
 * It also contains the syntax for each command.
 */

public class Command {
    // no arg commands
    public static final String SHOW_MAP = "showmap";
    public static final String VALIDATE_MAP = "validatemap";
    public static final String ASSIGN_COUNTRIES = "assigncountries";

    // arg commands
    public static final String EDIT_CONTINENT = "editcontinent";
    public static final String EDIT_COUNTRY = "editcountry";
    public static final String EDIT_NEIGHBOR = "editneighbor";

    public static final String SAVE_MAP = "savemap";
    public static final String EDIT_MAP = "editmap";
    public static final String LOAD_MAP = "loadmap";

    public static final String GAME_PLAYER = "gameplayer";
    public static final String DEPLOY = "deploy";

    public static final String ADVANCE = "advance";

    // options
    public static final String ADD = "-add";
    public static final String REMOVE = "-remove";
    
    // command syntax
    public static final String GAME_PLAYER_SYNTAX = "gameplayer -add playername -remove playername";
    public static final String ASSIGN_COUNTRIES_SYNTAX = "assigncountries";
    public static final String DEPLOY_SYNTAX = "deploy countryID num";

    public static final String ADVANCE_SYNTAX = "advance countrynamefrom countynameto numarmies";
    public static final String EDIT_CONTINENT_SYNTAX = "editcontinent -add continentID controlvalue -remove continentID";
    public static final String EDIT_COUNTRY_SYNTAX = "editcountry -add countryID continentID -remove countryID";
    public static final String EDIT_NEIGHBOR_SYNTAX = "editneighbor -add countryID neighborcountyID -remove countryID neighborcountryID";
    public static final String SAVE_MAP_SYNTAX = "savemap filename";
    public static final String EDIT_MAP_SYNTAX = "editmap filename";
    public static final String SHOW_MAP_SYNTAX = "showmap";
    public static final String VALIDATE_MAP_SYNTAX = "validatemap";
    public static final String LOAD_MAP_SYNTAX = "loadmap filename";
    public static final String BOMB_SYNTAX = "bomb countryID";
    public static final String BOMB = "bomb";
}
