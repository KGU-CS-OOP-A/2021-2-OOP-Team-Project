package org.corodiak.main;

import org.corodiak.util.Utils;
import org.corodiak.gui.GUIMain;

public class Main {
	public void run() {
		Utils.dataSetting();
		GUIMain.startGUI();
	}
	
	public static void main(String[] args) {
		Main m=new Main();
		m.run();
	}
}
