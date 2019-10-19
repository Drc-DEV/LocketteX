package pro.dracarys.LocketteX.file;

import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.file.impl.MessageFile;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class MessageManager {

    private LocketteX plugin;
    private static Map<String, CustomFile> fileMap = new HashMap<>();

    public MessageManager(LocketteX plugin) {
        this.plugin = plugin;
        addFile(new MessageFile(plugin));
    }

    private void addFile(CustomFile file) {
        fileMap.put(file.getName(), file);
        plugin.getLogger().log(Level.INFO, file.getName() + ".yml has initialized.");
        file.init();
    }

    public static Map<String, CustomFile> getFileMap() {
        return fileMap;
    }

}