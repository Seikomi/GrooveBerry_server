package grooveberryserver.server.net.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownload implements Runnable {
	private final static Logger LOGGER = LoggerFactory.getLogger(FileDownload.class);
	private ObjectInputStream in;

	public FileDownload(ObjectInputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		boolean connectionClosed = false;
		while (!connectionClosed) {
			try {
				File fileReceive = (File) this.in.readObject();

				File newFile = new File("ServerFiles/" + fileReceive.getName());
				if (!newFile.exists()) {
					fileCopy(fileReceive, newFile);
					LOGGER.info("Download of " + newFile.getName());
				} else {
					LOGGER.warn("The file " + newFile.getName() + " already exists");
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				connectionClosed = true;
				LOGGER.info("End FileDownload Thread", e);
			}
		}

	}

	private void fileCopy(File source, File destination) {
		FileInputStream sourceStream = null;
		FileOutputStream destinationStream = null;
		try {
			destination.createNewFile();

			sourceStream = new FileInputStream(source);
			destinationStream = new FileOutputStream(destination);

			byte buffer[] = new byte[1024];
			int numberOfReadingByte;
			while ((numberOfReadingByte = sourceStream.read(buffer)) != -1) {
				destinationStream.write(buffer, 0, numberOfReadingByte);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				sourceStream.close();
				destinationStream.close();
				LOGGER.debug("Copy " + destination.getName() + " in directory : " + destination.getParent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
