package grooveberryserver.library;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import grooveberryserver.audiofile.AudioFile;
import grooveberryserver.audiofile.AudioFileDirectoryScanner;
import grooveberryserver.server.net.Server;

public final class LibraryManager {
	
	/* Singleton Pattern */
	private static LibraryManager instance;
	
	public static synchronized LibraryManager getInstance() {
		if (instance == null) {
			instance = new LibraryManager();
		}
		return instance;
	}
	
	private Library library;
	
	private LibraryManager() {
		this.library = new Library();
		
	}
	
	public void scan() throws IOException {
		//LOGGER.debug("Scanning audio files in directory : " + directoryPath.toAbsolutePath());
        Path directoryPath = Paths.get(Server.USER_HOME_PATH + "/.grooveberry/library/");
		AudioFileDirectoryScanner directoryScanner = new AudioFileDirectoryScanner(directoryPath);

        //LOGGER.debug("Loading audio files in reading queue");
        ArrayList<AudioFile> audioFileList = directoryScanner.getAudioFileList();
        if (audioFileList.size() != 0) {
        	for (AudioFile audioFile : audioFileList) {
        		library.add(audioFile);
        	}
        } else {
        	//LOGGER.debug("No audio files in " + userHomePath + "/library/ (normal if this is the first launch)");
        }
	}
	
	
}
