package pro.dracarys.LocketteX.hooks;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.Util;

public class VaultHook implements PluginHook<VaultHook> {

    private static Economy econ = null;

    public static boolean isEnabled() {
        return Config.USE_ECONOMY.getOption() && econ != null && econ.isEnabled();
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public VaultHook setup(JavaPlugin plugin) {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            if (Config.USE_ECONOMY.getOption()) {
                Util.error(Message.ERROR_ECON_INVALID.getMessage());
            } else {
                Util.debug(Message.ERROR_ECON_INVALID.getMessage());
            }
            return this;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        // No economy provider found, like Essentials, CMI, TNE...
        if (rsp == null) {
            if (Config.USE_ECONOMY.getOption()) {
                Util.error(Message.ERROR_ECON_INVALID.getMessage());
            } else {
                Util.debug(Message.ERROR_ECON_INVALID.getMessage());
            }
            return this;
        }
        econ = rsp.getProvider();
        return this;
    }


    @Override
    public String getName() {
        return "Vault";
    }
}
