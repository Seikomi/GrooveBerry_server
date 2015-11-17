package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;

class Pause implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send Pause command");
		LOGGER.info("Pausing reading queue");
		ReadingQueueManager.getInstance().pause();
		return "#PAUSE OK";
	}

}
