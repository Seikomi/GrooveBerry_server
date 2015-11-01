package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;

public class WhatIsThisSong implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send WhatIsThisSong command");
		LOGGER.info("Questioning inference data base !");
		return "#SONG " + ReadingQueueManager.getInstance().whatIsThisSong();
	}

}
