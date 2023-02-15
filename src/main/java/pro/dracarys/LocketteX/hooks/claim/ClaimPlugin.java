package pro.dracarys.LocketteX.hooks.claim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.hooks.PluginHook;
import pro.dracarys.LocketteX.hooks.ProtectionStonesHook;
import pro.dracarys.LocketteX.utils.Util;

import java.util.List;

public class ClaimPlugin implements PluginHook<ClaimPlugin> {

    public static String hookedPlugin;

    @Override
    public ClaimPlugin setup(JavaPlugin plugin) {
        if (Bukkit.getPluginManager().isPluginEnabled("FactionsX")) {
            hookedPlugin = "FactionsX";
            Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", hookedPlugin));
            return new FactionsXHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            List<String> authors = plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getAuthors();
            if (authors.contains("drtshock")
                    || authors.contains("Benzimmer")
                    || authors.contains("ProSavage")
                    || authors.contains("LockedThread")
                    || authors.contains("ipodtouch0218")) {
                hookedPlugin = "Factions";
                Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", "FactionsUUID"));
                return new FactionsUUIDHook();
            } else {
                hookedPlugin = "Factions";
                Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", "MassiveCore Factions (Unsupported)"));
                return new MCoreHook();
            }
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Towny")) {
            hookedPlugin = "Towny";
            Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", hookedPlugin));
            return new TownyHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Feudal")) {
            hookedPlugin = "Feudal";
            Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", hookedPlugin));
            return new FeudalHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Lands")) {
            hookedPlugin = "Lands";
            Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", hookedPlugin));
            return new LandsHook();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("ProtectionStones")) {
            hookedPlugin = "ProtectionStones";
            Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_FOUND.getMessage().replace("%plugin%", hookedPlugin));
            return new ProtectionStonesHook();
        }
        Util.sendConsole(Message.PREFIX.getMessage() + Message.CLAIM_HOOK_NOTFOUND.getMessage());
        hookedPlugin = "none";
        return this;
    }

    @Override
    public String getName() {
        return hookedPlugin;
    }

    public String getLeaderOfClaimAt(Location location) {
        throw new NullPointerException(Message.CLAIM_HOOK_NOTFOUND.getMessage());
    }

    public boolean isClaimed(Location location) {
        throw new NullPointerException(Message.CLAIM_HOOK_NOTFOUND.getMessage());
    }

    public String getClaimTagAt(Location location) {
        throw new NullPointerException(Message.CLAIM_HOOK_NOTFOUND.getMessage());
    }

}
