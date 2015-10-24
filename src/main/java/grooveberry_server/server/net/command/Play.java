package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class Play implements CommandInterface {

	@Override
	public String apply() {
		Server.printMessageInGui("[Client] Send Play command");
		LOGGER.info("Playing reading queue");
		ReadingQueueManager.getInstance().play();
		return "#PLAY OK";
	}

}
