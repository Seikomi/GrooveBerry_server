package grooveberry_server.server.swing_gui;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LoggerView extends JScrollPane {
	private ServerMessageView serverMessageTextArea;

	public LoggerView(PipedInputStream in) {
		super();
		this.serverMessageTextArea = new ServerMessageView(in);
		this.serverMessageTextArea.setEditable(false);
		this.setViewportView(serverMessageTextArea);
		
		Thread serverMessageDisplayThread = new Thread(this.serverMessageTextArea);
		serverMessageDisplayThread.start();
		
		
	}
}
