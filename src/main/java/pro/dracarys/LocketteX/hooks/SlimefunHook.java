package pro.dracarys.LocketteX.hooks;

import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pro.dracarys.LocketteX.api.LocketteXAPI;

public class SlimefunHook implements PluginHook<SlimefunHook>, Listener {

    @Override
    public SlimefunHook setup(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        return this;
    }

    @EventHandler
    public void onExpBreak(ExplosiveToolBreakBlocksEvent e) {
        if (!LocketteXAPI.canBreak(e.getPlayer(), e.getPrimaryBlock())) {
            e.setCancelled(true);
            return;
        }
        for (Block b : e.getAdditionalBlocks()) {
            if (!LocketteXAPI.canBreak(e.getPlayer(), b)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @Override
    public String getName() {
        return "Slimefun";
    }
}
