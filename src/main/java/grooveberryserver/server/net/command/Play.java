package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Play implements CommandInterface {

	@Override
	public String[] apply(String[] args) {
		LOGGER.info("Playing reading queue");
		ReadingQueueManager.getInstance().play();
		return new String[] {"#PLAY OK"};
	}

}
