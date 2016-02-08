package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class WhatIsThisSong implements CommandInterface {

	@Override
	public String apply(String[] args) {
		LOGGER.info("Questioning inference data base !");
		return "#SONG " + ReadingQueueManager.getInstance().whatIsThisSong();
	}

}
