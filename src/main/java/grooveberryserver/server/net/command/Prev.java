package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Prev implements CommandInterface {
	
	@Override
	public String apply(String[] args) {
		//Server.printMessageInGui("[Client] Send Prev command");
		LOGGER.info("Switch to previous track in reading queue");
		ReadingQueueManager.getInstance().prev();
		return "#PREV OK";
	}

}
