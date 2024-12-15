# Plugin-Name: EasyServerManager

Beschreibung:

EasyServerManager ist ein einfaches und benutzerfreundliches Plugin für Minecraft, das die grundlegenden Funktionen für einen Server bietet. Es umfasst wichtige Features wie die Verwaltung von Befehlen, Teleportation, Welten Creationen und grundlegende Spieler-Interaktionen. Ideal für Server, die schnell und unkompliziert essentielle Funktionen integrieren möchten.

Funktionen:

    Befehlsverwaltung: Eine Reihe grundlegender Befehle wie /spawn, /home, /tpa, und /msg, die den Spielern das tägliche Servererlebnis erleichtern.

    Teleportation: Ermöglicht es Spielern, sich zu einem festgelegten Spawnpunkt, zu anderen Spielern oder zu benutzerdefinierten Home-Punkten zu teleportieren.

    Welten Creationen: Bietet einfache grundliegende möglichkeiten einfach welten zu erstellen aber auch sich dort hin zu teleportieren zu können.

    Chat-Management: Unterstützung für private Nachrichten zwischen Spielern sowie für die Anzeige von Nachrichten im Chat, die das Spielerlebnis verbessern. Sowie ein personalisierte Rang prefixe. Aber auch ein Chat zensur ist mit integriert. Die wörter die Zensiert werden sollen können über einen Befehl ("/censor add <wort>") hinzugefühgt werden.

