package grooveberryserver.server.net.thread;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUpload implements Runnable {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(FileUpload.class);
	
	private ObjectOutputStream out;
	
	private PipedInputStream in;
	
	public FileUpload(ObjectOutputStream out, PipedInputStream input) {
		this.out = out;
		this.in = input;
	}


	@Override
	public void run() {
		int data = 0;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			data = in.read();
			while (data != -1) {
				
				if(data == Character.valueOf('\n')) {
					String fileToSendName = stringBuilder.toString();
					/*
					 *  TODO Vérifier l'existance du fichier à envoyer
					 *  NEED la liste des fichiers présent sur le serveur
					 */
	            	sendFile(fileToSendName);
	            	stringBuilder.delete(0, stringBuilder.length());
	        	} else {
	        		stringBuilder.append((char) data);
	        	}
				data = in.read();
			}
		} catch (IOException e) { //TODO verif cas normal
			LOGGER.info("End FileUpload Thread", e);
		}
		
	}


	private void sendFile(String path) {
		File file = new File(path);
		try {
			this.out.writeObject(file);
			this.out.flush();
			this.out.reset();
			LOGGER.debug("Send the file :" + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
