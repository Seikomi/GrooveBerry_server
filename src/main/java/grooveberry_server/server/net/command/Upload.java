package grooveberry_server.server.net.command;

import grooveberry_server.manager.FileTransfertManager;
import grooveberry_server.server.net.Server;

import java.nio.file.Path;

public class Upload implements CommandIntf {
	private Path[] pathTab;
	private FileTransfertManager fileTransfertManager;

	public Upload(Path[] pathTab, FileTransfertManager fileTransfertManager) {
		this.pathTab = pathTab;
		this.fileTransfertManager = fileTransfertManager;
	}


	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Upload command");
		fileTransfertManager.download();
		return "#UPLOAD OK";
	}

}
