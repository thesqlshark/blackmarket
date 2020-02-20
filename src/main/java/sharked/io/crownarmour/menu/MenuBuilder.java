package sharked.io.crownarmour.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.event.inventory.*;
import sharked.io.crownarmour.CrownArmour;
import sharked.io.lib.util.Color;


public abstract class MenuBuilder<T> implements InventoryHolder
{
    @Setter
    private String title;
    @Getter @Setter
    private int rows;
    @Getter @Setter
    private Inventory inventory;
    @Getter @Setter
    private CrownArmour plugin;
    @Getter @Setter
    private Player player;

    public MenuBuilder(CrownArmour plugin, Player player, String title, int rows) {
        this.plugin = plugin;
        this.title = title;
        this.rows = rows;
        this.player = player;
        this.inventory = Bukkit.createInventory(this, 9 * rows, ChatColor.translateAlternateColorCodes('&', title));
    }

    public MenuBuilder(CrownArmour plugin, Player player, String title, int rows, InventoryHolder inventoryHolder) {
        this.plugin = plugin;
        this.title = title;
        this.rows = rows;
        this.player = player;
        this.inventory = Bukkit.createInventory(inventoryHolder, 9 * rows, ChatColor.translateAlternateColorCodes('&', title));
    }

    public String getTitle() {
        return Color.colorUnColor(this.title);
    }
    public abstract void onInventoryClick(final InventoryClickEvent p0);

    public abstract T init();

}