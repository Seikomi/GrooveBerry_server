package grooveberryserver.server.net.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import grooveberryserver.server.net.thread.ClientTreatment;

@FunctionalInterface
interface CommandInterface {
	public static final Logger LOGGER = LoggerFactory.getLogger(ClientTreatment.class);

	public String[] apply(String[] args);
}