Befehle:

    -> /gamemode <0, 1, 2, 3> oder /gamemode <spieler> <0, 1, 2, 3> -> Setzt euren oder deren von anderen Spieler in einem anderen Gamemode.
        -> Aliases: /gm
    -> /kick <spieler> <grund> -> Kickt einen spieler von Server.
    -> /teleport <spieler> -> Damit kannst du dich zu einen anderen Spieler/Spielerin teleportieren.
        -> Aliases: /tp
    -> /vanish -> Setzt euch in Vanish, das euch andere spieler ohne Permission sehen können.
        -> Aliases: /v
    -> /time <day, night, midnight> -> Setzt die tageszeit auf der Welt wo sie sich befinden zu den jeweiligen tageszeit.
    -> /weather <sun, rain, thunder> -> Setzt das wetter in dem aktuellen welt wo sie sich befinden in dem Jeweiligen wettertyp.
    -> /invsee <spieler> -> Damit könnt ihr in den Inventar anderer Spieler/Spielerinnen schauen.
    -> /tphere <spieler> -> Damit können sie anderen Spieler/Spielerinnen zu sich selbst teleportieren.
    -> /heal oder /heal <spieler> -> Damit können sie sich selbst oder auch andere Spieler/Spielerinnen Heilen.
    -> /ping oder /ping <spieler> -> Damit können Sie sich ihren eigenen Ping oder deren andere Spieler und Spielerinnen anzeigen lassen.
    -> /alert <spieler> -> Sie können mit diesem Befehl anderen spieler eine Titel Nachricht senden.
    -> /clear <spieler> -> So können sie von anderen Spieler/Spielerinnen das Inventar leeren.
    -> /fly oder /fly <spieler> -> So können ihren oder deren von anderen Spieler/Spielerinnen das flugmodus ändern.
    -> /tpa <spieler> -> Damit kann man anderen Spieler/Spielerinnen eine anfrage senden sich zu denen zu teleportieren.
    -> /farm -> Damit können Sie sich zum Farmpunkt teleportieren. (Wichtig ist da das Sie einen Warppunkt mit dem Namen "Farm" erstellen)
    -> /spawn -> Teleportiert sie zum Serverspawn. (Wichtig ist da das sie einen Warppunkt mit dem namen "Spawn" erstellen)
    -> /deposit <level> -> Mit diesem befehl können Sie sich selbst eingespeicherte Level abheben.
        -> Aliases: /depo
    -> /bank -> Damit können Sie sehen wieviele Level sie eingespeichert haben.
    -> /warn -> So können Sie anderen Spieler/Spielerinnen verwarnen.
    -> /god oder /god <spieler> -> So können Sie bei sich selbst oder anderen Spielern aktivieren das die keinerlei Schaden bekommen.
    -> /maintenance on -> So können sie die Wartungsarbeiten aktivieren.
    -> /maintenance off -> So können sie die Wartungsarbeiten deaktivieren.
    -> /maintenance add <spieler> -> So können sie andere Spieler/Spielerinnen erlauben während der Wartung joinen zu dürfen.
    -> /maintenance remove <spieler> -> So können sie andere Spieler/Spielerinnen die erlaubnis entziehen während der Wartung zu joinen.
    -> /setjail <jailname> -> Damit können sie einen Jail punkt erstellen.
    -> /jail <spieler> <jailname> -> So können sie anderen Spieler/Spielerinnen jailen.
    -> /payout <level> -> So können sie sich selbst Level abholen die sie eingespeichert haben.
    -> /home <homename> -> So können sie sich zu ihrem eigenen Homepunkt teleportieren.
    -> /home set <homename> - So können sie sich einen Homepunkt erstellen.
    -> /home remove <homename> So können sie ihren Homepunkt wieder löschen.
    -> /listhomes -> Mit diesem befehl lassen sich all ihre Homepunkte anzeigen.
    -> /reward -> Dieses befehl ermöglicht euch eine Tägliche belohnung abzuholen (Wenn es aktiviert ist)
    -> /pay <spieler> <level> -> Mit diesem Befehl können Sie anderen Spieler/Spielerinnen eingespeicherte Level von sich selbst senden.
    -> /pvp on -> So können Sie ihren eigenen PvP Modus aktivieren. So können sie von anderen Spieler/Spielerinnen angegriffen werden.
    -> /pvp off -> So können sie ihren eigenen PvP Modus deaktivieren. So können sie von anderen Spieler/Spielerinnen nicht mehr angegriffen werden.
    -> /back -> So können sie sich zu ihrem letzten Todespunkt teleportieren.
    -> /level add <spieler> <level> -> So können sie sich selbst oder auch anderen Spieler/Spielerinnen level geben.
    -> /level remove <spieler> <level> -> So können sie sich oder auch anderen Spieler/Spielerinnnen level entziehen.
    -> /warp <warpname> -> Damit können sie sich zu den Warp Punkten teleportieren.
    -> /setwarp <warpname> -> Damit können sie einen neuen Warp Punkt erstellen.
    -> /warpremove <warpname> -> Damit können sie bestehende Warp Punkte wieder löschen.
    -> /listwarps -> Damit lassen sich alle bestehende Warp Punkte aufm Server anzeigen.
    -> /censor -> Listet alle gesperrte wörter an. Diese wörter werden im Chat zensiert.
    -> /censor add <wort> -> Damit lassen sich wörter hinzufügen. Diese können dann nicht mehr gesendet werden.
    -> /censor remove <wort> -> Damit lassen sich wörter aus der Gesperrten liste entfernen. Diese können dann gesendet werden.
    -> /world create <worldname> <worldtype> Damit lassen sich welten in dem jeweiligen Typ erstellen. Mögliche typen sind normal, flat oder large.
    -> /world tp <worldname> -> Damit können Sie sich selber zu dem angegebenen welt teleportieren.
    -> /world remove <worldname> -> Damit lassen sich welten wieder löschen.
    -> /burn <spieler> -> So können sie anderen Spieler/Spielerinnen in brand setzen.
    -> /log -> So können sie den Befehl Log aktivieren oder deaktivieren. So können sie sehen welche befehle andere ausführen.
    -> /report add <spieler> <grund> -> Damit können Sie andere Spieler/Spielerinnen melden.
    -> /report list <spieler> -> So können Sie sehen ob ein Spieler offene Reports hat.
    -> /report accept <spieler> <grund> -> So können sie einen report anfrage bearbeiten. Sie werden automatisch an den erstellungspunkt teleportiert.
    -> /report close <spieler> <grund> -> So können sie einen fertig bearbeiten report wieder Schließen. Wichtig da nur das nur der Spieler der den Report bearbeitet diesen wieder schließen kann.
    -> /easyservermanager -> So lassen sich alle wichtigen Infos anzeigen.
        -> Aliases: /esm
    -> /easyservermanager restart now <zeit> -> So können sie den server in einer angegebener Sekunden zahl neustarten.
        -> Aliases: /esm restart now <zeit>
    -> /tpall -> So können sie alle Spieler/Spielerinnen zu sich teleportieren.
    -> /enderchest <spieler> -> So können Sie in den Enderchesten anderer Spieler/Spielerinnen schauen.
        -> Aliases: /ec <spieler>
    -> /chatclear -> So können Sie den server chat leeren.
        -> Aliases: /cc
