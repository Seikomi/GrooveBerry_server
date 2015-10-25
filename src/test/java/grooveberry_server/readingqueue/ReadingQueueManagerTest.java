package grooveberry_server.readingqueue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import grooveberry_server.audiofile.AudioFile;
import grooveberry_server.readingqueue.ReadingQueue;
import grooveberry_server.readingqueue.ReadingQueueManager;

public class ReadingQueueManagerTest {	
	private ReadingQueueManager readingQueueManager;
	
	private AudioFile leNeuf;
	private AudioFile free;
	private AudioFile aol;
	private AudioFile bornAgain;
	
	private static final ReadingQueue readingQueue = ReadingQueue.getInstance();
	
	@Before
	public void setUp() throws Exception {
		readingQueueManager = ReadingQueueManager.getInstance();
		
		this.leNeuf = new AudioFile("src/test/resources/ServerFileTest/9.wav");
		this.free = new AudioFile("src/test/resources/ServerFileTest/free.wav");
		this.aol = new AudioFile("src/test/resources/ServerFileTest/aol.wav");
		this.bornAgain = new AudioFile("src/test/resources/ServerFileTest/09 - Born Again.mp3");
		
		// Initialize Reading queue
		readingQueue.addLast(leNeuf);
		readingQueue.addLast(free);
		readingQueue.addLast(aol);
		readingQueue.addLast(bornAgain);
	}
	
	@After
	public void tearDown() throws Exception {
		readingQueueManager = null;
		
		this.leNeuf = null;
		this.free = null;
		this.aol = null;
		this.bornAgain = null;
		
		// Clean Reading queue
		readingQueue.clearQueue();
		readingQueue.setRandomised(false);
	}
	
	@Test
	public void testNextSongNoListeningTrack() {
		readingQueueManager.next();
		
		assertEquals(free, readingQueue.getCurrentTrack());
		assertEquals(1, readingQueue.getCurrentTrackPosition());
		assertTrue(readingQueue.getCurrentTrack().isPlaying());
	}
	
	@Test
	public void testNextSongWithListeningTrack() {
		readingQueueManager.play();
		readingQueueManager.next();
		
		assertEquals(free, readingQueue.getCurrentTrack());
		assertEquals(1, readingQueue.getCurrentTrackPosition());
		assertTrue(readingQueue.getCurrentTrack().isPlaying());
	}
	
	private boolean areIdentical(List<Integer[]> list) {
		Integer[] firstTrackIndexTab = list.get(0);
		for (Integer[] trackIndexTabIterator : list) {
			for (int i = 0; i < trackIndexTabIterator.length; i++) {
				if (!firstTrackIndexTab[i].equals(trackIndexTabIterator[i])) {
					return false;
				}
			}
		}
		return true;
	}
	
	private List<Integer[]> generateDistributionList(int numberOfTry) {
		List<Integer[]> tryList = new ArrayList<Integer[]>();
		
		int tryIndex = 0;
		while (tryIndex < numberOfTry) {
			Integer[] trackIndexTab = new Integer[readingQueue.size()];
			for (int i = 0; i < readingQueue.size(); i++) {
				readingQueueManager.next();
				trackIndexTab[i] = readingQueue.getCurrentTrackPosition();
				System.out.println(readingQueue.getCurrentTrackPosition());
			}
			tryList.add(trackIndexTab);
			tryIndex++;
		}
		
		return tryList;
	}

	@Test
	public void testRandomiseReadingQueue() {
		readingQueueManager.play();
		readingQueueManager.randomise(); // On rand
		
		List<Integer[]> tryList = generateDistributionList(10);
		assertFalse(areIdentical(tryList));
		
		readingQueueManager.randomise(); // Off rand
		
		tryList = generateDistributionList(10);
		assertTrue(areIdentical(tryList));		
	}
	
	@Test
	public void testNextSongDistributionWithNonRandomReadingQueue() {
		List<Integer[]> tryList = new ArrayList<Integer[]>();
		
		readingQueueManager.play();
		
		final int NUMBER_OF_TRY = 10; 
		int tryIndex = 0;
		while(tryIndex < NUMBER_OF_TRY) {
			Integer[] trackIndexTab = new Integer[readingQueue.size()];
			for (int i = 0; i < readingQueue.size(); i++) {
				readingQueueManager.next();
				trackIndexTab[i] = readingQueue.getCurrentTrackPosition();
			}
			tryList.add(trackIndexTab);
			tryIndex++;
		}
		assertTrue(areIdentical(tryList));
		
	}
	
	@Test
	public void testNextSongToTheEndOfReadingQueue() {
		readingQueue.setCurrentTrackPostion(readingQueue.size() - 1);
		readingQueueManager.next();
		
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
		assertTrue(readingQueue.getCurrentTrack().isPlaying());
	}
	
	@Test
	public void testPrevSongNoListeningTrack() {
		readingQueueManager.prev();
		
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
	}
	
	@Test
	public void testPrevSongWithListeningTrack() {
		readingQueue.setCurrentTrackPostion(1);
		readingQueueManager.play();
		readingQueueManager.prev();
		
		assertEquals(leNeuf, readingQueue.getCurrentTrack());
		assertEquals(0, readingQueue.getCurrentTrackPosition());
	}
	
	
	@Test
	public void testPauseReadingQueue() {
		readingQueueManager.play();
		assertTrue(readingQueue.getCurrentTrack().isPlaying());
		assertFalse(readingQueue.getCurrentTrack().isPaused());
		
		readingQueueManager.pause();
		assertTrue(readingQueue.getCurrentTrack().isPlaying());
		assertTrue(readingQueue.getCurrentTrack().isPaused());
	}

}
