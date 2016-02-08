package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class WhatIsTheReadingQueue implements CommandInterface {
	
	@Override
	public String apply(String[] args) {
		//Server.printMessageInGui("[Client] Send WhatIsTheReadingQueue command");
		LOGGER.info("Questioning inference data base !");
		return "#LIST " + ReadingQueueManager.getInstance().whatIsTheReadingQueue();
	}

}