Rechte:

    -> Sternchenrechte -> easyservermanager.*
    -> /setwarp -> easyservermanager.warp.set
    -> /removewarp -> easyservermanager.warp.remove
    -> /heal -> easyservermanager.heal
    -> /warp -> easyservermanager.warp.tp
    -> /heal <spieler> -> easyservermanager.heal.other
    -> /chatclear -> easyservermanager.chatclear
    -> /clear -> easyservermanager.clear
    -> /enderchest -> easyservermanager.enderchest
    -> /fly -> easyservermanager.fly
    -> /fly <spieler> -> easyservermanager.fly.other
    -> /gamemode -> easyservermanager.gamemode
    -> /gamemode <spieler> -> easyservermanager.gamemode.other
    -> /god -> easyservermanager.god
    -> /god <spieler> -> easyservermanager.god.other
    -> /invsee -> easyservermanager.invsee
    -> /jail -> easyservermanager.jail
    -> /world create <name> <typ> -> easyservermanager.world.create
    -> /world tp <name> -> easyservermanager.world.tp
    -> /world remove <name> -> easyservermanager.world.remove
    -> /kick -> easyservermanager.kick
    -> /level -> easyservermanager.level
    -> /listhomes <spieler> -> easyservermanager.gethomes.other
    -> /maintenance on  oder off -> easyservermanager.maintenance.activate
    -> /maintenance add <spieler> -> easyservermanager.maintenance.add
    -> /maintenance remove <spieler> -> easyservermanager.maintenance.remove
    -> /ping <spieler> -> easyservermanager.ping
    -> /setjail -> easyservermanager.setjail
    -> /teleport <spieler> -> easyservermanager.teleport
    -> /time -> easyservermanager.time
    -> /tphere <spieler> -> easyservermanager.tphere
    -> /weather -> easyservermanager.weather
    -> /censor -> easyservermanager.censor
    -> /burn <spieler> -> easyservermanager.burn
    -> /log -> easyservermanager.seecommands
    -> /warn add <spieler> grund> -> easyservermanager.warn
    -> /warn get <spieler> -> easyservermanager.warn.get
    -> /warn remove <spieler> <grund> -> easyservermanager.warn.remove
    -> /report list <spieler> -> easyservermanager.report.list
    -> /report close <spieler> <grund> -> easyservermanager.report.close
    -> /report accept <spieler> <grund> -> easyservermanager.report.accept
    -> /easyservermanager restart now -> easyservermanager.restart
    -> /tpall -> easyservermanager.tpall
    -> /reload oder /rl -> easyservermanager.reload
    -> Report benachrichtung -> easyservermanager.report.notify
    -> Warn benachrichtigung -> easyservermanager.warn.notify
    -> Sehen von Plugins die auf dem Server laufen -> easyservermanager.plugins
    -> Reward Benachrichtigun -> easyservermanager.reward.notify
    -> Maintenance Join -> easyservermanager.maintenance.join
    -> Chat Prefixe
        Owner -> prefix.owner
        CoOwner -> prefix.coowner
        SrAdmin -> prefix.sradmin
        Admin -> prefix.admin
        SrDeveloper -> prefix.srdeveloper
        Developer -> prefix.developer
        Test-Developer -> prefix.testdeveloper
        SrModerator -> prefix.srmod
        Moderator -> prefix.mod
        Test-Moderator -> prefix.testmoderator
        SrSupporter -> prefix.srsupporter
        Supporter -> prefix.supporter
        Test-Supporter -> prefix.testsupporter
        SrBuilder -> prefix.srbuilder
        Builder -> prefix.builder
        Test-Builder -> prefix.testbuilder
        Freund -> prefix.friend
        YouTuber -> prefix.youtuber
        Streamer -> prefix.streamer
        Premium Drei -> prefix.premiumthree
        Premium Zwei -> prefix.premiumtwo
        Premium eins -> prefix.premiumone
    -> Alle rechte können auch in der config.yml umgestellt werden.
