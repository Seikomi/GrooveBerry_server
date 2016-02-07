package grooveberryserver.server.net.command;

import java.util.HashMap;

public class CommandFactory {
	private final HashMap<String, CommandInterface>	commands;
	
	private CommandFactory() {
		this.commands = new HashMap<>();
	}

	public void addCommand(String name, CommandInterface command) {
		this.commands.put(name, command);
	}
	
	public String executeCommand(String name) {
		if ( this.commands.containsKey(name) ) {
			return this.commands.get(name).apply();
		}
		return null;
	}

	public void listCommands() {
		// utilisation des stream (Java 8)
		System.out.println("Commands enabled :");
		this.commands.keySet().stream().forEach(System.out::println);
	}
	
	/* Factory pattern */
	public static CommandFactory init() {
		CommandFactory cf = new CommandFactory();
		
		// les commandes sont ajoutées ici en utilisant les expressions lambda.
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
