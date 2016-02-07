package grooveberryserver.library;

import java.util.List;

import grooveberryserver.audiofile.AudioFile;

public class Library {
	private List<AudioFile> audioFileList;
	
	public Library() {
		this(null);
	}
	
	public Library(List<AudioFile> audioFileList) {
		this.audioFileList = audioFileList;
	}
	
	public boolean isEmpty() {
		return audioFileList.isEmpty();
	}
	
	public void add(AudioFile audioFile) {
		audioFileList.add(audioFile);
	}
	
	public void remove(AudioFile audioFile) {
		audioFileList.remove(audioFile);
	}
	
	public void clear() {
		audioFileList.clear();
	}

}
