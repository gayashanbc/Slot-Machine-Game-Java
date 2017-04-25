import javax.swing.SwingUtilities;

import view.SlotMachine_UI;

public class TestDrive {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new SlotMachine_UI());

	}

}
