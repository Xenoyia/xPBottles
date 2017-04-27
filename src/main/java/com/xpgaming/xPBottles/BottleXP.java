package com.xpgaming.xPBottles;

import java.net.URL;
import net.minecraft.server.MinecraftServer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class BottleXP implements CommandExecutor {

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    	String prefix = Config.getInstance().getConfig().getNode("bottles", "prefix").getString();
        if(src instanceof Player) {
        	Player player = (Player) src;
        	if(player.gameMode().get() == GameModes.SURVIVAL) {
	        	int totalEXP = player.get(Keys.TOTAL_EXPERIENCE).orElseGet(() -> 0);
	        	int currentLevel = player.get(Keys.EXPERIENCE_LEVEL).orElseGet(() -> 0);
	        	int minLevels = Config.getInstance().getConfig().getNode("bottles", "min_level").getInt();
	        	if(currentLevel >= minLevels) {
	        		double bottlesNeeded = Math.ceil(totalEXP / 11);
	        		createBottles(bottlesNeeded, player);
	        		if(bottlesNeeded == 1) {
		        		player.sendMessage(Text.of("§f[§a"+prefix+"§f] §aSuccessfully exchanged your XP for §2" +  (int)bottlesNeeded + "§a bottle!"));
	        		} else player.sendMessage(Text.of("§f[§a"+prefix+"§f] §aSuccessfully exchanged your XP for §2" +  (int)bottlesNeeded + "§a bottles!"));
	        		player.offer(Keys.TOTAL_EXPERIENCE, 0);
	        	} else {
	        		player.sendMessage(Text.of("§f[§c"+prefix+"§f] §cYou need at least " + minLevels + " levels to convert to bottles!"));
	        	}
	            return CommandResult.success();
	        } else {
        		player.sendMessage(Text.of("§f[§c"+prefix+"§f] §cYou need to be in Survival mode to use this!"));
	            return CommandResult.success();
        }
        }
        else {
	        	src.sendMessage(Text.of("["+prefix+"] You need to be a player to run this command!"));
	            return CommandResult.success();
	        }
        }
	
	public void createBottles(double amount, Player player) {
		double stacks = 0;
		if(Math.ceil(amount / 64) < 1) {
			stacks = 1;
		} else {
			stacks = Math.ceil(amount / 64);
		}
		double a = amount;
		for(int i = 0; i < stacks; i++) {
			if(a >= 64) {
				ItemStack bottles = ItemStack.builder().itemType(ItemTypes.EXPERIENCE_BOTTLE).quantity(64).build();
				if(player.getInventory().query(Hotbar.class, GridInventory.class).size() < 36){
		            player.getInventory().offer(bottles);
		        } else {
		    	    World world = player.getLocation().getExtent();
		    	    Entity it = world
		    	        .createEntity(EntityTypes.ITEM, player.getLocation().getPosition());
    	    			it.offer(Keys.REPRESENTED_ITEM, bottles.createSnapshot());
		    	    SpawnCause spawnCause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
		    	    world.spawnEntity(it, Cause.source(spawnCause).build());
		        }
				a -= 64;
			} else {
				ItemStack bottles = ItemStack.builder().itemType(ItemTypes.EXPERIENCE_BOTTLE).quantity((int) a).build();
				if(player.getInventory().query(Hotbar.class, GridInventory.class).size() < 36){
		            player.getInventory().offer(bottles);
		        } else {
		    	    World world = player.getLocation().getExtent();
		    	    Entity it = world
		    	    		.createEntity(EntityTypes.ITEM, player.getLocation().getPosition());
		    	    		it.offer(Keys.REPRESENTED_ITEM, bottles.createSnapshot());
		    	    SpawnCause spawnCause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
		    	    world.spawnEntity(it, Cause.source(spawnCause).build());
		        }
				a = 0;
			}
		}
	}

}
