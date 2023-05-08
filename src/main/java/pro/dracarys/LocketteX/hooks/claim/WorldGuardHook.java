package pro.dracarys.LocketteX.hooks.claim;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.config.Config;

public class WorldGuardHook extends ClaimPlugin {

    @Override
    public WorldGuardHook setup(JavaPlugin plugin) {
        return this;
    }

    public boolean isInOwnedProtection(Player player, Location location) {
        if (!Config.USE_WORLDGUARD.getOption() && !Config.USE_PROTECTIONSTONES.getOption())
            return true;
        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform()
                .getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location));

        for (ProtectedRegion region : regions) {
            if (Config.USE_PROTECTIONSTONES.getOption() ||
                    ((Config.WG_MUSTBEMEMBER.getOption() && region.getMembers().contains(player.getUniqueId()))
                            || (Config.WG_MUSTBEOWNER.getOption() && region.getOwners().contains(player.getUniqueId()))))
                return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return "WorldGuard";
    }

}
