package pro.dracarys.LocketteX.hooks;

import com.licel.stringer.annotations.secured;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.Util;

import java.util.HashSet;
import java.util.Set;

@secured
public class HookManager {

    private static HookManager hookManager;
    private LocketteX plugin;

    private Set<String> enabledHooks;

    private HookManager(LocketteX plugin) {
        enabledHooks = new HashSet<>();
        this.plugin = plugin;
    }

    public static HookManager getInstance() {
        if (hookManager == null) {
            hookManager = new HookManager(LocketteX.getInstance());
        }
        return hookManager;
    }

    public void loadHooks() {
        Bukkit.getScheduler().runTaskLater((JavaPlugin) plugin, () -> {
            // Load hooks with a delay, because plugins sometimes load before us even if
            // they are in the softdepend list, don't know why. This way we're 100% sure.

            // Economy Hook (Vault), try to hook even if the Econ is disabled in config
            // so that if the user enables it after the plugin is loaded, it will work
            // without restarting the server.
            if (checkHook("Vault") && !Econ.setup(plugin)) {
                enabledHooks.remove("Vault");
                if (Config.USE_ECONOMY.getBoolean()) {
                    Util.error(Message.ERROR_ECON_INVALID.getMessage());
                } else {
                    Util.debug(Message.ERROR_ECON_INVALID.getMessage());
                }
            }

            checkHook("Towny");
            checkHook("Factions");

            if (!enabledHooks.isEmpty())
                Util.sendConsole(Message.PREFIX.getMessage() + "&e" + Bukkit.getName() + " Hooked to: &f" + enabledHooks.toString().replaceAll("\\[\\]", ""));

        }, 2);
    }

    private boolean checkHook(String pluginName) {
        if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            enabledHooks.add(pluginName);
            return true;
        } else {
            Util.debug(Message.ERROR_HOOK_FAILED.getMessage().replace("%plugin%", pluginName));
        }
        return false;
    }

    public Set<String> getEnabledHooks() {
        return enabledHooks;
    }

}
