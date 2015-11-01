package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;


public class Next implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send Next command");
		LOGGER.info("Switch to next track in reading queue");
		ReadingQueueManager.getInstance().next();
		return "#NEXT OK";
	}

}
