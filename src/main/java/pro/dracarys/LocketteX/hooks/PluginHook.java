package pro.dracarys.LocketteX.hooks;

import org.bukkit.plugin.java.JavaPlugin;

public interface PluginHook<T> {

    T setup(JavaPlugin plugin);

    String getName();

}
