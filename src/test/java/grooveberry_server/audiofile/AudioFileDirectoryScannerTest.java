package grooveberry_server.audiofile;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AudioFileDirectoryScannerTest {
	private AudioFileDirectoryScanner audioFileDirectoryScanner;

	@Before
	public void setUp() throws Exception {
		Path audioFileDirectoryPath = Paths.get("src/test/resources/ServerFileTest/");
		this.audioFileDirectoryScanner = new AudioFileDirectoryScanner(audioFileDirectoryPath);
	}

	@After
	public void tearDown() throws Exception {
		this.audioFileDirectoryScanner = null;
	}

	@Test
	public void testScanDirectory() {
		List<AudioFile> audioFileList = audioFileDirectoryScanner.getAudioFileList();
		Set<String> audioFileSet = new HashSet<String>();
		for (AudioFile audioFile : audioFileList) {
			audioFileSet.add(audioFile.getName());
		}
		
		assertTrue(audioFileSet.contains("09 - Born Again.mp3"));
		assertTrue(audioFileSet.contains("9.wav"));
		assertTrue(audioFileSet.contains("aol.wav"));
		assertTrue(audioFileSet.contains("free.wav"));
	}

}
