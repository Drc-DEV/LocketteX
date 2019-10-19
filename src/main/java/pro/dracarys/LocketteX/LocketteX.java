package pro.dracarys.LocketteX;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.file.MessageManager;
import pro.dracarys.LocketteX.listener.*;
import pro.dracarys.LocketteX.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class LocketteX extends JavaPlugin {

    public static LocketteX plugin;

    private MessageManager messageManager;

    public static LocketteX getInstance() {
        return plugin;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    private static List<String> enabledHooks = new ArrayList<>();

    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        registerListeners(new InventoryOpen(), new BlockBreak(), new BlockPlace(), new SignChange());
        printPluginInfo();
        initConfig();
    }

    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        plugin = null;
        Util.sendConsole("&c[" + getName() + "] &7v" + getDescription().getVersion() + " &cDisabled - Plugin by Dracarys");
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
