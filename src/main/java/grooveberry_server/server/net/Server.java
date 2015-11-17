package grooveberry_server.server.net;

import grooveberry_server.audiofile.AudioFile;
import grooveberry_server.audiofile.AudioFileDirectoryScanner;
import grooveberry_server.readingqueue.ReadingQueueManager;
import grooveberry_server.server.net.thread.ClientAccept;

import java.io.File;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La classe Server construit un server de musique pilotable à distance par sockets
 * Java. <br/>
 * Il utilise les deux ports définies par les parametres de son constructeur:
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
 * <li>gestion du volume : #VOLUP, #VOLDOWN</li>
 * <li>recuperation d'information sur le serveur : #SONG, #LIST</li>
 * <li>gestion du transfert de fichiers : #UPLOAD@[nom du fichier]...,
 * #DOWNLOAD@[nom du fichier]...</li>
 * </ul>
 * 
 * @author  Nicolas Symphorien
 * @since   1.0
 */
public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static final String userHomePath = System.getProperty("user.home");
	
    private ServerSocket serverSocketCommande;
    private ServerSocket serverSocketTransfert;

    private Thread connectionClientsThread;
    
	/**
	 * Créer une instance de serveur GrooveBerry utilisant deux sockets.
	 * 
	 * @param serverCommandePort
	 * @param serverTransfertPort
	 * @throws InterruptedException
	 */
    public Server(int serverCommandePort, int serverTransfertPort) throws InterruptedException {
    	try {
            this.serverSocketCommande = new ServerSocket(serverCommandePort);
            this.serverSocketTransfert = new ServerSocket(serverTransfertPort);
        } catch (IOException e) {
            LOGGER.error("Unexpexted socket deconnection", e);
        }
    }
    
    /**
     * Démarre le serveur GrooveBerry, créer les dossiers et fichiers
     * nécessaires et initialise la file de lecture avec les fichiers
     * dans ~/.grooveberry/library/ (UNIX seulement) 
     */
    public void start() {
    	initServerFiles();
        initReadingQueue();
        startThreadsServer();
    }
    
    public void restart() {
    	// TODO
    }
    
    public void stop() {
    	// TODO
    }
        
    /**
     * Initialise les fichiers et dossiers nécessaires au serveur.
     */
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
    
    /**
     * Rempli la file de lecture avec le contenue du dossier library/
     */
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
    
    /**
     * Lance le thread de gestions des clients
     */
    private void startThreadsServer() {
        connectionClientsThread = new Thread(new ClientAccept(serverSocketCommande, serverSocketTransfert));
        connectionClientsThread.setName("ClientAccept");
        connectionClientsThread.start();
        LOGGER.debug("Start ClientAccept Thread");
    }

}