Kompatibilität:

    Minecraft Versionen: 1.16 bis 1.21.4

Voraussetzungen:

    Java 21 oder höher
Erläuterungen:

    RewardSystem -> Mit diesem system können Sie einstellen was man täglich als belohnung bekommen kann.
    WorldSystem -> Dies biete verinfachte möglichkeiten welten erstellen zu können.
    ReportSystem -> So könnt ihr andere für Verstöße jegliche art melden. Diese werden in einer file gespeichert und können in nachhinein bearbeitet werden.
    WarnSystem -> In diesem System könnt ihr Spieler verwarnen. Nach einer bestimmten anzahl der Verwarnungen wird der Spieler vom Server gekickt. Diese grenze kann in der config.yml eingestellt werden.
    LevelSystem -> Du sowie die Spieler können so Level in einer Datei speichern. Dabei gibt es eine maximal grenze diese kann in der Config umgestellt werden.
    PaySystem -> Das Pay system läuft über eure Level. Die level müssen eingespeichert sein. Ihr könnt dabei nicht mehr versenden als ihr eingespeichert habt.
    MaintenanceSystem -> Ihr könnt die Wartung aktivieren und deaktivieren. Aber das ist noch nicht alles. So könnt ihr einem Spieler rechte geben auch während der Wartung joinen zu können. So müsst ihr dem Spieler nicht gleich Wartungs rechte geben.
    BanSystem -> Dieses System wird noch in den kommenden Updates folgen.
    RestartSystem -> So könnt ihr den Server mit einem Countdown neustarten. Die Kick nachricht könnt ihr ebenfalls in der config einstellen.
    ReloadSystem -> Hier könnt ihr einfach mit /rl oder /reload Den server neuladen. Wie im standard Minecraft. Nur ist da eine personalisierte nachricht zu sehen.
    GrundSystem -> Es kann soweit fast alles selbst eingestellt werden. MariaDB sowie MongoDB folgen noch als update. Dort können dann level, Reports, Verwarnungen usw. Gespeichert werden.
    PrefixSystem -> Ihr könnt die Chat Prefixe selber umstellen.
    Tablist und Join Titel -> Ihr könnt auch die Tablist sowie die Join Titel nachricht umstellen

Installation:

    Laden Sie das Plugin von hier herunter.
    Fügen Sie die Datei in den plugins-Ordner Ihres Servers ein.
    Starten Sie den Server neu, um das Plugin zu aktivieren.
    Konfigurieren Sie die Standardoptionen in der config.yml nach Ihren Wünschen.

Support:

Für Fragen oder Unterstützung besuchen Sie unseren Support-Discord oder lesen Sie die Dokumentation auf unserer Webseite.

