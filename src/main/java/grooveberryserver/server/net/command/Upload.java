package grooveberryserver.server.net.command;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import grooveberryserver.DataTransfertManager;

class Upload implements CommandInterface {

	@Override
	public String apply(String[] args) {
		List<File> audioFileList = new ArrayList<>();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				//TODO implement the args cast to audioFileList to upload
			}
		}
		
		DataTransfertManager.getInstance().upload(audioFileList);
		return null;
	}

}
