package clueGame;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private static Board theInstance = new Board();
    private int numRows;
    private int numColumns;
    private BoardCell[][] grid;
    private String LayoutConfigFile;
    private Map<Character, Room> roomMap;
    
    /*
    * COPIED AND PASTED FROM ASSIGNMENT DESCRIPTION
    */
    
    // constructor is private to ensure only one can be created
    private Board() {
           super() ;
    }
    
    // this method returns the instance
    public static Board getInstance() {
           return theInstance;
    }
    
    // initialize the instance
    public void initialize(){
    	theInstance.numRows = 26;
    	theInstance.numColumns = 21;
    	theInstance.grid = new BoardCell[numRows][numColumns];
    	theInstance.LayoutConfigFile = "";
    	theInstance.roomMap = new HashMap<Character, Room>();
    }
    
    
   public void loadConfigFiles() {
	   
   }
   
   public void loadSetupConfig() {
	   
   }
    
   public void loadLayoutConfig() {
	   
   }
   
   public void setConfigFiles(String csvFile, String xlFile) {
	   
   }

}
