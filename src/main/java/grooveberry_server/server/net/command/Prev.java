package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class Prev implements CommandIntf {
	
	private ReadingQueueManager readingQueueManager;
	
	public Prev(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Prev command");
		LOGGER.info("Switch to previous track in reading queue");
		readingQueueManager.prev();
		return "#PREV OK";
	}

}
