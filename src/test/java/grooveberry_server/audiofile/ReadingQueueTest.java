package grooveberry_server.audiofile;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadingQueueTest {
	private ReadingQueue readingQueue;
	
	private AudioFile leNeuf;
	private AudioFile free;
	private AudioFile aol;
	private AudioFile bornAgain;

	@Before
	public void setUp() throws Exception {
		this.readingQueue = ReadingQueue.getInstance();
		
		this.leNeuf = new AudioFile("src/test/resources/ServerFileTest/9.wav");
		this.free = new AudioFile("src/test/resources/ServerFileTest/free.wav");
		this.aol = new AudioFile("src/test/resources/ServerFileTest/aol.wav");
		this.bornAgain = new AudioFile("src/test/resources/ServerFileTest/09 - Born Again.mp3");
	}

	@After
	public void tearDown() throws Exception {
		this.readingQueue.clearQueue();
		
		this.leNeuf = null;
		this.free = null;
		this.aol = null;
		this.bornAgain = null;
	}

	@Test
	public void testReadingQueueInitialization() {
		assertEquals(0, readingQueue.size());
		assertEquals(null, readingQueue.getCurrentTrack());
		assertEquals(-1, readingQueue.getCurrentTrackPosition());
		
		// Singleton test
		ReadingQueue readingQueueNewInstance = ReadingQueue.getInstance();
		assertEquals(readingQueueNewInstance, readingQueue);
	}
	
	@Test
	public void testIsEmpty() {
		assertEquals(true, readingQueue.isEmpty());
		
		readingQueue.addLast(leNeuf);
		assertEquals(false, readingQueue.isEmpty());
		assertEquals(1, readingQueue.size());
	}
	
	@Test
	public void testAddLastEmptyReadingQueue() {
		readingQueue.addLast(leNeuf);
		assertEquals(false, readingQueue.isEmpty());
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
		assertEquals(1, readingQueue.size());
		
		readingQueue.addLast(free);
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
		assertEquals(2, readingQueue.size());
	}
	
	@Test
	public void testAddAtFirstPositionInReadingQueueEmpty() {
		readingQueue.addAt(0, aol);
		assertEquals(aol, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
		assertEquals(1, readingQueue.size());
	}
	
	@Test
	public void testAddAtFirstPositionInReadingQueueNotEmpty() {
		readingQueue.addLast(leNeuf);
		readingQueue.addLast(free);
		assertEquals(2, readingQueue.size());
		
		readingQueue.addAt(0, aol);
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(1, readingQueue.getCurrentTrackPosition());
		assertEquals(3, readingQueue.size());
	}
	
	@Test
	public void testAddAtSecondPositionInReadingQueueNotEmpty() {
		readingQueue.addLast(leNeuf);
		readingQueue.addLast(free);
		assertEquals(2, readingQueue.size());
		
		readingQueue.addAt(1, aol);
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
		assertEquals(3, readingQueue.size());
		
		
	}
	
	@Test
	public void testAddListInEmptyReadingQueue() {
		ArrayList<AudioFile> audioFileList = new ArrayList<AudioFile>();
		audioFileList.add(free);
		audioFileList.add(leNeuf);
		audioFileList.add(aol);
		
		readingQueue.addList(audioFileList);
		
		assertEquals(3, readingQueue.size());
		List<AudioFile> audioFileListInReadingQueue = readingQueue.getAudioFileList();
		for (int i = 0; i < audioFileListInReadingQueue.size(); i++) {
			assertEquals(audioFileListInReadingQueue.get(i), audioFileList.get(i));
		}
	}
	
	@Test
	public void testAddListInNotEmptyReadingQueue() {
		readingQueue.addLast(aol);
		assertEquals(1, readingQueue.size());
		
		ArrayList<AudioFile> audioFileList = new ArrayList<AudioFile>();
		audioFileList.add(free);
		audioFileList.add(leNeuf);
		
		readingQueue.addList(audioFileList);
		
		assertEquals(3, readingQueue.size());
		List<AudioFile> audioFileListInReadingQueue = readingQueue.getAudioFileList();
		assertEquals(audioFileListInReadingQueue.get(0), aol);
		assertEquals(audioFileListInReadingQueue.get(1), free);
		assertEquals(audioFileListInReadingQueue.get(2), leNeuf);
	}
	
	@Test
	public void testAddListAtsecondPositionInNotEmptyReadingQueue() {
		readingQueue.addLast(aol);
		readingQueue.addLast(free);
		assertEquals(2, readingQueue.size());
		
		ArrayList<AudioFile> audioFileList = new ArrayList<AudioFile>();
		audioFileList.add(leNeuf);
		audioFileList.add(bornAgain);
		
		readingQueue.addListAt(1, audioFileList);
		
		assertEquals(4, readingQueue.size());
		List<AudioFile> audioFileListInReadingQueue = readingQueue.getAudioFileList();
		assertEquals(audioFileListInReadingQueue.get(0), aol);
		assertEquals(audioFileListInReadingQueue.get(1), leNeuf);
		assertEquals(audioFileListInReadingQueue.get(2), bornAgain);
		assertEquals(audioFileListInReadingQueue.get(3), free);
	}
	
	@Test
	public void testRemoveCurrentTrack() {
		readingQueue.addLast(aol);
		readingQueue.addLast(free);
		assertEquals(2, readingQueue.size());
		
		readingQueue.remove(0);
		assertEquals(1, readingQueue.size());
		assertEquals(free, readingQueue.getCurrentTrack());
	}
	
	@Test
	public void testRemoveNotCurrentTrackButLast() {
		readingQueue.addLast(aol);
		readingQueue.addLast(free);
		assertEquals(2, readingQueue.size());
		
		readingQueue.remove(1);
		assertEquals(1, readingQueue.size());
		assertEquals(aol, readingQueue.getCurrentTrack());
	}
	
	@Test
	public void testRemoveUniqueTrack() {
		readingQueue.addLast(aol);
		assertEquals(1, readingQueue.size());
		
		readingQueue.remove(0);
		assertEquals(0, readingQueue.size());
		assertEquals(null, readingQueue.getCurrentTrack());
		assertEquals(-1, readingQueue.getCurrentTrackPosition());
	}
	
	@Test
	public void testChangeCurrentTrackPositiob() {
		readingQueue.addLast(leNeuf);
		readingQueue.addLast(free);
		readingQueue.addLast(bornAgain);
		readingQueue.addLast(aol);
		
		assertEquals(4, readingQueue.size());
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
		
		readingQueue.setCurrentTrackPostion(2);
		assertEquals(4, readingQueue.size());
		assertEquals(bornAgain, readingQueue.getCurrentTrack());
		assertEquals(2, readingQueue.getCurrentTrackPosition());
	}
	

}
