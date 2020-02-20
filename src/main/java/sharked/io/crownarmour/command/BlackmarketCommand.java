package sharked.io.crownarmour.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sharked.io.crownarmour.CrownArmour;
import sharked.io.crownarmour.menu.menus.LoadMenu;
import sharked.io.crownarmour.user.UserData;
import sharked.io.lib.api.command.command.CommandMeta;

@CommandMeta(command = {"bm662232"} ,permission = "blackmarket.use")
public class BlackmarketCommand {

    private CrownArmour armour;

    public BlackmarketCommand(CrownArmour armour) {
        this.armour = armour;
    }

    public void execute(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You are not a player.");
            return;
        }
        Player player = (Player)sender;
        UserData user = armour.getUserManager().getUser().get(player.getUniqueId());
        LoadMenu loadMenu = new LoadMenu(armour, player);
        user.setMenuBuilder(loadMenu);
        player.openInventory(loadMenu.init().getInventory());
    }

}
