package pro.dracarys.LocketteX.hooks;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import org.bukkit.Location;

public class TownyHook {

    public static String getMayorOfTownAt(Location location){
        if (TownyUniverse.isWilderness(location.getBlock())) return "";
        try {
            String townAtLoc = (TownyUniverse.getTownName(location));
            if (townAtLoc != null) return TownyUniverse.getDataSource().getTown(townAtLoc).getMayor().getName();
        } catch (NullPointerException| NotRegisteredException nre) {
            return "";
        }
        return "";
    }

}
