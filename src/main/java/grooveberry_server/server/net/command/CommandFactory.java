package grooveberry_server.server.net.command;

import java.io.PipedOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import grooveberry_server.manager.FileTransfertManager;
import grooveberry_server.manager.ReadingQueueManager;

public class CommandFactory {
	private String commandString;
	private PipedOutputStream pipedOutput;

	public CommandFactory(String commandString, PipedOutputStream pipedOutput) {
		this.commandString = commandString;
		this.pipedOutput = pipedOutput;
	}
    
    public CommandFactory(String commandString) {
		this.commandString = commandString;
		this.pipedOutput = null;
	}

	public CommandIntf getCommande() {
		CommandIntf commandToReturn = null;
		
		StringTokenizer stringTokenizer = new StringTokenizer(commandString, "@");
		String commandStringIndentifieur = stringTokenizer.nextToken();
		
		Path[] argumentsTab = new Path[stringTokenizer.countTokens()];
		for (int i = 0; i < argumentsTab.length; i++) {
			argumentsTab[i] = Paths.get(stringTokenizer.nextToken());
			System.out.println(argumentsTab[i]);
		}
		
		switch (commandStringIndentifieur) {
		case "#PLAY":
			commandToReturn = new Play(new ReadingQueueManager());
			break;
		case "#PAUSE":
			commandToReturn = new Pause(new ReadingQueueManager());
			break;
		case "#NEXT":
			commandToReturn = new Next(new ReadingQueueManager());
			break;
		case "#PREV":
			commandToReturn = new Prev(new ReadingQueueManager());
			break;
        case "#RANDOMISE":
			commandToReturn = new Randomise(new ReadingQueueManager());
			break;
		case "#DOWNLOAD":
			commandToReturn = new Download(argumentsTab, new FileTransfertManager(pipedOutput));
			break;
		case "#UPLOAD":
			commandToReturn = new Upload(argumentsTab, new FileTransfertManager(pipedOutput));
			break;
		case "#EXIT":
			commandToReturn = new Exit();
			break;

		default:
			break;
		}
		return commandToReturn;
	}
	
	

}
