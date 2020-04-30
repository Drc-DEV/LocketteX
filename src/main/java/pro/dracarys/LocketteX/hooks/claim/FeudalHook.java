package pro.dracarys.LocketteX.hooks.claim;

import org.bukkit.Location;
import us.forseth11.feudal.core.Feudal;
import us.forseth11.feudal.kingdoms.Member;
import us.forseth11.feudal.kingdoms.Rank;

public class FeudalHook extends ClaimPlugin {

    @Override
    public String getLeaderOfClaimAt(Location location) {
        try {
            for (Member member : Feudal.getAPI().getKingdom(location).getMembersOrdered()) {
                if (member.getRank() == Rank.LEADER) return member.getPlayer().getName();
            }
            return "";
        } catch (Exception nre) {
            return "";
        }
    }

    @Override
    public String getClaimTagAt(Location location) {
        try {
            return Feudal.getAPI().getKingdom(location).getName();
        } catch (Exception nre) {
            return "";
        }
    }

    @Override
    public boolean isClaimed(Location location) {
        return !getClaimTagAt(location).equalsIgnoreCase("");
    }

}
