package pro.dracarys.LocketteX.utils;

import java.util.List;

public enum Config {

    PERMISSION_ADMIN("permission-admin", "lockettex.admin"),
    PERMISSION_CREATION("permission-creation", "lockettex.create"),

    PRICE_CREATION("price-creation",500),

    LEADER_CAN_BREAK("leader-bypasses-break-protection",false),
    LEADER_CAN_OPEN("leader-bypasses-open-protection",false),
    SIGN_ID_LINE("sign-id-line", "[Protect]"),
    SIGN_FORMATTED_LINES("sign-formatted-lines",  new String[]{
            "&1[Protect]",
            "",
            "%owner%",
            ""
    }),
    ENABLED_WORLDS("enabled-worlds",  new String[]{
            "world1",
            "world2"
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

    public void setInt(int number) { this.number = number; }

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
