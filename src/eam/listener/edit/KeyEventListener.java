package eam.listener.edit;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

public class KeyEventListener implements KeyListener {
	public static int keyNum = 0;
	public static int backspaceNum = 0;

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.keyCode == 8) {
			backspaceNum++;
		}
		keyNum++;
		// System.out.println(e.keyCode);

	}

}
