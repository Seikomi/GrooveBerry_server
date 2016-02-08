package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class WhatIsThisSong implements CommandInterface {

	@Override
	public String apply(String[] args) {
		//Server.printMessageInGui("[Client] Send WhatIsThisSong command");
		LOGGER.info("Questioning inference data base !");
		return "#SONG " + ReadingQueueManager.getInstance().whatIsThisSong();
	}

}
