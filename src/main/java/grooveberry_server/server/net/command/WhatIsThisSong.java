package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class WhatIsThisSong implements CommandIntf {
	
	private ReadingQueueManager readingQueueManager;
	
	public WhatIsThisSong(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}
	
	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send WhatIsThisSong command");
		LOGGER.info("Questioning inference data base !");
		return readingQueueManager.whatIsThisSong();
	}

}
