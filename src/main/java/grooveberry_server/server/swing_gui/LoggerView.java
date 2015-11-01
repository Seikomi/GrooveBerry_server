package grooveberry_server.server.swing_gui;

import java.io.PipedInputStream;

import javax.swing.JScrollPane;

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
