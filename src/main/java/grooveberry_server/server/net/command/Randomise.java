/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grooveberry_server.server.net.command;

import grooveberry_server.manager.ReadingQueueManager;
import grooveberry_server.server.net.Server;
import static grooveberry_server.server.net.command.CommandIntf.LOGGER;

/**
 *
 * @author nicolas
 */
public class Randomise implements CommandIntf {

    private ReadingQueueManager readingQueueManager;
	
	public Randomise(ReadingQueueManager readingQueueManager) {
		this.readingQueueManager = readingQueueManager;
	}

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Randomize command");
		LOGGER.info("Randomising reading queue");
		readingQueueManager.randomise();
		return "#RANDOMISE OK";
	}
    
}
