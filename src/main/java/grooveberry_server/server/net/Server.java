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
 * <p>
 * 	 Server class to build an instance of a <i>Grooveberry Server</i>. <br />
 * 	 <i>Grooveberry Server</i> is a remote sever to control a music playlist.
 * </p>
 * <p>
 * 	 They provide this sevices:
 * 	 <ul>
 * 	   <li>audio track management (play, pause, stop) ;</li>
 * 	   <li>reading queue management (start, stop, next, prev, randomize) ;</li>
 * 	   <li>volume management (volume up, volume down, mute) ;</li>
 * 	   <li>provide state information (get the current track play, get the reading queue ).</li>
 *   </ul>
 * </p>
 * 
 * @author  Nicolas Symphorien
 * @since   1.0
 */
public class Server {
    private final static Logger LOGGER = LoggerFactory.getLogger(Server.class);

    public static final String USER_HOME_PATH = System.getProperty("user.home");
	
    private ServerSocket commandServerSocket;
    private ServerSocket dataServerSocket;

    private Thread connectionClientsThread;
    
	/**
	 * Construct an {@code Server} instance listen on the two ports :
	 * <ul>
	 * 	 <li>{@code serverCommandPort} : the command port to send command to the server ;</li>
	 * 	 <li>{@code serverDataPort} : the data port to receive and send data to the server.</li>
	 * </ul>
	 * 
	 * @param serverCommandPort the command port of the {@code Server} instance 
	 * @param serverDataPort the data port of the {@code Server} instance 
	 * @throws InterruptedException
	 */
    public Server(final int serverCommandPort, final int serverDataPort) throws InterruptedException {
    	try {
            this.commandServerSocket = new ServerSocket(serverCommandPort);
            this.dataServerSocket = new ServerSocket(serverDataPort);
        } catch (IOException e) {
            LOGGER.error("Unexpexted socket deconnection", e);
        }
    }
    
    /**
     * Start the Groovery server and initialize the reading queue with all the file in the 
     * library. <br />
     * If this is the first lauch on this machine, make the libray directory in 
     * {@code HOME/.groovebery/library/} and add the .properties file in 
     * {@code HOME/.groovebery/grooveberry.properties}.
     */
    public void start() {
    	initServerFiles();
        initReadingQueue();
        startClientAcceptThread();
    }
    
    public void restart() {
    	// TODO
    }
    
    public void stop() {
    	// TODO
    }
        
    /**
     * Initialize library directory and the .properties file.
     */
    private void initServerFiles() {
    	Path mainDirectoryPath = Paths.get(USER_HOME_PATH	+ "/.grooveberry/");
    	Path serverPropertiesPath = Paths.get(USER_HOME_PATH + "/.grooveberry/grooveberry.properties");
    	Path serverLibraryDirectoryPath = Paths.get(USER_HOME_PATH + "/.grooveberry/library/");
    	
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
     * initialize the reading queue with all the file in the 
     * library.
     */
    private void initReadingQueue() {
        try {
            Path directoryPath = Paths.get(USER_HOME_PATH + "/.grooveberry/library/");
            
            if (directoryPath.toFile().exists()) {
	            LOGGER.debug("Scanning audio files in directory : " + directoryPath.toAbsolutePath());
	            AudioFileDirectoryScanner directoryScanner = new AudioFileDirectoryScanner(directoryPath);
	
	            LOGGER.debug("Loading audio files in reading queue");
	            ArrayList<AudioFile> audioFileList = directoryScanner.getAudioFileList();
	            if (audioFileList.size() != 0) {
	            	ReadingQueueManager.getInstance().addToReadingQueue(audioFileList);
	            } else {
	            	LOGGER.debug("No audio files in " + USER_HOME_PATH + "/library/ (normal if this is the first launch)");
	            }
            }
        } catch (IOException e) {
            LOGGER.error("Directory scanning error", e);
        }

    }
    
    /**
     * Start the client accept thread
     */
    private void startClientAcceptThread() {
        connectionClientsThread = new Thread(new ClientAccept(commandServerSocket, dataServerSocket));
        connectionClientsThread.setName("ClientAccept");
        connectionClientsThread.start();
        LOGGER.debug("Start ClientAccept Thread");
    }

}
