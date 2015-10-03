package grooveberry_server.server.net.command;

import grooveberry_server.server.net.Server;

public class Exit implements CommandIntf {

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Exit command");
		LOGGER.info("One client close this connection");
		return "#EXIT OK";
	}

}
