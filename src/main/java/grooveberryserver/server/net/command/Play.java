package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Play implements CommandInterface {

	@Override
	public String apply(String[] args) {
		//Server.printMessageInGui("[Client] Send Play command");
		LOGGER.info("Playing reading queue");
		ReadingQueueManager.getInstance().play();
		return "#PLAY OK";
	}

}
