package sharked.io.crownarmour.menu.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sharked.io.crownarmour.CrownArmour;
import sharked.io.crownarmour.data.InventoryDataSlot;
import sharked.io.crownarmour.menu.MenuBuilder;
import sharked.io.crownarmour.user.UserData;
import sharked.io.lib.util.Color;
import sharked.io.lib.util.SharkUtil;

import java.util.ArrayList;
import java.util.Map;

public class KingMenu extends MenuBuilder {

    public KingMenu(CrownArmour crownArmour, Player player) {
        super(crownArmour, player, crownArmour.getConfigFile().getString("settings.king.title"), crownArmour.getConfigFile().getConfig().getInt("settings.king.rows"));
    }

    @Override
    public KingMenu init() {
        for (Map.Entry<String, InventoryDataSlot> entry : getPlugin().getBishop().entrySet()) {
            this.getInventory().setItem(entry.getValue().getSlot(), this.getItem(entry.getValue().getItemStack(), entry.getKey()));
        }
        for (int i = 0; i < this.getInventory().getSize(); ++i) {
            if (this.getInventory().getContents()[i] == null) {
                this.getInventory().setItem(i, SharkUtil.createItem(Material.valueOf(getPlugin().getConfigFile().getString("settings.background.mat")), 1, getPlugin().getConfigFile().getConfig().getInt("settings.background.data"), ""));
            }
        }
        this.getPlayer().openInventory(this.getInventory());
        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        int slot = inventoryClickEvent.getSlot();
        if (slot < 0 || slot > this.getInventory().getSize()) {
            return;
        }
        Material type = this.getInventory().getItem(slot).getType();
        if (this.getInventory().getItem(slot) != new ItemStack(Material.STAINED_GLASS_PANE)) {
            int n = 0;
            for (Map.Entry<String, InventoryDataSlot> entry : getPlugin().getBishop().entrySet()) {
                if (entry.getValue().getMaterial() == type) {
                    getPlayer().closeInventory();
                    UserData user = getPlugin().getUserManager().getUser().get(getPlayer().getUniqueId());

                    if (type == Material.valueOf(getPlugin().getConfigFile().getString("settings.back-mat"))) {
                        LoadMenu loadMenu = new LoadMenu(getPlugin(), getPlayer());
                        user.setMenuBuilder(loadMenu);
                        getPlayer().openInventory(loadMenu.init().getInventory());
                        return;
                    }

                    if (getPlugin().getEcon().getBalance(getPlayer()) >= Double.parseDouble(entry.getValue().getId())) {
                        getPlugin().getEcon().withdrawPlayer(getPlayer(), Double.parseDouble(entry.getValue().getId()));
                        getPlayer().sendMessage(Color.color(getPlugin().getConfigFile().getString("messages.purchased")));
                        for (String cmd : getPlugin().getConfigFile().getConfig().getStringList("bishop." + entry.getKey() + ".commands")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", getPlayer().getName()));
                        }
                    } else {
                        getPlayer().sendMessage(Color.color(getPlugin().getConfigFile().getString("messages.no-money")));
                    }

                    // UserData user = getPlugin().getUserManager().getUser(getPlayer());
                    //  TimeMenu timeMenu = new TimeMenu(getPlugin(), getPlayer());
                    //   user.setMenuBuilder(timeMenu);
                    //   getPlayer().openInventory(timeMenu.init().getInventory());
                }
            }
        }
    }

    public ItemStack getItem(ItemStack itemStack, String id) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        if (itemMeta != null && itemMeta.hasLore()) {
            for (String s : itemMeta.getLore()) {
                lore.add(s.replaceAll("%price%", "" + getPlugin().getConfigFile().getDouble("king." + id + ".price")));
            }
        }
        itemMeta.setLore(sharked.io.lib.util.Color.colorList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
