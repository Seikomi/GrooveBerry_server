/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grooveberry_server.server.net.command;

import grooveberry_server.readingqueue.ReadingQueueManager;
import grooveberry_server.server.net.Server;

/**
 *
 * @author nicolas
 */
public class Randomise implements CommandInterface {

	@Override
	public String apply() {
		Server.printMessageInGui("[Client] Send Randomize command");
		LOGGER.info("Randomising reading queue");
		ReadingQueueManager.getInstance().randomise();
		return "#RANDOMISE OK";
	}
    
}
