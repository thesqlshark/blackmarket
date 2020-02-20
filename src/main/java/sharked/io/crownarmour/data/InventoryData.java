package sharked.io.crownarmour.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryData
{
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Material material;

    public InventoryData() {
        this.name = "";
    }

    public InventoryData(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    public InventoryData(Material material) {
        this.material = material;
        this.name = "";
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(this.material,1);
        ItemMeta itemMeta = item.getItemMeta();
        if (this.name != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
        }
        item.setItemMeta(itemMeta);
        return item;
    }
}
