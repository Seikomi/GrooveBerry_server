package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class WhatIsTheReadingQueue implements CommandInterface {
	
	@Override
	public String apply(String[] args) {
		LOGGER.info("Questioning inference data base !");
		return "#LIST " + ReadingQueueManager.getInstance().whatIsTheReadingQueue();
	}

}
