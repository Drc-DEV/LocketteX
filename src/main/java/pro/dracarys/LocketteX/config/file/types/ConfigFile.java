package pro.dracarys.LocketteX.config.file.types;

import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.config.Config;
import pro.dracarys.LocketteX.config.file.CustomFile;

public class ConfigFile extends CustomFile {

    private JavaPlugin instance;

    public ConfigFile(JavaPlugin instance) {
        super(instance, "");
        this.instance = instance;
        for (Config message : Config.values()) {
            if (message.getStrings() != null) {
                for (String string : message.getStrings()) {
                    getConfig().addDefault(message.getConfig(), message.getStrings());
                }
            } else if (message.getString() != null) {
                getConfig().addDefault(message.getConfig(), message.getString());
            } else if (message.getInt() != null) {
                getConfig().addDefault(message.getConfig(), message.getInt());
            } else if (message.getDouble() != null) {
                getConfig().addDefault(message.getConfig(), message.getDouble());
            } else {
                getConfig().addDefault(message.getConfig(), message.getOption());
            }
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public ConfigFile init() {
        this.reloadConfig();
        for (Config message : Config.values()) {
            if (message.getStrings() != null) {
                message.setStrings(getConfig().getStringList(message.getConfig()));
            } else if (message.getInt() != null) {
                message.setInt(getConfig().getInt(message.getConfig()));
            } else if (message.getDouble() != null) {
                message.setDouble(getConfig().getDouble(message.getConfig()));
            } else if (message.getString() != null) {
                message.setString(getConfig().getString(message.getConfig()));
            } else {
                message.setOption(getConfig().getBoolean(message.getConfig()));
            }
        }
        return this;
    }

    @Override
    public String getName() {
        return "config";
    }
}