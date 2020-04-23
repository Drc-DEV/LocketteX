package pro.dracarys.LocketteX.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.Message;
import pro.dracarys.LocketteX.utils.Util;

import java.util.HashSet;
import java.util.Set;

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

            checkHook("FactionsX");
            checkHook("Factions");
            checkHook("Towny");
            if (checkHook("Lands")) {
                LandsHook.init();
            }

            if (!enabledHooks.isEmpty())
                Util.sendConsole(Message.PREFIX.getMessage() + "&e" + Bukkit.getName() + " Hooked to: &f" + enabledHooks.toString().replaceAll("\\[\\]", ""));

        }, 2);
    }

    private boolean checkHook(String pluginName) {
        if (Bukkit.getPluginManager().isPluginEnabled(pluginName)) {
            if (Bukkit.getPluginManager().getPlugin(pluginName).getDescription().getAuthors().contains("drtshock")
                    || Bukkit.getPluginManager().getPlugin(pluginName).getDescription().getAuthors().contains("Benzimmer")) {
                LocketteX.isMCoreFactions = false;
            } else {
                Util.sendConsole("&4| X X X X X | &cOld MassiveCraft-Factions detected! &4| X X X X X |");
                Util.sendConsole("&7- &fConsider switching to a more modern Factions solution, like &eFactionsX&7 -");
                Util.sendConsole("&7- &fGet FactionsX here &fhttps://patreon.com/ProSavage&7 -");
                Util.sendConsole("&4| X X X X X | &cOld MassiveCraft-Factions detected! &4| X X X X X |");
                LocketteX.isMCoreFactions = true;
            }
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
