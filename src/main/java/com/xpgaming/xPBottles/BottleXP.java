package com.xpgaming.xPBottles;

import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.GameModeData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

public class BottleXP implements CommandExecutor {

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
        	Player player = (Player) src;
        	int totalEXP = player.get(Keys.TOTAL_EXPERIENCE).orElseGet(() -> 0);
        	if(totalEXP >= 11) {
        		int bottlesNeeded = totalEXP / 11;
        		createBottles(bottlesNeeded, player);
        		player.sendMessage(Text.of("[" + TextColors.AQUA + "xP//" + TextColors.RESET + "] " + TextColors.AQUA + "Successfully exchanged your EXP for " + TextColors.GOLD + bottlesNeeded + TextColors.AQUA + " bottles!"));
        	}
            return CommandResult.success();
        } else {
        	src.sendMessage(Text.of("[xP//] You need to be a player to run this command!"));
            return CommandResult.success();
        }
    }
	
	public void createBottles(int amount, Player player) {
		ItemStack Bottles = ItemStack.builder()
				.itemType(ItemTypes.EXPERIENCE_BOTTLE)
				.quantity(amount).build();
		Location<World> loc = player.getLocation();
		spawnItem(Bottles, loc);
	}
	
	public void spawnItem(ItemStack i, Location<World> spawnLocation) {
	    World world = spawnLocation.getExtent();
	    
	    /* Can't quite figure out how to give items, there is very little documentation on any 
	       Sponge API newer than 3, so methods have completely changed. Some people have recommended
	       DROPPING the item in the world, but I can't even figure THAT out - I can create an item
	       entity but I have no clue how to assign the ItemStack to it. */
	    
	    Entity Bottles = world
	            .createEntity(EntityTypes.ITEM, spawnLocation.getPosition())
	            .offer(Keys.REPRESENTED_ITEM, i);
	        SpawnCause spawnCause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
	        world.spawnEntity(Bottles, Cause.source(spawnCause).build());
	}

}
