package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;

public class VolumeUp implements CommandIntf {
	
	private ReadingQueueManager readingQueueManager;
	
	public VolumeUp(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}
	
	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send VolumeUp command");
		LOGGER.info("Turn Up the volume by 10%!");
		readingQueueManager.volumeUp();
		return "#VOLUP OK";
	}

}
