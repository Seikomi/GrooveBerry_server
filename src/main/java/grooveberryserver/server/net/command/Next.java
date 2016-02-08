package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;


class Next implements CommandInterface {

	@Override
	public String apply() {
		LOGGER.info("Switch to next track in reading queue");
		ReadingQueueManager.getInstance().next();
		return "#NEXT OK";
	}

}