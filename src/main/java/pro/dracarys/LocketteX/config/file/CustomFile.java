package pro.dracarys.LocketteX.config.file;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public abstract class CustomFile implements ICustomFile {

    private YamlConfiguration config;
    private File file;
    private File configFile;

    public CustomFile(JavaPlugin instance, String parent) {
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }
        if (parent != null) {
            file = new File(instance.getDataFolder(), "/" + parent);
            if (!file.exists()) {
                file.mkdir();
            }
            configFile = new File(file, getName() + ".yml");
        } else {
            configFile = new File(getName() + ".yml");
        }
        try {
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public YamlConfiguration getConfig() {
        return config;
    }


}