Dieses Plugin bietet also grundlegende Funktionen, die für die meisten Minecraft-Server nützlich sind, ohne zu viele komplexe Features zu beinhalten. Es ist einfach zu installieren und eignet sich gut für Serverbetreiber, die nur die wesentlichen Funktionen benötigen.

English Description:
EasyServerManager is a simple and user-friendly plugin for Minecraft that provides basic server functionalities. It includes essential features such as command management, teleportation, world creation, and basic player interactions. Ideal for servers that want to quickly and easily integrate essential features.
Features:

    Command Management: A set of basic commands such as /spawn, /home, /tpa, and /msg that make the players' daily server experience easier.

    Teleportation: Allows players to teleport to a set spawn point, to other players, or to custom home points.

    World Creation: Offers simple, basic options to create worlds and also teleport to them.

    Chat Management: Supports private messages between players as well as displaying messages in the chat to enhance the player experience. Also includes personalized rank prefixes. A chat filter is integrated as well, where words to be censored can be added using the command "/censor add <word>".
Commands:

    -> /gamemode <0, 1, 2, 3> or /gamemode <player> <0, 1, 2, 3> -> Set yours or those of other players in a different game mode..
        -> Aliases: /gm
    -> /kick <player> <reason> -> Kicked a player from the server.
    -> /teleport <player> -> This allows you to teleport to another player.
        -> Aliases: /tp
    -> /vanish -> Sit in Vanish so that other players cannot see you without permission.
        -> Aliases: /v
    -> /time <day, night, midnight> -> Sets the time of day in the world where you are at the current time of day.
    -> /weather <sun, rain, thunder> -> Sets the weather in the current world where you are in the respective weather type.
    -> /invsee <player> -> This allows you to look into other players’ inventories.
    -> /tphere <player> -> This allows them to teleport other players to themselves.
    -> /heal or /heal <player> -> This allows them to heal themselves or other players.
    -> /ping or /ping <player> -> This allows you to display your own ping or that of other players.
    -> /alert <player> -> You can use this command to send a title message to other players.
    -> /clear <player> -> This allows them to empty other players’ inventories.
    -> /fly or /fly <player> -> This way you or other players can change the flight mode.
    -> /tpa <player> -> This allows you to send other players a request to teleport to them.
    -> /farm -> This allows you to teleport to the farm point. (It is important that you create a warp point with the name "Farm")
    -> /spawn -> Teleports you to the server spawn. (It is important that you create a warp point with the name "Spawn")
    -> /deposit <level> -> With this command you can withdraw levels you have saved yourself.
        -> Aliases: /depo
    -> /bank -> This allows you to see how many levels you have saved.
    -> /warn -> This is how you can warn other players.
    -> /god or /god <player> -> This way you can activate it for yourself or other players so that they do not take any damage.
    -> /maintenance on -> This is how you can activate the maintenance work.
    -> /maintenance off -> This is how you can deactivate the maintenance work.
    -> /maintenance add <player> -> This way you can allow other players to join during maintenance.
    -> /maintenance remove <player> -> This way you can revoke other players' permission to join during maintenance.
    -> /setjail <jailname> -> This allows you to create a jail point.
    -> /jail <player> <jailname> -> This allows them to jail other players.
    -> /payout <level> -> This way you can retrieve levels that you have saved.
    -> /home <homename> -> This allows them to teleport to their own home point.
    -> /home set <homename> - This way you can create a home point.
    -> /home remove <homename> This way you can delete your home point again.
    -> /listhomes -> With this command you can display all your home points.
    -> /reward -> This command allows you to collect a daily reward (if it is activated)
    -> /pay <player> <level> -> With this command you can send your saved levels to other players.
    -> /pvp on -> This allows you to activate your own PvP mode. This allows you to be attacked by other players.
    -> /pvp off -> This allows them to deactivate their own PvP mode. This means they can no longer be attacked by other players.
    -> /back -> This allows them to teleport to their final death point.
    -> /level add <player> <level> -> This way they can level up themselves or other players.
    -> /level remove <player> <level> -> This allows them to evade levels for themselves or other players.
    -> /warp <warpname> -> This allows them to teleport to the warp points.
    -> /setwarp <warpname> -> This allows you to create a new warp point.
    -> /warpremove <warpname> -> This allows you to delete existing warp points.
    -> /listwarps -> This allows you to display all existing warp points on the server.
    -> /censor -> Lists all blocked words. These words will be censored in chat.
    -> /censor add <word> -> This allows you to add words. These can then no longer be sent.
    -> /censor remove <word> -> This allows you to remove words from the blocked list. These can then be sent.
    -> /world create <worldname> <worldtype> This allows you to create worlds in the respective type. Possible types are normal, flat or large.
    -> /world tp <worldname> -> This allows you to teleport yourself to the specified world.
    -> /world remove <worldname> -> This allows you to delete worlds again.
    -> /burn <player> -> This way they can set other players on fire.
    -> /log -> This allows you to enable or disable the command log. This way you can see what commands others are executing.
    -> /report add <player> <reason> -> This allows you to report other players.
    -> /report list <player> -> This way you can see if a player has any open reports.
    -> /report accept <player> <reason> -> This is how you can edit a report request. You will automatically be teleported to the creation point.
    -> /report close <player> <reason> -> This way you can close a report that you have finished editing. This is important because only the player who is editing the report can close it.
    -> /easyservermanager -> This way, all important information can be displayed.
        -> Aliases: /esm
    -> /easyservermanager restart now <time> -> This allows you to restart the server in a specified number of seconds.
        -> Aliases: /esm restart now <time>
    -> /tpall -> This allows them to teleport all players to them.

