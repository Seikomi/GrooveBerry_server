package grooveberry_server.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import grooveberry_server.audiofile.AudioFile;
import grooveberry_server.audiofile.ReadingQueue;

public class ReadingQueueManagerTest {	
	private ReadingQueueManager readingQueueManager;
	
	private AudioFile leNeuf;
	private AudioFile free;
	private AudioFile aol;
	private AudioFile bornAgain;
	
	private static final ReadingQueue readingQueue = ReadingQueue.getInstance();
	
	@Before
	public void setUp() throws Exception {
		readingQueueManager = new ReadingQueueManager();
		
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
