package grooveberry_server.server.net.command;

class Exit implements CommandInterface {

	@Override
	public String apply() {
		//Server.printMessageInGui("[Client] Send Exit command");
		LOGGER.info("One client close this connection");
		return "#EXIT OK";
	}

}
