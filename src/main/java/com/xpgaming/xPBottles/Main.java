package com.xpgaming.xPBottles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "xpbottles", name = "xP// Bottles", version = "0.2")
public class Main {

	CommandSpec confirm = CommandSpec.builder()
			.description(Text.of("Bottle 30 levels of XP!"))
			.permission("xpgaming.bottles.use")
			.executor(new BottleXP())
			.build();
	
	CommandSpec reload = CommandSpec.builder()
			.description(Text.of("Reload config!"))
			.permission("xpgaming.bottles.reload")
			.executor(new Reload())
			.build();
	
	CommandSpec bottle = CommandSpec.builder()
			.description(Text.of("Bottle XP!"))
			.permission("xpgaming.bottles")
			.child(confirm, "confirm", "c", "yes")
			.child(reload, "reload", "r")
			.executor(new Bottle())
			.build();
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	private File configFile;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	ConfigurationLoader<CommentedConfigurationNode> configLoader;
	
	@Listener
	public void onGameInitialization(GameInitializationEvent event) {
		Config.getInstance().setup(configFile, configLoader);
	}
	
	@Listener
	public void onPreInitializationEvent(GameInitializationEvent event) {
		Sponge.getCommandManager().register(this, bottle, "bottle", "xpbottle", "bottlexp", "xptobottle", "bxp");
	}
	
	@Listener
    public void onServerStart(GameStartedServerEvent event) {
		System.out.println("[xP// Bottles] Loaded v0.2!");
    }
	
	
}


