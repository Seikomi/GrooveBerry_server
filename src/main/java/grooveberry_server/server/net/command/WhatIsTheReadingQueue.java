package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;

class WhatIsTheReadingQueue implements CommandInterface {
	
	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send WhatIsTheReadingQueue command");
		LOGGER.info("Questioning inference data base !");
		return "#LIST " + ReadingQueueManager.getInstance().whatIsTheReadingQueue();
	}

}
