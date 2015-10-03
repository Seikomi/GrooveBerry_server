package grooveberry_server.manager;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.nio.file.Path;

public class FileTransfertManager {
	private PipedOutputStream pipedOutput;

	public FileTransfertManager(PipedOutputStream pipedOutput) {
		this.pipedOutput = pipedOutput;
	}

	public void upload(Path[] pathTab) {
		for (Path path : pathTab) {
			try {
				this.pipedOutput.write((path + "\n").getBytes());
				this.pipedOutput.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void download() {
		// TODO Auto-generated method stub
		
	}

}
