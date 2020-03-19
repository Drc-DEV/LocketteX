package pro.dracarys.LocketteX.config.file;

import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.config.file.types.ConfigFile;
import pro.dracarys.LocketteX.config.file.types.MessageFile;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static ConfigManager configManager;
    private JavaPlugin plugin;

    private static Map<String, CustomFile> fileMap = new HashMap<>();

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        addFile(new MessageFile(plugin));
        addFile(new ConfigFile(plugin));
    }

    public static ConfigManager getInstance() {
        if (configManager == null) {
            configManager = new ConfigManager(LocketteX.getInstance());
        }
        return configManager;
    }

    private void addFile(CustomFile file) {
        fileMap.put(file.getName(), file);
        file.init();
    }

    public static Map<String, CustomFile> getFileMap() {
        return fileMap;
    }

}