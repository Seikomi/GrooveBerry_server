package grooveberry_server.server.net.command;

import java.nio.file.Path;

import grooveberry_server.manager.FileTransfertManager;
import grooveberry_server.server.net.Server;

public class Download implements CommandIntf {
	private Path[] pathTab;
	private FileTransfertManager fileTransfertManager;

	public Download(Path[] pathTab, FileTransfertManager fileTransfertManager) {
		this.pathTab = pathTab;
		this.fileTransfertManager = fileTransfertManager;
	}

	@Override
	public String execute() {
		Server.printMessageInGui("[Client] Send Download command");
		fileTransfertManager.upload(pathTab);
		return "#DOWNLOAD OK";
	}
}
