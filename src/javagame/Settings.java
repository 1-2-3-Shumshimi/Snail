package javagame;

public class Settings {
	
	private static Settings settings;
	private static int opponentSelected;
	private static int winConditionSelected;
	
	public static Settings getSettings(){
		if (settings == null){
			settings = new Settings();
		}
		return settings;
	}
	
	private Settings() {
		opponentSelected = 0;
		winConditionSelected = 0;
	}
	
	public static void setOpponentSelected(int selection){
		opponentSelected = selection;
	}
	
	public static int getOpponentSelection(){
		return opponentSelected;
	}
	
	public static void setWinConditionSelected(int selection){
		winConditionSelected = selection;
	}
	
	public static int getWinConditionSelection(){
		return winConditionSelected;
	}

}
