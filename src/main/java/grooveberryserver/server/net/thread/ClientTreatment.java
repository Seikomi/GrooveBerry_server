package grooveberryserver.server.net.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import grooveberryserver.server.net.command.CommandFactory;

/**
 * Thread de gestion des commandes envoyer au serveur par socket.
 * 
 * @author nicolas
 *
 */
public class ClientTreatment implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClientTreatment.class);
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private boolean connectionClosed;
	
	public ClientTreatment(ObjectInputStream in, ObjectOutputStream out) throws IOException {
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		sendMessage("#HELLO Welcome to GrooveBerry Server");
		
		connectionClosed = false;
		while(!connectionClosed) {
			try {
				String receivingMessage = (String) this.in.readObject();
				if (receivingMessage == null) {
					LOGGER.info("One Client is disconnected");
					//Server.printMessageInGui("One Client is disconnected");
					
					connectionClosed = true;
				}
				
				commandeExecute(receivingMessage);
			} catch (IOException | ClassNotFoundException e) {
				LOGGER.info("One client disapear !!! - End command Thread");
				LOGGER.debug("stack trace :", e);
				connectionClosed = true;
			}
		}
	}

	private void sendMessage(String message) {
		try {
			this.out.writeObject(message);
			this.out.flush();
			this.out.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void commandeExecute(String receivingMessage) {
		CommandFactory commandFactory = CommandFactory.init();
		String commandReturnState = commandFactory.executeCommand(receivingMessage);
		if (commandReturnState == null) {
			sendMessage(receivingMessage);
		} else if ("#EXIT OK".equals(commandReturnState)) {
			connectionClosed = true;
		} else {
			sendMessage(commandReturnState);
		}
	}

}