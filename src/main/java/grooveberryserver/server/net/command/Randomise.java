/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grooveberryserver.server.net.command;

import grooveberryserver.readingqueue.ReadingQueueManager;

class Randomise implements CommandInterface {

	@Override
	public String apply(String[] args) {
		//Server.printMessageInGui("[Client] Send Randomize command");
		LOGGER.info("Randomising reading queue");
		ReadingQueueManager.getInstance().randomise();
		return "#RANDOMISE OK";
	}
    
}
