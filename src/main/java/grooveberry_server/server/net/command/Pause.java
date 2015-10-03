package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class Pause implements CommandIntf {

	private ReadingQueueManager readingQueueManager;

	public Pause(ReadingQueueManager readingQueueManager) {
		super();
		this.readingQueueManager = readingQueueManager;
	}

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Pause command");
		LOGGER.info("Pausing reading queue");
		readingQueueManager.pause();
		return "#PAUSE OK";
	}

}
