package com.xpgaming.xPBottles;

import java.io.File;
import java.io.IOException;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class Config {
private static Config instance = new Config();
	
	public static Config getInstance() {
		return instance;
	}
	
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;
	private CommentedConfigurationNode config;
	private File configFile;
	
	public void configCreate() {
		try {
			configFile.createNewFile();
			configLoad();
			CommentedConfigurationNode bottles = config.getNode("bottles").setComment("xP// Bottles coded by Xenoyia! Check out mc.xpgaming.com!");
			bottles.getNode("min_level").setComment("Do not set this to lower than 2, it's hardcoded to not give half a bottle.").setValue("30");			
			configSave();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
		this.configLoader = configLoader;
		this.configFile = configFile;
		if (!configFile.exists()) {
			configCreate();
		} else
			configLoad();
	}
	
	public CommentedConfigurationNode getConfig() {
		return config;
	}
	
	public void configLoad() {
		if (!configFile.exists()) {
			configCreate();
		} else
			try {
				config = configLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void configSave() {
		try {
			configLoader.save(config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAndLoadConfig() {
		configSave();
		configLoad();
	}
}
