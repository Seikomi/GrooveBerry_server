package grooveberry_server.readingqueue;

import grooveberry_server.audiofile.AudioFile;
import grooveberry_server.systemvolume.AudioUtility;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ReadingQueueManager implements Observer {
	
	/* Singleton Pattern */
	private static ReadingQueueManager instance;
	
	public static synchronized ReadingQueueManager getInstance() {
		if (instance == null) {
			instance = new ReadingQueueManager();
		}
		return instance;
	}
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ReadingQueueManager.class);
	
	private ReadingQueue readingQueue = ReadingQueue.getInstance();
	
	private ReadingQueueManager() {
		
	}

	/**
	 * Passer au morceau suivant dans le fil de lecture
	 */
	public void next() {
		readingQueue.getCurrentTrack().deleteObservers();
		
		int trackIndex = readingQueue.getCurrentTrackPosition();
		if ((trackIndex + 1 < readingQueue.size()) || readingQueue.isRandomised()) {
				changeTrack(true);
		} else {
			readingQueue.setCurrentTrackPostion(0);
		}
		readingQueue.getCurrentTrack().addObserver(this);
		readingQueue.getCurrentTrack().play();
		
		
	}
	
	/**
	 * Passer au morceau précédent dans le fil de lecture
	 */
	public void prev() {
		readingQueue.getCurrentTrack().deleteObservers();
		
		int trackIndex = readingQueue.getCurrentTrackPosition();
		if ((trackIndex - 1 >= 0)) {
			changeTrack(false);
			readingQueue.getCurrentTrack().addObserver(this);
			readingQueue.getCurrentTrack().play();
		}
	}
	
	/**
	 * Activer/désactiver le passage aléatoire à un morceau
	 */
	public void play() {
		readingQueue.getCurrentTrack().deleteObservers();
		ReadingQueue.getInstance().getCurrentTrack().addObserver(this);
		readingQueue.getCurrentTrack().play();
		
	}

	public void pause() {
		readingQueue.getCurrentTrack().pause();
		
	}
	
	public void randomise() {
	    if (readingQueue.isRandomised()) {
	        readingQueue.setRandomised(false);
	    } else {
	        readingQueue.setRandomised(true);
	    }
	}

	public void volumeUp() {
		Float volume = AudioUtility.getMasterOutputVolume();
		if (volume >= 0f && volume <= 0.9f) {
			AudioUtility.setMasterOutputVolume(volume + 0.1f);
		} else if (volume > 0.9 && volume < 1f) {
			AudioUtility.setMasterOutputVolume(1f);
		}
			
	}

	public void volumeDown() {
		Float volume = AudioUtility.getMasterOutputVolume();
		if (volume >= 0.1f && volume <= 1f) {
			AudioUtility.setMasterOutputVolume(volume - 0.1f);
		} else if (volume < 0.1f && volume > 0f) {
			AudioUtility.setMasterOutputVolume(0f);
		}
		
	}

	public String whatIsThisSong() {
		return readingQueue.getCurrentTrack().getName();
		
	}

	public String whatIsTheReadingQueue() {
		StringBuilder stringBuilder = new StringBuilder();
		for (AudioFile audioFile : readingQueue.getAudioFileList()) {
			if (audioFile.equals(readingQueue.getCurrentTrack())) {
				stringBuilder.append(">> ");
			}
			stringBuilder.append(audioFile.getName());
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	public void scan() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Mettre fin au morceau en cours de lecture en fonction de 
	 * <code>trackFlags</code>.
	 * @param trackFlags
	 * 		Le statut d'un fichier audio.
	 * 	
	 * @see AudioFile, AudioFile.TrackFlags
	 */
	private void endCurrentTrack(TrackFlags trackFlags) {
		if (trackFlags.played) {
			if (trackFlags.paused) {
				readingQueue.getCurrentTrack().pause();
			}
			readingQueue.getCurrentTrack().stop();
		}
		if (trackFlags.muted) {
			readingQueue.getCurrentTrack().mute();
		}
		if (trackFlags.looped) {
			readingQueue.getCurrentTrack().loop();
		}
	}
	/**
	 * Changer le morceau selon <code>forward</code>, <code>isRandomised</code> et
	 * <code>trackFlags</code>.<br/>
	 * 
	 * @param forward
	 * 		Si <code>forward = true</code> alors passe au morceau suivant, sinon passe
	 * 		au morceau précédent. 
	 * @param trackFlags
	 * 		Le statut d'un fichier audio.
	 * 	
	 * @see AudioFile, AudioFile.TrackFlags
	 */
	private void changeCurrentTrack(boolean forward, TrackFlags trackFlags) {
		int shiftInt;
		if (readingQueue.isRandomised()) {
			Random rand = new Random();
			shiftInt = rand.nextInt(readingQueue.size() - 1);
		} else if (forward) {
			shiftInt = readingQueue.getCurrentTrackPosition() + 1;
		} else {
			shiftInt =  readingQueue.getCurrentTrackPosition() - 1;
		}
		readingQueue.setCurrentTrackPostion(shiftInt);
	}
	
	/**
	 * Changer le statut du morceau en cours de lecture en fonction de 
	 * <code>trackFlags</code>.
	 * @param trackFlags
	 * 		Le statut d'un fichier audio.
	 * 
	 * @see AudioFile, AudioFile.TrackFlags
	 */
	private void changeCurrentTrackStatus(TrackFlags trackFlags) {
		if (trackFlags.muted) {
			readingQueue.getCurrentTrack().mute();
		}
		if (trackFlags.looped) {
			readingQueue.getCurrentTrack().loop();
		}

	}
	
	/**
	 * Changer le morceau selon <code>forward</code>.
	 * 
	 * @param forward
	 * 		Si <code>forward = true</code> alors passe au morceau suivant, sinon passe
	 * 		au morceau précedent. 
	 */
	private void changeTrack(boolean forward){
		TrackFlags previousTrackFlags = new TrackFlags(readingQueue.getCurrentTrack());
		
		endCurrentTrack(previousTrackFlags);
		changeCurrentTrack(forward, previousTrackFlags);
		changeCurrentTrackStatus(previousTrackFlags);
	}

    /**
	 * Structure de données represantant l'etat d'un fichier audio.
	 * 
	 * @see AudioFile
	 * 
	 * @author Nicolas Symphorien
	 * @version 1.0
	 */
	private class TrackFlags {
		public boolean muted;
		public boolean looped;
		public boolean played;
		public boolean paused;
		
		/**
		 * Construire une structure de données représantant le fichier audio
		 * <code>track</code>.
		 * 
		 * @param track
		 * 		le fichier audio a analyser.
		 * 
		 * @see AudioFile
		 */
		public TrackFlags(AudioFile track) {
			muted = track.isMuted();
			looped = track.isLooping();
			played = track.isPlaying();
			paused = track.isPaused();
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof AudioFile) {
			String state = (String) arg;
			if (state.equals("EndOfPlay")) {
				endOfPlay();
			}
			else if (state.equals("StopOfPlay")) {
				stopOfPlay();
			}
		}
		
	}

	/**
	 * Événement qui survient à la fin de la lecture d'un morceau dans
	 * le fil de lecture.
	 */
	private void endOfPlay() {
		next();
		
		LOGGER.info("End of Play event : switch to next track");
		
	}
	
	/**
	 * Événement qui survient à la mise sur stop d'un morceau dans
	 * le fil de lecture.
	 */
	private void stopOfPlay() {
		LOGGER.info("Stop of Play event");
	}

	public void addToReadingQueue(ArrayList<AudioFile> audioFileList) {
		readingQueue.addList(audioFileList);
		
	}







}
