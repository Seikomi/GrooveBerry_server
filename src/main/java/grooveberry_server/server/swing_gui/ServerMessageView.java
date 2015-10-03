package grooveberry_server.server.swing_gui;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.swing.JTextArea;

public class ServerMessageView extends JTextArea implements Runnable {
	private PipedInputStream in;
	
	public ServerMessageView(PipedInputStream in) {
		super();
		this.in = in;
	}
	
	@Override
	public void run() {
		int data = 0;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			data = in.read();
			while (data != -1) {
				stringBuilder.append((char) data);
				if(data == Character.valueOf('\n')) {
	            	this.setText(stringBuilder.toString());
	        	}
				data = in.read();
			}
		} catch (IOException e) {
			System.out.println("ERREUR PIPE");
			e.printStackTrace();
		}
	}

}
