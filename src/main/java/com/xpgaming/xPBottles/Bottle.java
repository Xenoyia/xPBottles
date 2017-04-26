package com.xpgaming.xPBottles;

import java.net.URL;
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

public class Bottle implements CommandExecutor {
	
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(src instanceof Player) {
        	Player player = (Player) src;
        	player.sendMessage(Text.of("§f[§bxP//§f] §b§l-- COMMANDS --"));
        	if(player.hasPermission("xpgaming.bottles.use")) {
        		player.sendMessage(Text.of("  §7> §b/bottle confirm §7- Convert your XP into bottles!"));
        	}
        	if(player.hasPermission("xpgaming.bottles.reload")) {
        		player.sendMessage(Text.of("  §7> §b/bottle reload §7- Reload plugin configuration!"));
        	}
		} else {
        	src.sendMessage(Text.of("§f[§bxP//§f] §b§l-- COMMANDS --"));
    		src.sendMessage(Text.of("  §7> §b/bottle confirm §7- Convert your XP into bottles!"));
    		src.sendMessage(Text.of("  §7> §b/bottle reload §7- Reload plugin configuration!"));
		}
		return CommandResult.success();
	}

}
