package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;

class VolumeDown implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send VolumeDown command");
		LOGGER.info("Turn Down the volume by 10%!");
		ReadingQueueManager.getInstance().volumeDown();
		return "#VOLDOWN OK";
	}

}
