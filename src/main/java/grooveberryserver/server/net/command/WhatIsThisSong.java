package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class WhatIsThisSong implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send WhatIsThisSong command");
		LOGGER.info("Questioning inference data base !");
		return "#SONG " + ReadingQueueManager.getInstance().whatIsThisSong();
	}

}
