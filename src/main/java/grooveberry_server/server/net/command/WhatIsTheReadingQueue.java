package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class WhatIsTheReadingQueue implements CommandIntf {
	
	private ReadingQueueManager readingQueueManager;
	
	public WhatIsTheReadingQueue(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}
	
	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send WhatIsTheReadingQueue command");
		LOGGER.info("Questioning inference data base !");
		return readingQueueManager.whatIsTheReadingQueue();
	}

}
