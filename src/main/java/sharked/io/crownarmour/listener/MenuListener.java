package sharked.io.crownarmour.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sharked.io.crownarmour.CrownArmour;
import sharked.io.crownarmour.menu.MenuBuilder;
import sharked.io.crownarmour.user.UserData;

public class MenuListener
implements Listener
{
    private CrownArmour armour;

    public MenuListener(CrownArmour armour) {
        this.armour =armour;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getInventory().getHolder() instanceof MenuBuilder) {
            armour.getUserManager().getUser().get((inventoryClickEvent.getWhoClicked()).getUniqueId()).getMenuBuilder().onInventoryClick(inventoryClickEvent);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        armour.getUserManager().getUser().put(event.getPlayer().getUniqueId(), new UserData(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        armour.getUserManager().getUser().remove(event.getPlayer().getUniqueId());
    }
}
