package pro.dracarys.LocketteX.hooks.claim;

import me.angeschossen.lands.api.integration.LandsIntegration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import pro.dracarys.LocketteX.LocketteX;

public class LandsHook extends ClaimPlugin {

    private void init() {
        landsAddon = new LandsIntegration(LocketteX.getInstance(), false);
    }

    private LandsIntegration landsAddon;

    @Override
    public String getLeaderOfClaimAt(Location location) {
        try {
            if (landsAddon == null) init();
            return Bukkit.getOfflinePlayer(landsAddon.getLand(location).getOwnerUID()).getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    @Override
    public String getClaimTagAt(Location location) {
        try {
            if (landsAddon == null) init();
            return landsAddon.getLand(location).getName();
        } catch (NullPointerException npe) {
            return "";
        }
    }

    @Override
    public boolean isClaimed(Location location) {
        return !getClaimTagAt(location).equalsIgnoreCase("");
    }

}
