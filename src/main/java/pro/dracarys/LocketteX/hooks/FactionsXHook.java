package pro.dracarys.LocketteX.hooks;

import net.prosavage.factionsx.core.Faction;
import net.prosavage.factionsx.manager.GridManager;
import org.bukkit.Location;

public class FactionsXHook {

    public static String getLeaderOfFactionAt(Location location) {
        Faction f = GridManager.INSTANCE.getFactionAt(location.getChunk());
        if (f.isWilderness()) return "";
        return f.getLeader().getName();
    }

    public static String getFactionTagAt(Location location) {
        Faction f = GridManager.INSTANCE.getFactionAt(location.getChunk());
        if (f.isWilderness()) return "";
        return f.getTag();
    }

    public static boolean isClaimed(Location location) {
        Faction f = GridManager.INSTANCE.getFactionAt(location.getChunk());
        return !f.isWilderness();
    }

    public static boolean isSetup() {
        return HookManager.getInstance().getEnabledHooks().contains("FactionsX");
    }
}
