package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class Play implements CommandIntf {
	
	private ReadingQueueManager readingQueueManager;
	
	public Play(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Play command");
		LOGGER.info("Playing reading queue");
		readingQueueManager.play();
		return "#PLAY OK";
	}

}
