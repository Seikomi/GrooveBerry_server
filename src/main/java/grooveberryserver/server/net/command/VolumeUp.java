package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class VolumeUp implements CommandInterface {
	
	@Override
	public String[] apply(String[] args) {
		LOGGER.info("Turn Up the volume by 10%!");
		ReadingQueueManager.getInstance().volumeUp();
		return new String[] {"#VOLUP OK"};
	}

}
