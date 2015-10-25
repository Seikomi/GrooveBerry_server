package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class Pause implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send Pause command");
		LOGGER.info("Pausing reading queue");
		ReadingQueueManager.getInstance().pause();
		return "#PAUSE OK";
	}

}
