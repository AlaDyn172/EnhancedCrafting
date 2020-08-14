package xyz.AlaDyn172.EnhancedCrafting;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	public String pTitle = ChatColor.RED + "[EnhancedCrafting] " + ChatColor.AQUA;
	ConsoleCommandSender console = getServer().getConsoleSender();
	
	public void onEnable() {
	    Bukkit.getServer().getPluginManager().registerEvents(this, this);
	    
	    getConfig().options().copyDefaults(true);
	    saveConfig();
	    new Metrics(this);
	    
	    createRecipes();
	    	    
	    console.sendMessage(ChatColor.GREEN + "[EnhancedCrafting] Plugin developed by Echo. You're using v1.5!");
	}
	

	public void onDisable() {
		saveConfig();
	}
		
	public boolean createRecipe(Integer sth, Material craftItem, ArrayList<String> mats) {
		NamespacedKey recipeKey = new NamespacedKey(Bukkit.getPluginManager().getPlugin("EnhancedCrafting"), sth.toString());
		ItemStack recipe = new ItemStack(craftItem, 1);
		ShapedRecipe Recipe = new ShapedRecipe(recipeKey, recipe);
		Recipe.shape("abc","def","ghi");
				
		Recipe.setIngredient('a', Material.valueOf(mats.get(0)));
		Recipe.setIngredient('b', Material.valueOf(mats.get(1)));
		Recipe.setIngredient('c', Material.valueOf(mats.get(2)));
		Recipe.setIngredient('d', Material.valueOf(mats.get(3)));
		Recipe.setIngredient('e', Material.valueOf(mats.get(4)));
		Recipe.setIngredient('f', Material.valueOf(mats.get(5)));
		Recipe.setIngredient('g', Material.valueOf(mats.get(6)));
		Recipe.setIngredient('h', Material.valueOf(mats.get(7)));
		Recipe.setIngredient('i', Material.valueOf(mats.get(8)));
		getServer().addRecipe(Recipe);
		
		console.sendMessage(ChatColor.YELLOW + "[EnhancedCrafting] Adding Recipe... -> " + craftItem.toString());
		
		return false;
	}
	
	public boolean createRecipes() {
		int itemKey = 102330;
		
		console.sendMessage(ChatColor.AQUA + "[EnhancedCrafting] Adding Recipes...");

		for(String key : getConfig().getConfigurationSection("Recipes").getKeys(false)) {
			itemKey++;
			
			String craft_item = key;
			
			ArrayList<String> mats = new ArrayList<String>();
						
			for(String mat : getConfig().getConfigurationSection("Recipes." + key).getKeys(false)) {
				String materialul = getConfig().getString("Recipes." + key + "." + mat);						
				mats.add(materialul);
			}
			
			createRecipe(itemKey, Material.valueOf(craft_item), mats);			
		}
		
		return false;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if(commandLabel.equalsIgnoreCase("ecraft") && p.hasPermission("ecraft.use")) {
			
			if(args.length == 0 || args.length > 1) {
				p.sendMessage(pTitle + "Plugin created by " + ChatColor.YELLOW + "Echo");
				p.sendMessage(pTitle + "Available commands: ");
				p.sendMessage(ChatColor.YELLOW + "/ecraft reload " + ChatColor.GREEN + "(to reload the Crafting Recipes)");
			} else if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				
				if(p.hasPermission("ecraft.reload")) {
					getServer().clearRecipes();
					reloadConfig();
					saveConfig();
					createRecipes();
					p.sendMessage(pTitle + "Crafting Recipes reloaded!");
				} else {
					p.sendMessage(pTitle + "You don't have permission to use this command!");
				}
				
			} else {
				p.sendMessage(pTitle + "Plugin created by " + ChatColor.YELLOW + "Echo");
				p.sendMessage(pTitle + "Available commands: ");
				p.sendMessage(ChatColor.YELLOW + "/ecraft reload " + ChatColor.GREEN + "(to reload the Crafting Recipes)");
			}
			
		} else {
			p.sendMessage(pTitle + "You don't have permission to use this command!");
		}
		
		return false;
	}
}
