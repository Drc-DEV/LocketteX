[CENTER][IMG]https://i.imgur.com/H8CBDvs.png[/IMG][/CENTER]

[CENTER][IMG]https://i.imgur.com/hYqyjFn.png[/IMG][/CENTER]
[LIST]
[*]Some servers [B]don't need a complicated Chest Protection plugin[/B], this one provides the most commonly used features in a [B]lightweight[/B] way, without any database or heavy checks.
[*]Just [B]place a chest[/B]/double chest and [B]put a sign on one of its sides[/B], with [B][Protect] on the 1st line[/B] (configurable), it will instantly protect the chest, making the text on the sign colorful and pretty to see, with your name in one of its lines. After that only you can access the chest, in any kind of environment.
[*]This was designed with [B]Towny [/B]and [B]Factions [/B]environments in mind, where there already is a protection system on chests in a per-plot or per-rank fashion, so in these use-cases only a simple chest protected for private use was more than enough.
[/LIST]
[CENTER][IMG]https://i.imgur.com/zF2mDqu.png[/IMG][/CENTER]
[LIST]
[*][B]Any Container can be protected[/B]

[*][B]Any Message is translatable[/B]
[*][B]Per-World enabled[/B]
[*][B]Configurable Permissions[/B]
[*][B]Configurable sign tag and text[/B]
[*][B]Configurable sign owner line[/B], to make it compatible with previously installed sign protection plugins, do not change the owner line number after some signs are created, or the old ones won't work anymore for obvious reasons.
[*][B][COLOR=rgb(255, 0, 0)][NEW] [/B][/COLOR] [B]Commands to give / revoke access to a protected block[/B]
[*][B][COLOR=rgb(255, 0, 0)][NEW] [/B][/COLOR] [B]Option to restrict access to whitelisted players only while the owner is online[/B]
[*][B][COLOR=rgb(255, 0, 0)][NEW] [/B][/COLOR] [B]Option to restrict protection to blocks where the player has build permissions[/B]
[*][B](optional) Explosions and Hopper protection[/B]
[*][B](opt) UUID Support [/B](enabled by default, will save the UUID of the player alongside its name when creating the protection sign. This way the [B]protection will work even if the player changes its name[/B]. (if you are an offline-mode server or you do not need this feature, you can just set the owner line format to just "%owner%" and the plugin will just use the player name on the sign)
[*][B](opt) Make Protection Expire after x Days[/B]
[*][B](opt) Shift-Click to Protect chests[/B], gotta go fast.
[*][B](opt) Vault compatibility,[/B] adds economy as in a configurable price for the creation of a protect sign.
[*][B](opt) Towny, FactionsX, Lands, Feudal and FactionsUUID [/B](and its forks)[B] compatibility, [/B]adds options to make the mayor/leader bypass break and/or open protection and to disable protection on unclaimed lands like Wilderness.[COLOR=rgb(64, 64, 64)] (MassiveCore Factions should work too, but is not supported)[/COLOR]
[*][B](opt) GriefPrevention compatibility[/B], enabled by default, denies protection in locations where the player does not have build permissions.

[*][I]Much more... (read the configuration file to know about all the features!)[/I]
[/LIST]
[SPOILER="TODO"]
[LIST]
[*][S]Disable protection on unclaimed lands[/S]
[*][S]Shift-Click to Protect chests[/S]
[*][S]Add Explosion protection[/S]
[*][S]Add support for FactionsX and Lands[/S]
[*][S]Remove protected chests after "X" days[/S]
[*][S]Add UUID support, because right now player names are used[/S]
[*][S]Add /protect add <player>, and /protect remove <player>, to give/revoke access to the block while standing in front of the sign.[/S]
[*][S]Add option to make whitelisted players able to access the chest only while you are online.[/S]
[*]Add /protect whitelist, to open a gui to manage whitelisted players
[*][B]Make your own suggestion in the Discussion section <3[/B]
[/LIST]
[/SPOILER]

[SPOILER="API"]
LocketteXAPI#isProtected(BlockState) // boolean
LocketteXAPI#hasAccess(Player,BlockState) // boolean
LocketteXAPI#getOwner(BlockState) // SignUser
LocketteXAPI#getSignOwner(Sign, signBlock, attachedBlock) // SignUser

From the SignUser you can grab the player name (getName()) and UUID (getUniqueId())

PlayerProtectBlockEvent // cancellable, called before successfully protecting a block. Has methods to get Player and Block involved.
[/SPOILER]

[CENTER][IMG]https://i.imgur.com/lgoL8ZS.png[/IMG]

[SIZE=6]You will find all Permissions inside [B]config.yml[/B][/SIZE][/CENTER]
[LIST]
[*][SIZE=4]All permissions are configurable, you can edit them yourself to your liking, and reload the plugin in order for the changes to be applied.[/SIZE]
[/LIST]
[CENTER][B]____________________________________[/B]

[QUOTE][B][B]Bugs [/B]and [B]Suggestions [/B]go in the [B]DISCUSSION [/B]page, the [B]Review [/B]section is [B]not [/B]the right place for complaints.[/B][/QUOTE][/CENTER]