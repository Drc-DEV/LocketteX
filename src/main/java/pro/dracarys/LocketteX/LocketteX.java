package pro.dracarys.LocketteX;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.commands.MainCommand;
import pro.dracarys.LocketteX.file.MessageManager;
import pro.dracarys.LocketteX.listener.BlockBreak;
import pro.dracarys.LocketteX.listener.BlockPlace;
import pro.dracarys.LocketteX.listener.InventoryOpen;
import pro.dracarys.LocketteX.listener.SignChange;
import pro.dracarys.LocketteX.utils.Config;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class LocketteX extends JavaPlugin {

    public static LocketteX plugin;

    public static boolean UseEconomy = false;
    public static Economy econ = null;

    private MessageManager messageManager;

    public static LocketteX getInstance() {
        return plugin;
    }

    public List<String> getEnabledHooks() {
        return enabledHooks;
    }

    public boolean isUsingEconomy() {
        return UseEconomy;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    private static List<String> enabledHooks = new ArrayList<>();

    public void onEnable() {
        plugin = this;
        registerListeners(new InventoryOpen(), new BlockBreak(), new BlockPlace(), new SignChange());
        initConfig();
        loadConfig();
        if (Config.USE_ECONOMY.getOption()) {
            if (!setupEconomy()) {
                Util.sendConsole("&c[" + getName() + "] &7v" + getDescription().getVersion()
                        + " &aEconomy support Disabled - No Vault dependency found!");
                UseEconomy = false;
            } else {
                UseEconomy = true;
                enabledHooks.add("Vault");
            }
        } else {
            Util.sendConsole("&c[" + getName() + "] &7v" + getDescription().getVersion()
                    + "&cEconomy disabled - Set 'use-economy: true' in config.yml and restart the server in order to enable");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Towny")) {
            enabledHooks.add("Towny");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            enabledHooks.add("Factions");
        }
        PluginCommand cmd = this.getCommand("lockettex");
        MainCommand executor = new MainCommand();
        cmd.setExecutor(executor);
        cmd.setTabCompleter(executor);
        printPluginInfo();
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        plugin = null;
        enabledHooks.clear();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    private void printPluginInfo() {
        Util.sendConsole("&6================================================");
        Util.sendConsole("  &a" + getDescription().getName() + " Enabled");
        Util.sendConsole("  &f- &eVersion: &7" + getDescription().getVersion());
        Util.sendConsole("  &f- &eAuthor(s): &7" + getDescription().getAuthors());
        Util.sendConsole("  &f- &eDesc: &7" + getDescription().getDescription());
        if (enabledHooks.size() > 0)
            Util.sendConsole("  &f- &eEnabled Hooks: &f" + enabledHooks.toString().replaceAll("\\[\\]", ""));
        Util.sendConsole("&6================================================");
    }

    private void initConfig() {
        this.messageManager = new MessageManager(this);
    }

    public void loadConfig() {
        messageManager.getFileMap().get("config").init();
        messageManager.getFileMap().get("messages").init();
    }
}
