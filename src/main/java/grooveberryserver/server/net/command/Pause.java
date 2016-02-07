package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Pause implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send Pause command");
		LOGGER.info("Pausing reading queue");
		ReadingQueueManager.getInstance().pause();
		return "#PAUSE OK";
	}

}
