package sharked.io.crownarmour.data;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sharked.io.lib.util.Color;

import java.util.List;

public class InventoryDataSlot extends InventoryData
{
    private List<String> lore;
    @Getter
    private int slot;
    @Getter
    private String id;

    public InventoryDataSlot(int slot) {
        this.slot = slot;
    }

    public InventoryDataSlot(Material material, String s, List<String> lore, int slot, String id) {
        super(material, s);
        this.slot = slot;
        this.lore = lore;
        this.setMaterial(material);
        this.id = id;
    }

    public int getSlot() {
        return this.slot;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(this.getMaterial(), 1);
        ItemMeta itemMeta = item.getItemMeta();
        if (this.lore.size() != 0) {
            itemMeta.setLore(Color.colorList(this.lore));
        }
        if (this.getName() != null) {
            itemMeta.setDisplayName(Color.color(this.getName()));
        }
        item.setItemMeta(itemMeta);
        return item;
    }
}
