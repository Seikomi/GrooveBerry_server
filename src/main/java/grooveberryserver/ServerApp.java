package grooveberryserver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import grooveberryserver.server.net.Server;

public class ServerApp {
	private final static Logger LOGGER = LoggerFactory.getLogger(ServerApp.class);
	
	public final static int SERVER_COMMANDE_PORT = 2009;
	public final static int SERVER_TRANSFERT_PORT = 3009;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Server server = new Server(SERVER_COMMANDE_PORT, SERVER_TRANSFERT_PORT);
		LOGGER.debug("Server created on ports :");
		LOGGER.debug("Commandes port: " + SERVER_COMMANDE_PORT);
		LOGGER.debug("Transfert port: " + SERVER_TRANSFERT_PORT);
		
        server.start();
		LOGGER.info("Server started");

    }
        
}
