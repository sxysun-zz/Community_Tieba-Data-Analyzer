package setupProg;

import mainProg.Config;

public class SetupMain {
	
	public static void main(String args[]){
		Config.getIns().Setup();
		new SetupDisplay().setupOnGUI();
	}
	
}


