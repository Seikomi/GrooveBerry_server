package grooveberryserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTransfertManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataTransfertManager.class);
	private static DataTransfertManager instance;
	
	private Properties serverProperties;

	private DataTransfertManager() {
		this.serverProperties = null;
	}

	public static synchronized DataTransfertManager getInstance() {
		if (instance == null) {
			instance = new DataTransfertManager();
		}
	return instance;
	}
	
	public void startDataSocket() throws ServerPropertiesNotFoundException, ServerPropertiesMafformedException {
		if (serverProperties == null) {
			throw new ServerPropertiesNotFoundException();
		}
		try {
			ServerSocket dataServerSocket = new ServerSocket(Integer.parseInt(serverProperties.getProperty("server.port.data")));
			
			
		} catch (NumberFormatException | IOException e ) {
			throw new ServerPropertiesMafformedException();
		} 
		
	}
	
	public void stopDataSocket() {
		// TODO Auto-generated method stub
		
	}

	public void upload(Path[] filesToUploadPath) {
		// TODO Auto-generated method stub
	}

	public void setServerProperties(Properties serverProperties) {
		this.serverProperties = serverProperties;		
	}

}
