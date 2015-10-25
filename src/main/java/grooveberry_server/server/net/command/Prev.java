package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class Prev implements CommandInterface {
	
	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send Prev command");
		LOGGER.info("Switch to previous track in reading queue");
		ReadingQueueManager.getInstance().prev();
		return "#PREV OK";
	}

}
