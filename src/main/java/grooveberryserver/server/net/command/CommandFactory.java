package grooveberryserver.server.net.command;

import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import grooveberryserver.DataTransfertManager;

public class CommandFactory {
	private final HashMap<String, CommandInterface> commands;;

	private CommandFactory() {
		this.commands = new HashMap<>();
	}

	private static void sendServerProperties(Properties serverProperties) {
		DataTransfertManager.getInstance().setServerProperties(serverProperties);
		
	}

	public void addCommand(String name, CommandInterface command) {
		this.commands.put(name, command);
	}

	public String[] executeCommand(String commandString) {
		String[] toReturn = null;

		StringTokenizer stringTokenizer = new StringTokenizer(commandString);
		String commandHeader = stringTokenizer.nextToken();
		if (commands.containsKey(commandHeader)) {
			if (stringTokenizer.hasMoreTokens()) {
				String[] args = new String[stringTokenizer.countTokens()];
				for (int i = 0; i < args.length; i++) {
					args[i] = stringTokenizer.nextToken();
				}
				toReturn = this.commands.get(commandHeader).apply(args);
			} else {
				toReturn = this.commands.get(commandHeader).apply(null);
			}
		}

		return toReturn;
	}

	public static CommandFactory init(Properties serverProperties) {
		CommandFactory cf = new CommandFactory();
		sendServerProperties(serverProperties);
		
		cf.addCommand("#PLAY", new Play());
		cf.addCommand("#PAUSE", new Pause());
		cf.addCommand("#NEXT", new Next());
		cf.addCommand("#PREV", new Prev());
		cf.addCommand("#RANDOMISE", new Randomise());
		cf.addCommand("#VOLDOWN", new VolumeDown());
		cf.addCommand("#VOLUP", new VolumeUp());
		cf.addCommand("#SONG", new WhatIsThisSong());
		cf.addCommand("#LIST", new WhatIsTheReadingQueue());

		return cf;
	}
}
