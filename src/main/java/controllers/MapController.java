package controllers;
import models.GameMap;

import java.io.File;

public class MapController {
    GameMap gameMap;

    public void handleShowMap(String[] p_args){
        try{
            if(p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SHOW_MAP_SYNTAX);
            gameMap.showMap();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleValidateMap(String[] p_args){
        try{
            if(p_args.length != 0)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.VALIDATE_MAP_SYNTAX);
            gameMap.validateMap();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleEditMap(String[] p_args){
        try{
            if(p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.EDIT_MAP_SYNTAX);
            File f = new File(p_args[0]);
            if(f.exists()) {
                gameMap = new GameMap(p_args[0]);
            } else {
                gameMap = new GameMap();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleLoadMap(String[] p_args){
        try{
            if(p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.LOAD_MAP_SYNTAX);
            File f = new File(p_args[0]);
            if(f.exists()) {
                gameMap = new GameMap(p_args[0]);
            } else {
                throw new Exception("Map file does not exist.");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleSaveMap(String[] p_args){
        try{
            if(p_args.length != 1)
                throw new Exception("Invalid number of arguments." + "Correct Syntax: \n\t" + Command.SAVE_MAP_SYNTAX);
            gameMap.saveMap();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}