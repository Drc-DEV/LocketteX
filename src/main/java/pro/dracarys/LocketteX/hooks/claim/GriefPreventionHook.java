package pro.dracarys.LocketteX.hooks.claim;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.config.Config;

public class GriefPreventionHook extends ClaimPlugin {

    @Override
    public GriefPreventionHook setup(JavaPlugin plugin) {
        return this;
    }

    public boolean canBuildAt(Player player, Location location) {
        if (!Config.USE_GRIEFPREVENTION.getOption()) return true;
        return GriefPrevention.instance.allowBuild(player, location) != null;
    }

    public boolean canBreakAt(Player player, Block block, Location location) {
        if (!Config.USE_GRIEFPREVENTION.getOption()) return true;
        return GriefPrevention.instance.allowBreak(player, block, location) != null;
    }

    @Override
    public String getName() {
        return "GriefPrevention";
    }

}
