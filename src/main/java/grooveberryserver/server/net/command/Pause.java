package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Pause implements CommandInterface {

	@Override
	public String[] apply(String[] args) {
		LOGGER.info("Pausing reading queue");
		ReadingQueueManager.getInstance().pause();
		return new String[] {"#PAUSE OK"};
	}

}
