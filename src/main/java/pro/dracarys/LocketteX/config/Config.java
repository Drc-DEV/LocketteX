package pro.dracarys.LocketteX.config;

import java.util.Arrays;
import java.util.List;

public enum Config {

    DEBUG("Debug", false),

    ENABLED_WORLDS("GeneralSettings.enabled-worlds", new String[]{
            "world",
            "world_nether",
            "world_the_end"
    }),
    ENABLED_WORLDS_ASBLACKLIST("GeneralSettings.make-enabled-worlds-a-blacklist", false),
    USE_ECONOMY("GeneralSettings.use-economy", true),
    PRICE_CREATION("GeneralSettings.price-creation", 500),
    SNEAKCLICK_TO_CREATE("GeneralSettings.shift-click-with-sign-to-protect", false),
    USE_INV_MOVE("GeneralSettings.use-inventory-move-event", false),
    USE_CANCEL_EXPLOSIONS("GeneralSettings.protect-from-explosions", false),

    PERMISSION_ADMIN("Permissions.permission-admin", "lockettex.admin"),
    PERMISSION_CREATION("Permissions.permission-creation", "lockettex.create"),

    LEADER_CAN_BREAK("Hooks.leader-bypasses-break-protection", false),
    LEADER_CAN_OPEN("Hooks.leader-bypasses-open-protection", false),
    PROTECT_CLAIMED_ONLY("Hooks.disable-protection-on-unclaimed-land", false),
    SIGN_ID_LINE("SignSettings.sign-id-line", "[Protect]"),
    SIGN_FORMATTED_LINES("SignSettings.sign-formatted-lines", new String[]{
            "&1[Protect]",
            "",
            "%owner%",
            ""
    });


    String config, message;
    Boolean option;
    String[] messages;
    Integer number;

    Config(String config, String message) {
        this.config = config;
        this.message = message;
    }

    Config(String config, String[] messages) {
        this.config = config;
        this.messages = messages;
    }

    Config(String config, Boolean option) {
        this.config = config;
        this.option = option;
    }

    Config(String config, Integer number) {
        this.config = config;
        this.number = number;
    }

    public boolean getOption() {
        return option;
    }

    public boolean getBoolean() {
        return option;
    }

    public String getConfig() {
        return config;
    }

    public String getString() {
        return message;
    }

    public Integer getInt() {
        return number;
    }

    public String[] getStrings() {
        return this.messages;
    }

    public void setInt(int number) {
        this.number = number;
    }

    public void setStrings(List<String> list) {
        this.messages = list.stream().toArray(String[]::new);
    }

    public void setString(String message) {
        this.message = message;
    }

    public void setOption(Boolean option) {
        this.option = option;
    }

    public List<String> getStringList() {
        return Arrays.asList(this.messages);
    }
}
