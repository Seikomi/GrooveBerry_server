package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class VolumeUp implements CommandInterface {
	
	@Override
	public String apply(String[] args) {
		//Server.printMessageInGui("[Client] Send VolumeUp command");
		LOGGER.info("Turn Up the volume by 10%!");
		ReadingQueueManager.getInstance().volumeUp();
		return "#VOLUP OK";
	}

}
