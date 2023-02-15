package pro.dracarys.LocketteX.hooks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.hooks.claim.ClaimPlugin;

public class ProtectionStonesHook extends ClaimPlugin {

    @Override
    public ProtectionStonesHook setup(JavaPlugin plugin) {
        return this;
    }

    public boolean isInOwnedProtection(Player player, Location location) {
        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform()
                .getRegionContainer().createQuery().getApplicableRegions(BukkitAdapter.adapt(location));

        for (ProtectedRegion region : regions) {
            if (region.getMembers().contains(player.getUniqueId())
                || region.getOwners().contains(player.getUniqueId())) return true;
        }

        return false;
    }

    @Override
    public String getName() {
        return "ProtectionStones";
    }

}
