package sharked.io.crownarmour.user;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import sharked.io.crownarmour.menu.MenuBuilder;

public class UserData {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private MenuBuilder menuBuilder;
    @Getter
    @Setter
    private Player player;

    public UserData(Player player) {
        setPlayer(player);
    }

    public void update(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }
}
