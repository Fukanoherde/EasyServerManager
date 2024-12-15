# EasyServerManager
This plugin is subject to copyright protection. You can rewrite it but you still have to credit the author. If it turns out that this is not the case, this will lead to legal prosecution. This plugin is almost completely customizable. You can adjust a lot of things to your needs via the config.

Plugin-Name: EasyServerManager

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
Rechte:

    -> Sternchenrechte -> easyservermanager.*
    -> SetWarp -> easyservermanager.warp.set
    -> RemoveWarp -> easyservermanager.warp.remove
Kompatibilität:

    Minecraft Versionen: 1.16 bis 1.21.4

Voraussetzungen:

    Java 21 oder höher

Installation:

    Laden Sie das Plugin von hier herunter.
    Fügen Sie die Datei in den plugins-Ordner Ihres Servers ein.
    Starten Sie den Server neu, um das Plugin zu aktivieren.
    Konfigurieren Sie die Standardoptionen in der config.yml nach Ihren Wünschen.

Support:

Für Fragen oder Unterstützung besuchen Sie unseren Support-Discord oder lesen Sie die Dokumentation auf unserer Webseite.

Dieses Plugin bietet also grundlegende Funktionen, die für die meisten Minecraft-Server nützlich sind, ohne zu viele komplexe Features zu beinhalten. Es ist einfach zu installieren und eignet sich gut für Serverbetreiber, die nur die wesentlichen Funktionen benötigen.