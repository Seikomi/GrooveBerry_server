package grooveberry_server.server.swing_gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PipedInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainWindow extends JFrame implements WindowListener {
	private final static Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
	
	private PipedInputStream serverInput;
	
	private LoggerView loggerView;
	
	public MainWindow() {
		this.addWindowListener(this);
		
		initializeWindowsPreferences();
		
		this.add(new JLabel("Welcome to GrooveBerry Server"), BorderLayout.NORTH);
		
		LOGGER.info("Loading Server GUI");
	}
	
	public MainWindow(PipedInputStream serverInput) throws IOException {
		this();
		this.serverInput = serverInput;
		
		this.loggerView = new LoggerView(serverInput);
		this.add(this.loggerView , BorderLayout.CENTER);
	}

	private void initializeWindowsPreferences() {
		this.setTitle("GrooveBerry Server - 0.0.1 Dev");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		LOGGER.info("Closing Server GUI");
		
	}

	@Override public void windowOpened(WindowEvent e) {}
	@Override public void windowClosed(WindowEvent e) {}
	@Override public void windowIconified(WindowEvent e) {}
	@Override public void windowDeiconified(WindowEvent e) {}
	@Override public void windowActivated(WindowEvent e) {}
	@Override public void windowDeactivated(WindowEvent e) {}
}
