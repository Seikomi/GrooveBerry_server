package grooveberry_server.server.net.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread charger de connecter les clients souhaitant joindre le server.
 * Créer deux threads pour chaque clients :
 * <ul>
 * <li>Le premier {@link FileUpload} permet d'envoyer des fichiers au client;</li>
 * <li>Le second {@link ClientTreatment} se charge d'executer les commandes envoyer par
 * le client.</li>
 * </ul>
 * 
 * @author nicolas
 * @version 1.0
 */
public class ClientAccept implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClientAccept.class);
	
	private ServerSocket serverCommandSocket;
	private ServerSocket serverTransfertSocket; //TODO
	
	private Socket socketCommand;
	private Socket socketFile; //TODO
	
	/**
	 * Construit un thread pour accepter des clients.
	 * 
	 * @param serverCommandSocket la socket de communication de commande
	 * @param serverTransfertSocket la socket de transfert de fichier
	 */
	public ClientAccept(ServerSocket serverCommandSocket, ServerSocket serverTransfertSocket) {
		this.serverCommandSocket = serverCommandSocket;
		this.serverTransfertSocket = serverTransfertSocket;
	}

	@Override
	public void run() {
		while(true) {
			// The method blocks until a connection is made
			startCommandTreatmentThread(); 
		}
	}
	
	private void startCommandTreatmentThread() {
		try {
			this.socketCommand = this.serverCommandSocket.accept();
		
			LOGGER.info("One client connected");
			LOGGER.debug("Open commande transfert socket");
			
			ObjectOutputStream commandeOut = new ObjectOutputStream(this.socketCommand.getOutputStream());
			ObjectInputStream commandeIn = new ObjectInputStream(this.socketCommand.getInputStream());
	        
			Thread treatmentThread = new Thread(new ClientTreatment(commandeIn, commandeOut)); //TODO Named threads and logs
			treatmentThread.setName("TreatmentThread");
			treatmentThread.start();
			LOGGER.debug("Start commande Thread");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
