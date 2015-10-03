package grooveberry_server.server.net.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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
	
	private ServerSocket serverSocket;
	private ServerSocket serverSocketFile;
	
	private Socket socketCommande;
	private Socket socketFile;
	
	/**
	 * Construit un thread pour accepter des clients.
	 * 
	 * @param serverCommandeSocket la socket de communication de commande
	 * @param serverTransfertSocket la socket de transfert de fichier
	 */
	public ClientAccept(ServerSocket serverCommandeSocket, ServerSocket serverTransfertSocket) {
		this.serverSocket = serverCommandeSocket;
		this.serverSocketFile = serverTransfertSocket;
	}

	@Override
	public void run() {
		while(true) {
			try {
				// Liaison commande --> file upload (download from a client)
				PipedOutputStream commandeOutput = new PipedOutputStream();
				PipedInputStream  fileInput  = new PipedInputStream(commandeOutput);
				
				this.socketCommande = this.serverSocket.accept();
				LOGGER.info("One client connected");
				LOGGER.debug("Open commande transfert socket");
				
				ObjectOutputStream commandeOut = new ObjectOutputStream(this.socketCommande.getOutputStream());
				commandeOut.flush();
				commandeOut.reset();
				
				ObjectInputStream commandeIn = new ObjectInputStream(this.socketCommande.getInputStream());
                
				Thread treatmentThread = new Thread(new ClientTreatment(commandeIn, commandeOut, commandeOutput)); //TODO Named threads and logs
				treatmentThread.start();
				LOGGER.debug("Start commande Thread");
				
				
				this.socketFile = this.serverSocketFile.accept();
				LOGGER.debug("Open file transfert socket");
				
				ObjectOutputStream fileOut = new ObjectOutputStream(socketFile.getOutputStream());
				fileOut.flush();
				fileOut.reset();
				
				Thread fileUploadThread = new Thread(new FileUpload(fileOut, fileInput));
				fileUploadThread.start();
				LOGGER.debug("Start FileUpload Thread for one client");
				
				ObjectInputStream fileIn = new ObjectInputStream(socketFile.getInputStream());
				
				Thread fileDownloadThread = new Thread(new FileDownload(fileIn));
				fileDownloadThread.start();
				LOGGER.debug("Start FileDownload Thread for one client ");
					
			} catch (IOException e) {
				LOGGER.error(null, e);
			}
		}
	}

}
