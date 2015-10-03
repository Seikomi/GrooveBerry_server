package grooveberry_server.server.net.command;

import grooveberry_server.server.net.thread.ClientTreatment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CommandIntf {
	public final static Logger LOGGER = LoggerFactory.getLogger(ClientTreatment.class);
	
	public String execute();
}
