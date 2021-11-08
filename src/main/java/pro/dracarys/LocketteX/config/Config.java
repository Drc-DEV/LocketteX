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
    USE_INV_MOVE("GeneralSettings.protect-from-hoppers.enabled", false),
    DESC_HOPPER_PROTECTION("GeneralSettings.protect-from-hoppers.desc", new String[]{
            "Enables a basic protection from hoppers stealing items from protected containers," + "\n"
                    + "this can have some impact on performance and only works if inventory move event caused by hoppers is enabled in the spigot configuration."
    }),
    USE_CANCEL_EXPLOSIONS("GeneralSettings.protect-from-explosions", true),

    EXPIRE_ENABLED("Expiration.enabled", false),
    EXPIRE_TIME("Expiration.disable-protection-after-x-days", 90),

    PERMISSION_FOR_ALL("Permissions.ignore-creation-permission", false),
    PERMISSION_ADMIN("Permissions.permission-admin", "lockettex.admin"),
    PERMISSION_CREATION("Permissions.permission-creation", "lockettex.create"),

    DISABLE_CLAIM_HOOKS("Hooks.disable-hooking-to-claim-plugins", false),
    LEADER_CAN_BREAK("Hooks.leader-bypasses-break-protection", false),
    LEADER_CAN_OPEN("Hooks.leader-bypasses-open-protection", false),
    PROTECT_CLAIMED_ONLY("Hooks.disable-protection-on-unclaimed-land", false),
    SIGN_ID_LINE("SignSettings.sign-id-line", "[Protect]"),
    SIGN_OWNER_LINE("SignSettings.sign-owner-line-number", 3),
    SIGN_FORMATTED_LINES("SignSettings.sign-formatted-lines", new String[]{
            "&1[Protect]",
            "",
            "%owner%#%uuid%",
            ""
    });

    String config, message;
    Boolean option;
    String[] messages;
    Integer number;
    Double dnumber;

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

    Config(String config, Double dnumber) {
        this.config = config;
        this.dnumber = dnumber;
    }

    public boolean getOption() {
        return option;
    }

    public String getConfig() {
        return config;
    }

    public String getString() {
        return message;
    }

    public Double getDouble() {
        return dnumber;
    }

    public Integer getInt() {
        return number;
    }

    public String[] getStrings() {
        return this.messages;
    }

    public List<String> getStringList() {
        return Arrays.asList(this.messages);
    }

    public void setInt(int number) {
        this.number = number;
    }

    public void setDouble(double dnumber) {
        this.dnumber = dnumber;
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
}
