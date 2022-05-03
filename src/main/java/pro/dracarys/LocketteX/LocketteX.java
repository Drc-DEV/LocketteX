package pro.dracarys.LocketteX;

import me.pikamug.localelib.LocaleManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.commands.MainCommand;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.file.ConfigFile;
import pro.dracarys.LocketteX.config.file.MessageFile;
import pro.dracarys.LocketteX.hooks.HookManager;
import pro.dracarys.LocketteX.hooks.claim.ClaimPlugin;
import pro.dracarys.LocketteX.listener.*;
import pro.dracarys.configlib.ConfigLib;

import java.util.logging.Level;

public class LocketteX extends JavaPlugin {

    public static LocketteX plugin;

    public static LocketteX getInstance() {
        return plugin;
    }

    private ClaimPlugin claimPlugin;

    public ClaimPlugin getClaimPlugin() {
        return claimPlugin;
    }

    private HookManager hookManager;

    public HookManager getHookManager() {
        return hookManager;
    }

    private LocaleManager localeManager;

    public LocaleManager getLocaleManager() {
        return localeManager;
    }

    @Override
    public void onEnable() {
        try {
            int pluginId = 7307;
            new Metrics(this, pluginId);
        } catch (Exception ex) {
            getServer().getLogger().log(Level.SEVERE, "Error while trying to register Metrics (bStats)");
        }
        plugin = this;
        localeManager = new LocaleManager();
        initConfig();
        loadConfig();
        checkServerVersion();
        PluginCommand cmd = this.getCommand("lockettex");
        MainCommand executor = new MainCommand();
        if (cmd != null) {
            cmd.setExecutor(executor);
            cmd.setTabCompleter(executor);
        }
        ConfigLib.printPluginInfo();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            hookManager = new HookManager(this);
            claimPlugin = new ClaimPlugin().setup(this);
            registerListeners(new InventoryOpen(), new BlockBreak(), new BlockPlace(), new SignChange(), new Explosions());
            if (Config.USE_INV_MOVE.getOption()) registerListeners(new InventoryMoveItem());
        }, 1);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        plugin = null;
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    private void initConfig() {
        ConfigLib.setPlugin(this);
        ConfigLib.addFile(new ConfigFile());
        ConfigLib.addFile(new MessageFile());
    }

    public void loadConfig() {
        ConfigLib.initAll();
    }

    private static int ver;

    private void checkServerVersion() {
        ver = Integer.parseInt(Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].replace("1_", "").substring(1).replaceAll("_R\\d", ""));
    }

    public static int getServerVersion() {
        return ver;
    }
}
