package com.xpgaming.xPBottles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Plugin(id = Main.id, name = Main.name, version = "0.3")
public class Main {
	public static final String id = "xpbottles";
	public static final String name = "xP// Bottles";
	private static final Logger log = LoggerFactory.getLogger(name);

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
		log.info("Loaded v0.3!");
	}
	
	@Listener
	public void onPreInitializationEvent(GameInitializationEvent event) {
		Sponge.getCommandManager().register(this, bottle, "bottle", "xpbottle", "bottlexp", "xptobottle", "bxp");
	}
	
	
}


