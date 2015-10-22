package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class VolumeDown implements CommandIntf {
	
	private ReadingQueueManager readingQueueManager;
	
	public VolumeDown(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}
	
	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send VolumeDown command");
		LOGGER.info("Turn Down the volume by 10%!");
		readingQueueManager.volumeDown();
		return "#VOLDOWN OK";
	}

}
