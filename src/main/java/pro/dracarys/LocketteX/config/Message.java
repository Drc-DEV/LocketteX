package pro.dracarys.LocketteX.config;

import pro.dracarys.LocketteX.utils.Util;

import java.util.List;

public enum Message {

    GENERAL_NOPERMISSION("no-permission", "&7[&4✕&7] &cYou don't have the required permission!"),
    CREATION_NOPERMISSION("no-permission-protection", "&7[&4✕&7] &cYou don't have the permission to protect Blocks!"),
    NONE("none", "N/A"),
    PREFIX("prefix", "&a&lProtect &f➤ "),
    PREFIX_DEBUG("prefix-debug", "&7[Protect] &e<DEBUG> "),
    PREFIX_ERROR("prefix-error", "&7[Protect] &c<ERROR> "),
    CMD_MAIN_HEADER("cmd-main-header", "&f«&m---------------&f»  &6&lProtect&f  «&m---------------&f»"),
    CMD_MAIN_FOOTER("cmd-main-footer", " "),
    CMD_RELOAD_DESC("cmd-reload-desc", "&7Reloads Config and Messages"),
    CMD_RELOAD_SUCCESS("cmd-reload-success", "&7[&a✔&7] &aConfig Reloaded!"),
    CMD_WHITELIST_DESC("cmd-whitelist-desc", "&7Add or revoke with a protected block to a player. &c[!] &eYou have to execute this command while looking at the protection sign attached to the block. &7You can list multiple players by separating their names with a comma, like this: player1,player2 etc."),
    CMD_ADD_SUCCESS("cmd-whitelist-add-success", "&7[&a✔&7] &aSuccessfully given access to this protected block to %player%!"),
    CMD_REMOVE_SUCCESS("cmd-whitelist-remove-success", "&7[&a✔&7] &aSuccessfully revoked access to this protected block to %player%!"),
    CMD_ADD_FAIL("cmd-whitelist-add-fail", "&7[&4✕&7] &cAll of the players specified already belong to this block's whitelist!"),
    CMD_REMOVE_FAIL("cmd-whitelist-remove-fail", "&7[&4✕&7] &cNone of the players specified are in this block's whitelist!"),
    CMD_WHITELIST_NOSIGN("cmd-whitelist-nosign", "&7[&4✕&7] &cYou must be directly looking at a protection sign (created by you) while performing this command."),
    CMD_USAGE("cmd-usage", "&7[&4✕&7] &cSomething's wrong! Do /protect for a command list."),

    NOT_ENOUGH_MONEY("not-enough-money", "&7[&4✕&7] &cYou must have &e%price%&c in order to protect a block."),

    CANT_PROTECT_ON_UNCLAIMED("cant-protect-in-unclaimed-land", "&7[&4✕&7] &cYou can't protect blocks in unclaimed land!"),
    CANT_PROTECT_THIS("cant-protect-this", "&7[&4✕&7] &cYou can't protect &e<item>&c!"),
    CANT_PROTECT_CANTBUILD("cant-protect-no-build-permissions", "&7[&4✕&7] &cYou can't protect where you do not have build permissions!"),
    ALREADY_PROTECTED("block-already-protected", "&7[&4✕&7] &cThis Block was already protected by &e%owner% &che must break the sign first!"),
    BREAK_DENIED("protected-break-denied", "&7[&4✕&7] &cThis &e<item>&c is protected! Only &e%owner% &ccan break it!"),
    INTERACT_DENIED("protected-interact-denied", "&7[&4✕&7] &cThis &e<item>&c is protected! Only &e%owner% &ccan decide who can interact with it!"),
    SIGN_BREAK_DENIED("sign-break-denied", "&7[&4✕&7] &cOnly &e%owner% &ccan break this sign!"),
    CONTAINER_OPEN_DENIED("container-open-denied", "&7[&4✕&7] &cThis &e<item>&c is protected! Only &e%owner% &ccan decide who can open it!"),
    HOPPER_PLACE_DENIED("hopper-place-denied", "&7[&4✕&7] &cYou can't place Hoppers near a protected Block!"),

    PROTECT_SUCCESS_ECON("block-protect-success-econ", "&7[&a✔&7] &aBlock successfully protected! You payed &e%price%&a!"),
    PROTECT_SUCCESS("block-protect-success", "&7[&a✔&7] &aBlock successfully protected!"),

    ERROR_IO_LOAD("Error.could-not-load-data", "&7[&4✕&7] &cCould not load block ownership data!"),
    ERROR_IO_SAVE("Error.could-not-save-data", "&7[&4✕&7] &cCould not save block ownership data!"),

    GP_HOOK_CANT_PROTECT("Hooks.griefprevention.cant-protect", "&7[&4✕&7] &cYou can't protect here, as you do not have permissions to build here!"),

    CLAIM_HOOK_FOUND("Hooks.claim-plugin-hooked", "&7[&a✔&7] &aUsing %plugin% as Claim Provider."),
    CLAIM_HOOK_NOTFOUND("Hooks.claim-plugin-not-found", "&7[&4✘&7] &cNo valid Claim Provider found!"),

    ERROR_HOOK_FAILED("Hooks.generic-hook-failed", "&7[&4✕&7] &cCould not hook to %plugin%. %plugin% support is disabled"),
    ERROR_ECON_INVALID("Hooks.economy-hook-failed", "&7[&4✕&7] &cEconomy support has been disabled! Error while hooking to Vault, or no Economy Service was found!");

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
