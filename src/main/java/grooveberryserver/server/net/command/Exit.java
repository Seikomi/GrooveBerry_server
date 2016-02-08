package grooveberryserver.server.net.command;

class Exit implements CommandInterface {

	@Override
	public String apply(String[] args) {
		LOGGER.info("One client close this connection");
		return "#EXIT OK";
	}

}
