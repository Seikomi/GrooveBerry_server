package grooveberry_server.server.net.thread;

import grooveberry_server.server.net.Server;
import grooveberry_server.server.net.command.CommandFactory;
import grooveberry_server.server.net.command.CommandIntf;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTreatment implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClientTreatment.class);
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PipedOutputStream pipedOutput;
	
	private boolean connectionClosed;
	
	public ClientTreatment(ObjectInputStream in, ObjectOutputStream out,
						   PipedOutputStream pipedOutput) throws IOException {
		this.in = in;
		this.out = out;
		this.pipedOutput = pipedOutput;
	}

	@Override
	public void run() {
		sendMessage("Welcome to GrooveBerry Server");
		
		connectionClosed = false;
		while(!connectionClosed) {
			try {
				String receivingMessage = (String) this.in.readObject();
				if (receivingMessage == null) {
					LOGGER.info("One Client is disconnected");
					Server.printMessageInGui("One Client is disconnected");
					
					connectionClosed = true;
				}
				
				commandeExecute(receivingMessage);
			} catch (IOException | ClassNotFoundException e) {
				LOGGER.info("One client disapear !!! - End command Thread", e);
				connectionClosed = true;
			}
		}
		closeFileUploadThreads(); //TODO Implement close download thread
	}

	private void closeFileUploadThreads() {
		try {
			this.pipedOutput.write(-1);
			this.pipedOutput.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		CommandFactory commandeFactory = new CommandFactory(receivingMessage, pipedOutput);
		CommandIntf commande = commandeFactory.getCommande();
		if (commande != null) {
			String commandReturnState = commande.execute();
			if ("#EXIT OK".equals(commandReturnState)) {
				connectionClosed = true;
			}
			sendMessage(commandReturnState);
		} else {
			sendMessage(receivingMessage);
		}
	}

}
