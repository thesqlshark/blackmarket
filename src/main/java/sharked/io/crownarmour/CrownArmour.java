package sharked.io.crownarmour;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import sharked.io.crownarmour.command.BlackmarketCommand;
import sharked.io.crownarmour.data.InventoryDataSlot;
import sharked.io.crownarmour.listener.MenuListener;
import sharked.io.crownarmour.user.UserManager;
import sharked.io.lib.api.command.Shark;
import sharked.io.lib.api.files.DataFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrownArmour extends JavaPlugin {

    @Getter
    private DataFile configFile;

    @Getter
    private Shark command;
    @Getter
    private UserManager userManager;

    @Getter
    private Map<String, InventoryDataSlot> load;
    @Getter
    private Map<String, InventoryDataSlot> king;
    @Getter
    private Map<String, InventoryDataSlot> prince;
    @Getter
    private Map<String, InventoryDataSlot> bishop;

    @Getter
    private Economy econ;


    @Override
    public void onEnable() {
        configFile = new DataFile(this, "config", true);
        command = new Shark(this);
        userManager = new UserManager();
        command.registerCommand(new BlackmarketCommand(this));
        getServer().getPluginManager().registerEvents(new MenuListener(this),this);
        load();

        if (!this.setupEconomy()) {
            this.getServer().getConsoleSender().sendMessage("§c§lSetupEconomy FAILED -- You need Vault AND an Economy Plugin for this plugin to work");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void load() {
        load = new HashMap<>();
        king = new HashMap<>();
        prince = new HashMap<>();
        bishop = new HashMap<>();

        
        for (String s : configFile.getConfig().getConfigurationSection("load").getKeys(false)) {
            Material material = Material.getMaterial(configFile.getString("load." + s + ".mat"));
            String name = configFile.getString("load." + s + ".name");
            List<String> lore = configFile.getConfig().getStringList("load." + s + ".lore");
            int slot = configFile.getConfig().getInt("load." + s + ".slot");
            String id = configFile.getString("load." + s + ".gui");
            load.put(s, new InventoryDataSlot(material, name, lore,slot, id));
        }

        for (String s : configFile.getConfig().getConfigurationSection("king").getKeys(false)) {
            Material material = Material.getMaterial(configFile.getString("king." + s + ".mat"));
            String name = configFile.getString("king." + s + ".name");
            List<String> lore = configFile.getConfig().getStringList("king." + s + ".lore");
            int slot = configFile.getConfig().getInt("king." + s + ".slot");
            String id = configFile.getString("king." + s + ".price");
            king.put(s, new InventoryDataSlot(material, name, lore,slot, id));
        }

        for (String s : configFile.getConfig().getConfigurationSection("prince").getKeys(false)) {
            Material material = Material.getMaterial(configFile.getString("prince." + s + ".mat"));
            String name = configFile.getString("prince." + s + ".name");
            List<String> lore = configFile.getConfig().getStringList("prince." + s + ".lore");
            int slot = configFile.getConfig().getInt("prince." + s + ".slot");
            String id = configFile.getString("prince." + s + ".price");
            prince.put(s, new InventoryDataSlot(material, name, lore,slot, id));
        }

        for (String s : configFile.getConfig().getConfigurationSection("bishop").getKeys(false)) {
            Material material = Material.getMaterial(configFile.getString("bishop." + s + ".mat"));
            String name = configFile.getString("bishop." + s + ".name");
            List<String> lore = configFile.getConfig().getStringList("bishop." + s + ".lore");
            int slot = configFile.getConfig().getInt("bishop." + s + ".slot");
            String id = configFile.getString("bishop." + s + ".price");
            bishop.put(s, new InventoryDataSlot(material, name, lore, slot, id));
        }
    }

    private boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.econ = rsp.getProvider();
        return this.econ != null;
    }
}
