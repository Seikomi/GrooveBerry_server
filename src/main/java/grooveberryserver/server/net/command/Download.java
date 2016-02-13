package grooveberryserver.server.net.command;

import java.nio.file.Path;
import java.nio.file.Paths;

import grooveberryserver.DataTransfertManager;
import grooveberryserver.ServerPropertiesMafformedException;
import grooveberryserver.ServerPropertiesNotFoundException;

class Download implements CommandInterface {

	@Override
	public String[] apply(String[] args) {
		try {
			DataTransfertManager.getInstance().startDataSocket();
		} catch (ServerPropertiesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerPropertiesMafformedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Path[] filesToUploadPath = new Path[args.length];
		for (int i = 0; i < args.length; i++) {
			filesToUploadPath[i] = Paths.get(args[i]);
		}		
		
		// TODO block until the upload are made
		DataTransfertManager.getInstance().upload(filesToUploadPath);
		
		
		DataTransfertManager.getInstance().stopDataSocket();
		
		return new String[] {"DOWNLOAD OK"};
	}
}
