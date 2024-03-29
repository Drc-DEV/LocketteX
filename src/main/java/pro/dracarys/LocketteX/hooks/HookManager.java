package pro.dracarys.LocketteX.hooks;

import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.hooks.claim.GriefPreventionHook;
import pro.dracarys.LocketteX.hooks.claim.WorldGuardHook;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HookManager {

    private Map<String, PluginHook<?>> hookedPlugins = new HashMap<>();
    private JavaPlugin plugin;

    public HookManager(JavaPlugin plugin) {
        this.plugin = plugin;
        hookPlugin(new VaultHook());
        hookPlugin(new SlimefunHook());
        hookPlugin(new GriefPreventionHook());
        hookPlugin(new WorldGuardHook());
    }

    private void hookPlugin(PluginHook<?> pluginHook) {
        if (!hookedPlugins.containsKey(pluginHook.getName())
                && plugin.getServer().getPluginManager().isPluginEnabled(pluginHook.getName())) {
            hookedPlugins.put(pluginHook.getName(), (PluginHook<?>) pluginHook.setup(plugin));
        }
    }

    public Map<String, PluginHook<?>> getHookedPluginsMap() {
        return hookedPlugins;
    }

    public Set<String> getHookedPlugins() {
        return hookedPlugins.keySet();
    }

}
