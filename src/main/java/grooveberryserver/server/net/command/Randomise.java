package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Randomise implements CommandInterface {

	@Override
	public String apply(String[] args) {
		LOGGER.info("Randomising reading queue");
		ReadingQueueManager.getInstance().randomise();
		return "#RANDOMISE OK";
	}
    
}
