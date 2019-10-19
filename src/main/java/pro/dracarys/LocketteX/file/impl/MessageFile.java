package pro.dracarys.LocketteX.file.impl;

import net.md_5.bungee.api.ChatColor;
import pro.dracarys.LocketteX.LocketteX;
import pro.dracarys.LocketteX.file.CustomFile;
import pro.dracarys.LocketteX.utils.Message;
import pro.dracarys.LocketteX.utils.Util;

public class MessageFile extends CustomFile {

    private LocketteX instance;

    public MessageFile(LocketteX instance) {
        super(instance, "");
        this.instance = instance;
        for (Message message : Message.values()) {
            if (message.getMessages() != null) {
                for (String string : message.getMessages()) {
                    ChatColor.translateAlternateColorCodes('&', string);
                    getConfig().addDefault(message.getConfig(), message.getMessages());
                }
            } else {
                getConfig().addDefault(message.getConfig(), message.getMessage());
            }
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public MessageFile init() {
        this.reloadConfig();
        for (Message message : Message.values()) {
            if (message.getMessages() == null) {
                message.setMessage(getConfig().getString(Util.color(message.getConfig())));
            } else {
                message.setMessages(getConfig().getStringList(message.getConfig()));
            }
        }
        return this;
    }

    @Override
    public String getName() {
        return "messages";
    }
}