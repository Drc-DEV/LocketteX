package pro.dracarys.LocketteX.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import pro.dracarys.LocketteX.utils.Util;

public class SignChange implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent e){
        if (!e.getPlayer().hasPermission("lockettex.create") && e.getLine(0).equalsIgnoreCase("[Protect]")) return;
        e.setLine(0, Util.color("&1[Protect]"));
        e.setLine(1,e.getPlayer().getName());
        e.getPlayer().sendMessage(Util.color("&7[&a✔&7] &aChest protetta con successo!"));
    }

}
