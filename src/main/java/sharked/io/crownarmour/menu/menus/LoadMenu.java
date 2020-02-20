package sharked.io.crownarmour.menu.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sharked.io.crownarmour.CrownArmour;
import sharked.io.crownarmour.data.InventoryDataSlot;
import sharked.io.crownarmour.menu.MenuBuilder;
import sharked.io.crownarmour.user.UserData;
import sharked.io.lib.util.SharkUtil;

import java.util.ArrayList;
import java.util.Map;

public class LoadMenu extends MenuBuilder
{
    public LoadMenu(CrownArmour crownArmour, Player player) {
        super(crownArmour, player, crownArmour.getConfigFile().getString("settings.load.title"), crownArmour.getConfigFile().getConfig().getInt("settings.load.rows"));
    }

    @Override
    public LoadMenu init() {
        for (Map.Entry<String, InventoryDataSlot> entry : getPlugin().getLoad().entrySet()) {
            this.getInventory().setItem(entry.getValue().getSlot(), this.getItem(entry.getValue().getItemStack()));
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
        if (this.getInventory().getItem(slot) != new ItemStack(Material.valueOf(getPlugin().getConfigFile().getString("settings.background.mat")))) {
            int n = 0;
            for (Map.Entry<String, InventoryDataSlot> entry : getPlugin().getLoad().entrySet()) {
                if (entry.getValue().getMaterial() == type) {
                    getPlayer().closeInventory();
                    UserData user = getPlugin().getUserManager().getUser().get(getPlayer().getUniqueId());

                    switch (entry.getValue().getId()) {
                        case "prince":
                            PrinceMenu princeMenu = new PrinceMenu(getPlugin(), getPlayer());
                            user.setMenuBuilder(princeMenu);
                            getPlayer().openInventory(princeMenu.init().getInventory());
                            break;
                        case "bishop":
                            BishopMenu bishopMenu = new BishopMenu(getPlugin(), getPlayer());
                            user.setMenuBuilder(bishopMenu);
                            getPlayer().openInventory(bishopMenu.init().getInventory());
                            break;
                        case "king":
                            KingMenu kingMenu = new KingMenu(getPlugin(), getPlayer());
                            user.setMenuBuilder(kingMenu);
                            getPlayer().openInventory(kingMenu.init().getInventory());
                            break;
                    }
                }
            }
        }
    }

    public ItemStack getItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        if (itemMeta != null && itemMeta.hasLore()) {
            for (String s : itemMeta.getLore()) {
                lore.add(s);
            }
        }
        itemMeta.setLore(sharked.io.lib.util.Color.colorList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
