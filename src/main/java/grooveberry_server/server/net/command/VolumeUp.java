package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;

public class VolumeUp implements CommandInterface {
	
	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send VolumeUp command");
		LOGGER.info("Turn Up the volume by 10%!");
		ReadingQueueManager.getInstance().volumeUp();
		return "#VOLUP OK";
	}

}
