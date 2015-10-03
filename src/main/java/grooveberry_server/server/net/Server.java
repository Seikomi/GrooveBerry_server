package grooveberry_server.server.net;

import grooveberry_server.audiofile.AudioFile;
import grooveberry_server.audiofile.AudioFileDirectoryScanner;
import grooveberry_server.audiofile.ReadingQueue;
import grooveberry_server.server.net.command.CommandFactory;
import grooveberry_server.server.net.command.CommandIntf;
import grooveberry_server.server.net.thread.ClientAccept;

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
 * Il utilise deux ports d�finies par les parametres :
 * <ul>
 * <li>{@code int serverCommandePort} : port g�rant les commandes
 * (TODO d�finie selon un protocole) envoy�es au serveur</li>
 * <li>{@code int serverTransfertPort} : port g�rant les donn�es
 * (fichiers audios, xml, ...) envoy�es au serveur</li>
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
        initReadingQueue();
        startThreadsServer();
    }
    
    public static void sendCommand(String command) {
        CommandFactory commandeFactory = new CommandFactory(command);
		CommandIntf commande = commandeFactory.getCommande();
        commande.execute();
    }

    private void initReadingQueue() {
        try {
            Path directoryPath = Paths.get("C:\\Users\\nicolas\\Music\\GrooveBerry_Musique");

            LOGGER.debug("Scanning audio files in directory : " + directoryPath.toAbsolutePath());
            AudioFileDirectoryScanner directoryScanner = new AudioFileDirectoryScanner(directoryPath);

            LOGGER.debug("Loading audio files in reading queue");
            ArrayList<AudioFile> audioFileList = directoryScanner.getAudioFileList();
            ReadingQueue.getInstance().addList(audioFileList);
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