package grooveberry_server.library;

import grooveberry_server.audiofile.AudioFile;

import java.util.List;

public class Library {
	private List<AudioFile> audioFileList;
	//private File libraryFile;
	
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
		//libraryFile.add();
	}
	
	public void remove(AudioFile audioFile) {
		audioFileList.remove(audioFile);
	}
	
	public void clear() {
		audioFileList.clear();
	}

}
