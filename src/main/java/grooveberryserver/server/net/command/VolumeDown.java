package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class VolumeDown implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send VolumeDown command");
		LOGGER.info("Turn Down the volume by 10%!");
		ReadingQueueManager.getInstance().volumeDown();
		return "#VOLDOWN OK";
	}

}