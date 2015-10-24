package grooveberry_server.server.net.command;

import java.nio.file.Path;

import grooveberry_server.audiofile.manager.FileTransfertManager;
import grooveberry_server.server.net.Server;

public class Download implements CommandInterface {

	@Override
	public String apply() {
		// TODO Auto-generated method stub
		return null;
	}
//	private Path[] pathTab;
//
//	public Download(Path[] pathTab, FileTransfertManager fileTransfertManager) {
//		this.pathTab = pathTab;
//		this.fileTransfertManager = fileTransfertManager;
//	}
//
//	@Override
//	public String execute() {
//		Server.printMessageInGui("[Client] Send Download command");
//		fileTransfertManager.upload(pathTab);
//		return "#DOWNLOAD OK";
//	}
}
