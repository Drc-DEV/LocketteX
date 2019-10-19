package pro.dracarys.LocketteX.utils;

import java.util.List;

public enum Message {

    GENERAL_NOPERMISSION("no-permission", "&cNon hai i permessi!"),
    CREATION_NOPERMISSION("no-permission-protection", "&cNon hai i permessi per proteggere le chest!"),
    NONE("none", "N/A"),
    PREFIX("prefix", "&a&lProtect &f➤"),
    CMD_MAIN_HEADER("cmd-main-header", "&f«&m---------------&f»  &6&lProtect&f  «&m---------------&f»"),
    CMD_MAIN_FOOTER("cmd-main-footer", " "),
    CMD_RELOAD_SUCCESS("cmd-reload-success", "&7[&a✔&7] &aConfig Ricaricata!"),
    CMD_USAGE("cmd-usage","&7[&4✘&7] &cStai sbagliando qualcosa! Fai /protect per la lista dei comandi."),

    NOT_ENOUGH_MONEY("not-enough-money","&7[&4✘&7] &cNon hai abbastanza soldi! Devi avere &e%price%&c per proteggere una chest."),

    CHEST_ALREADY_PROTECTED("chest-already-protected","&7[&4✘&7] &cQuesta chest è già stata protetta! &e%owner% &cdeve prima rompere il cartello per annullare la protezione!"),
    CHEST_BREAK_DENIED("chest-break-denied","&7[&4✘&7] &cQuesta chest è protetta! Solo &e%owner% &cpuò romperla!"),
    SIGN_BREAK_DENIED("sign-break-denied","&7[&4✘&7] &cSolo &e%owner% &cpuò rompere questo cartello!"),
    CHEST_OPEN_DENIED("chest-open-denied","&7[&4✘&7] &cQuesta chest è protetta! Solo &e%owner% &cpuò aprirla!"),
    HOPPER_PLACE_DENIED("hopper-place-denied", "&7[&4✘&7] &cNon puoi piazzare Hopper sotto una chest protetta!"),

    CHEST_PROTECT_SUCCESS_ECON("chest-protect-success-econ","&7[&a✔&7] &aChest protetta con successo! Hai pagato &e%price%$&a!"),
    CHEST_PROTECT_SUCCESS("chest-protect-success","&7[&a✔&7] &aChest protetta con successo!");

    String config, message;
    String[] messages;

    Message(String config, String message) {
        this.config = config;
        this.message = message;
    }

    Message(String config, String[] messages) {
        this.config = config;
        this.messages = messages;
    }

    public String getConfig() {
        return config;
    }

    public String getMessage() {
        return message;
    }

    public String[] getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> list) {
        this.messages = list.stream().toArray(String[]::new);
    }

    public void setMessage(String message) {
        this.message = Util.color(message);
    }

}
