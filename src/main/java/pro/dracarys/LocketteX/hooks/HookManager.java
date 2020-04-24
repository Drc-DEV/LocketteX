package pro.dracarys.LocketteX.hooks;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HookManager {

    private Map<String, PluginHook<?>> hookedPlugins;
    private JavaPlugin plugin;

    public HookManager(JavaPlugin plugin) {
        hookedPlugins = new HashMap<>();
        hookPlugin(new VaultHook());
        this.plugin = plugin;
    }

    private void hookPlugin(PluginHook<?> pluginHook) {
        if (!hookedPlugins.containsKey(pluginHook.getName()) && plugin.getServer().getPluginManager().isPluginEnabled(pluginHook.getName())) {
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
