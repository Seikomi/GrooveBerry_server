package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;


public class Next implements CommandIntf {

	private ReadingQueueManager readingQueueManager;

	public Next(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Next command");
		LOGGER.info("Switch to next track in reading queue");
		readingQueueManager.next();
		return "#NEXT OK";
	}

}
