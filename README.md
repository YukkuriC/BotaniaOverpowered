# Botania Overpowered

[![Curseforge](https://badges.moddingx.org/curseforge/versions/TODO) ![CurseForge](https://badges.moddingx.org/curseforge/downloads/TODO)](https://www.curseforge.com/minecraft/mc-mods/TODO)  
[![Modrinth](https://badges.moddingx.org/modrinth/versions/TODO) ![Modrinth](https://badges.moddingx.org/modrinth/downloads/TODO)](https://modrinth.com/mod/TODO)

An addon of [Botania](https://github.com/VazkiiMods/Botania) about some enhancements / un-nerfs which are kind of overpowered.

## Features

-   Dandelifeon changes
    -   consuming center cells no longer destroys outer cells
    -   modifyable GoL rules under `io.yukkuric.botania_overpowered.api.DandelifeonRules`; change them with KubeJS at will
        ```js
        // requires: botania_overpowered
        let API = Java.loadClass('io.yukkuric.botania_overpowered.api.DandelifeonRules')
        API.NEW = API.GenFromNums(1, 3, 5)
        API.KEEP = x => x >= 1 && x <= 5
        ```
-   Entropinnyum changes
    -   disables unethical duper TNT check
    -   TNTs exploding in liquids now count as valid
