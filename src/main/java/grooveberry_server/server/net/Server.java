package grooveberry_server.server.net;

import grooveberry_server.audiofile.AudioFile;
import grooveberry_server.audiofile.AudioFileDirectoryScanner;
import grooveberry_server.readingqueue.ReadingQueue;
import grooveberry_server.readingqueue.ReadingQueueManager;
import grooveberry_server.server.net.command.CommandFactory;
import grooveberry_server.server.net.command.CommandInterface;
import grooveberry_server.server.net.thread.ClientAccept;

import java.io.File;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server est un TODO singleton du server de musique GrooveBerry.
 * Il peut etre lancer sous deux modes selon le constructeur choisi :
 * avec ou sans stream de sortie (voir {@link PipedOutputStream}).
 * Il utilise deux ports définies par les parametres :
 * <ul>
 * <li>{@code int serverCommandePort} : port gérant les commandes
 * (TODO définie selon un protocole) envoyées au serveur</li>
 * <li>{@code int serverTransfertPort} : port gérant les données
 * (fichiers audios, xml, ...) envoyées au serveur</li>
 * </ul>
 * <p>
 * Son but est de fournir les services suivants :
 * <ul>
 * <li>gestion de la piste en cours de lecture : #PLAY, #PAUSE</li>
 * <li>gestion de la file de lecture : #PREV, #NEXT</li>
 * <li>gestion du transfert de fichiers : #UPLOAD@[nom du fichier]...,
 * #DOWNLOAD@[nom du fichier]...</li>
 * </ul>
 * 
 * @author  Nicolas Symphorien
 * @version %I%, %G%
 * @since   1.0
 */
public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private PipedOutputStream serverOutput;

    private ServerSocket serverSocketCommande;
    private ServerSocket serverSocketTransfert;

    private Thread connectionClientsThread;
    private Thread communicationThread;

	public static final String userHomePath = System.getProperty("user.home");
    
    public Server(int serverCommandePort, int serverTransfertPort) throws InterruptedException {
        try {
            this.serverSocketCommande = new ServerSocket(serverCommandePort);
            this.serverSocketTransfert = new ServerSocket(serverTransfertPort);
        } catch (IOException e) {
            LOGGER.error("Unexpexted socket deconnection", e);
        }
    }

    public Server(int serverCommandePort, int serverTransfertPort, PipedOutputStream serverOutput) throws InterruptedException {
        this.serverOutput = serverOutput;
        try {
            this.serverSocketCommande = new ServerSocket(serverCommandePort);
            this.serverSocketTransfert = new ServerSocket(serverTransfertPort);
        } catch (IOException e) {
            LOGGER.error("Unexpexted socket deconnection", e);
        }
    }
    
    public void start() {
    	initServerFiles();
        initReadingQueue();
        startThreadsServer();
    }
    
    public static void sendCommand(String command) {
        CommandFactory commandeFactory = CommandFactory.init();
        commandeFactory.executeCommand(command);		
    }
    
    private void initServerFiles() {
    	Path mainDirectoryPath = Paths.get(userHomePath	+ "/.grooveberry/");
    	Path serverPropertiesPath = Paths.get(userHomePath + "/.grooveberry/grooveberry.properties");
    	Path serverLibraryDirectoryPath = Paths.get(userHomePath + "/.grooveberry/library/");
    	
    	File mainDirectory = mainDirectoryPath.toFile();
    	File serverProperties = serverPropertiesPath.toFile();
    	File serverLibraryDirectory = serverLibraryDirectoryPath.toFile();
    	
    	try {
    		if (!mainDirectory.exists()) {
    			boolean isCreate = mainDirectory.mkdir();
    			if (!isCreate) {
    				throw new IOException();
    			} else {
    				LOGGER.info("Create the directory : " + mainDirectoryPath);
    			}
    		}
    		if (!serverProperties.exists()) {
				boolean isCreate = serverProperties.createNewFile();
				if (!isCreate) {
    				throw new IOException();
    			} else {
    				LOGGER.info("Create the file :" + serverPropertiesPath);
    			}
    		}
    		if (!serverLibraryDirectory.exists()) {
				boolean isCreate = serverLibraryDirectory.mkdir();
				if (!isCreate) {
    				throw new IOException();
    			} else {
    				LOGGER.info("Create the directory :" + serverLibraryDirectoryPath);
    			}
    		}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void initReadingQueue() {
        try {
            Path directoryPath = Paths.get(userHomePath + "/.grooveberry/library/");
            
            if (directoryPath.toFile().exists())
            	
            	// LibraryManager.getInstance().scanDirectory();
            	

	            LOGGER.debug("Scanning audio files in directory : " + directoryPath.toAbsolutePath());
	            AudioFileDirectoryScanner directoryScanner = new AudioFileDirectoryScanner(directoryPath);
	
	            LOGGER.debug("Loading audio files in reading queue");
	            ArrayList<AudioFile> audioFileList = directoryScanner.getAudioFileList();
	            if (audioFileList.size() != 0) {
	            	ReadingQueueManager.getInstance().addToReadingQueue(audioFileList);
	            } else {
	            	LOGGER.debug("No audio files in " + userHomePath + "/library/ (normal if this is the first launch)");
	            }
        } catch (IOException e) {
            LOGGER.error("Directory scanning error", e);
        }

    }
    
    private void startThreadsServer() {
        connectionClientsThread = new Thread(new ClientAccept(serverSocketCommande, serverSocketTransfert));
        connectionClientsThread.setName("ClientAccept");
        connectionClientsThread.start();
        LOGGER.debug("Start ClientAccept Thread");
        
        if (serverOutput != null) {
            communicationThread = new Thread(new CommunicationThread(serverOutput));
            communicationThread.setName("CommunicationToGui");
            communicationThread.start();
            LOGGER.debug("Start CommunicationToGui Thread");
        }
    }
    
    public static void printMessageInGui(String message) {
        CommunicationThread.communicationQueue.offer(message);
        synchronized (CommunicationThread.communicationQueue) {
            CommunicationThread.communicationQueue.notifyAll();
        }
    }
    
    private static class CommunicationThread implements Runnable {

        public static final Queue<String> communicationQueue = new ConcurrentLinkedQueue<>();

        private final PipedOutputStream serverOutput;

        public CommunicationThread(PipedOutputStream serverOutput) {
            this.serverOutput = serverOutput;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    while (!communicationQueue.isEmpty()) {
                        String message = communicationQueue.poll();
                        if (message != null) {
                            this.serverOutput.write((message + "\n").getBytes());
                            this.serverOutput.flush();
                        }
                    }
                    synchronized (communicationQueue) {
                        communicationQueue.wait();
                    }
                } catch (IOException | InterruptedException e) {
                    LOGGER.error(null, e);
                }
            }
        }
    }
}
