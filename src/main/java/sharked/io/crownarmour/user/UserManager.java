package sharked.io.crownarmour.user;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    @Getter
    private HashMap<UUID, UserData> user;

    public UserManager() {
        user = new HashMap<>();
    }
}
