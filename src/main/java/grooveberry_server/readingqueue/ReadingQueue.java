package grooveberry_server.readingqueue;

import grooveberry_server.audiofile.AudioFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * La classe <code>ReadingQueue</code> permet la gestion d'un fil de lecture
 * de maniére automatisée en gérant les operations suivantes :
 * <ul>
 * <li>Lire le morceaux suivant;</li>
 * <li>Lire le morceaux précédent;</li>
 * <li>Ajouter des morceaux de musique;</li>
 * <li>Supprimer des morceaux de musique.</li>
 * </ul>
 * 
 * @see AudioFile
 * 
 * @author Nicolas Symphorien
 * @author Enzo Alunni Bagarelli
 * @version 1.0
 */
public final class ReadingQueue {

	/* Singleton Pattern */
	private static ReadingQueue instance;
	
	protected static synchronized ReadingQueue getInstance() {
		if (instance == null) {
			instance = new ReadingQueue();
		}
		return instance;
	}

	private	AudioFile currentTrack;
	private int currentTrackIndex;
	private boolean randomised;

    public void setRandomised(boolean randomised) {
        this.randomised = randomised;
    }
	
	private LinkedList<AudioFile> queue; //TODO Validate type of this container 

	/**
	 * Construire un fil de lecture vide.
	 *
	 */
	private ReadingQueue() {
		this.queue = new LinkedList<>();
		this.currentTrackIndex = -1;
        this.randomised = false; // active les playlist aleatoire
	}
	

	
	/**
	 * Vérifier si le fil de lecture est vide.
	 * 
	 * @return
	 * 		<code>true</code> si le fil de lecture est vide, <code>false</code> sinon.
	 */
	public boolean isEmpty() {
		return this.queue.isEmpty();
	}
	
	/**
	 * Récupérer le nombre de morceaux du fil de lecture.
	 * 
	 * @return
	 * 		le nombre de morceaux dans du fil de lecture.
	 */	
	public int size() {
		return this.queue.size();
	}
	
	/**
	 * Ajouter un morceau à la fin du fil de lecture.
	 * 
	 * @param
	 * 		track le morceau à ajouter
	 */
	public void addLast(AudioFile track){
		if (this.isEmpty()) {
			this.currentTrack = track;
			this.currentTrackIndex = 0;
		}
		this.queue.add(track);
	}
	
	/** 
	 * Ajouter un morceau à une position spécifique dans le fil
	 * de lecture.
	 * 
	 * @param index
	 * 		la positon où ajouter le morceau
	 * @param track
	 * 		le morceau à ajouter
	 */
	public void addAt(int index, AudioFile track) {
		if (this.isEmpty()) {
			this.currentTrack = track;
			this.currentTrackIndex = 0;
		}
		if (index <= this.currentTrackIndex && !this.isEmpty()) {
			this.currentTrackIndex++;
		}
		this.queue.add(index, track);
	}
	
	/**
	 * Ajouter l'ensemble d'une playlist à la fin du fil de lecture. //TODO class Playlist ?
	 * 
	 * @param playlist
	 * 		la playlist à ajouter
	 */
	public void addList(ArrayList<AudioFile> playlist) {
		if (this.isEmpty()) {
			this.currentTrack = playlist.get(0);
			this.currentTrackIndex = 0;
		}
		this.queue.addAll(playlist);
	}
	
	/** 
	 * Ajouter l'ensemble d'une playlist à une positon spécifique dans le fil //TODO class Playlist ?
	 * de lecture.
	 * 
	 * @param index
	 * 		la positon où ajouter la playlist
	 * @param playlist
	 * 		la playlist à ajouter
	 */
	public void addListAt(int index, ArrayList<AudioFile> playlist) {
		this.queue.addAll(index, playlist);
	}

	/**
	 * Supprimer un morceau à une position spécifique dans le fil
	 * de lecture.
	 * 
	 * @param index
	 * 		la position où supprimer le morceau
	 */
	public void remove(int index) {
		AudioFile removeFile = this.queue.get(index);
		if (removeFile == this.currentTrack) {
			this.currentTrack.deleteObservers();
			if (this.queue.getLast() != this.currentTrack) {				
				this.currentTrack = this.queue.get(currentTrackIndex + 1);
			} else {
				this.currentTrack = null;
				this.currentTrackIndex = -1;
			}
		}
		this.queue.remove(index);
		if (index <= this.currentTrackIndex) {
			this.currentTrackIndex--;
		}
	}
	
	/**
	 * Supprimer tout les morceaux contenue dans le fil de lecture.
	 */
	public void clearQueue() {
		this.queue.removeAll(this.queue);
		this.currentTrack = null;
		this.currentTrackIndex = -1;
	}
	
	/**
	 * Récupérer le morceau en cours de lecture.
	 * 
	 * @return
	 * 		le morceau en cours de lecture
	 */
	public AudioFile getCurrentTrack() {
		return this.currentTrack;
	}

	/**
	 * Récupérer la position du morceau en cours de lecture.
	 * 
	 * @return
	 * 		la position du morceau en cours de lecture
	 */
	public int getCurrentTrackPosition() {
		return this.currentTrackIndex;
	}

	/**
	 * Récupérer la liste des morceaux.
	 * 
	 * @return
	 * 		la liste des morceaux sous forme de linkedList d'AudioFile
     */
    
	 /* 
     * @see LikedList
     * @see Audiofile
	 */ 
	public List<AudioFile> getAudioFileList() {
		return Collections.unmodifiableList(this.queue);
	}

	/**
	 * Vérifier si le fil de lecture est randomisé.
	 * 
	 * @return
	 * 		<code>true</code> si le fil de lecture est randomisé, <code>false</code> sinon.
	 */
	public boolean isRandomised() {
		return randomised;
	}

	/**
	 * Changer le morceau en cours de lecture.
	 * 
	 * @param index
	 * 		la position du morceau
	 */
	public void setCurrentTrackPostion(int index) {
		this.currentTrackIndex = index;
		this.currentTrack = this.queue.get(index);
	}

}
