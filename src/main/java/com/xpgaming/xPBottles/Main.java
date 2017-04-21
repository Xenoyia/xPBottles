package com.xpgaming.xPBottles;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(id = "xPBottles", name = "xP// Bottles", version = "0.1")
public class Main {
	private Logger logger;
	
	CommandSpec Commands = CommandSpec.builder()
			.description(Text.of("Bottle 30 levels of XP!"))
			.permission("xpgaming.bottles.use")
			.executor(new BottleXP())
			.build();
	
	@Inject
	public Main(Logger logger) {
	    this.logger = logger;
	}
	
	public Logger Log() {
	    return logger;
	}
	
	@Listener
	public void onPreInitializationEvent(GameInitializationEvent event) {
		Sponge.getCommandManager().register(this, Commands, "bottlexp", "xpbottle", "bottle");
	}
	
	@Listener
    public void onServerStart(GameStartedServerEvent event) {
        Log().info("[xP//] xPBottles has loaded: v0.1");
    }
	
	
}