Permissions:

    -> All rights can also be changed in the config.yml.
Compatibility:

    Minecraft Versions: 1.16 to 1.21.4

Requirements:

    Java 21 or higher

Explanation:

    RewardSystem -> This system allows you to set daily rewards for players. It offers flexibility in defining what players can receive as a reward.
    WorldSystem -> This system simplifies the process of creating and managing worlds on the server. It likely provides tools to create new worlds easily.
    ReportSystem -> Players can report violations or issues (e.g., cheating, abuse) through this system. The reports are stored in a file and can be reviewed and edited later.
    WarnSystem -> This system lets you issue warnings to players. If a player accumulates too many warnings, they will be kicked from the server. The threshold for this can be configured in the config.yml file.
    LevelSystem -> Players, including the server administrator, can save and track their levels in a file. There is a maximum level cap, which can be adjusted in the config file.
    PaySystem -> This system works based on player levels. Players can send currency or rewards, but only up to the amount they have saved. Levels must be stored for this system to function properly.
    MaintenanceSystem -> This system allows you to activate or deactivate maintenance mode on the server. Additionally, you can grant specific players permission to join the server during maintenance without giving them full maintenance rights.
    BanSystem -> The BanSystem will be introduced in future updates. It will likely allow you to ban players who violate rules.
    RestartSystem -> This system allows you to restart the server with a countdown. You can also customize the kick message displayed to players when the server restarts, via the configuration.
    ReloadSystem -> With this system, you can reload the server using commands like /rl or /reload. The reload process includes a personalized message instead of the default Minecraft message.
    GrundSystem -> This system seems to offer customization for almost everything. It mentions that future updates will integrate MariaDB and MongoDB, which will store data like levels, reports, warnings, and more.
    PrefixSystem -> You can change the chat prefixes yourself.
    Tablist and Join Title -> You can also change the tablist and the join title message

Installation:

    Download the plugin from here.
    Add the file to your server's plugins folder.
    Restart the server to activate the plugin.
    Configure the default options in the config.yml file as desired.

Support:

For questions or support, visit our Support Discord or read the documentation on our website.

This plugin provides basic features that are useful for most Minecraft servers without too many complex options. It is easy to install and well-suited for server operators who only need the essential functions.