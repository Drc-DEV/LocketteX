package pro.dracarys.LocketteX.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.config.Config;

public class Econ {

    private static Economy econ = null;

    public static boolean setup(LocketteX plugin) {
        // Just to be safe, return true if it's already setup.
        if (econ != null) return true;
        // Just to be safe, return false if the plugin is not enabled.
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        // No economy provider found, like Essentials, CMI, TNE...
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        // this should never be null, but keep the check for safety.
        return econ != null;
    }

    public static boolean isEnabled() {
        return Config.USE_ECONOMY.getOption() && econ != null && econ.isEnabled();
    }

    public static boolean isSetup() {
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
