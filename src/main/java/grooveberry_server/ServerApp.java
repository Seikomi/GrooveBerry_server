package grooveberry_server;

import grooveberry_server.server.net.Server;
import grooveberry_server.server.swing_gui.MainWindow;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerApp {
	private final static Logger LOGGER = LoggerFactory.getLogger(ServerApp.class);
	
	public final static int SERVER_COMMANDE_PORT = 2009;
	public final static int SERVER_TRANSFERT_PORT = 3009;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		PipedOutputStream output = new PipedOutputStream(); 	 // Server -->
		PipedInputStream  input  = new PipedInputStream(output); // --> GUI
		LOGGER.debug("Create communication pipes : server --> GUI");
		
		Server server = new Server(SERVER_COMMANDE_PORT, SERVER_TRANSFERT_PORT/*, output*/);
		LOGGER.debug("Server created on ports :");
		LOGGER.debug("Commandes port: " + SERVER_COMMANDE_PORT);
		LOGGER.debug("Transfert port: " + SERVER_TRANSFERT_PORT);
        server.start();
		LOGGER.info("Server started");
        
		MainWindow windows = new MainWindow(input);
		windows.setVisible(true);
		LOGGER.info("Server GUI started");
    }
        
}
