# Botania Overpowered

[![Curseforge](https://badges.moddingx.org/curseforge/versions/1205282) ![CurseForge](https://badges.moddingx.org/curseforge/downloads/1205282)](https://www.curseforge.com/minecraft/mc-mods/botaniaoverpowered)  
[![Modrinth](https://badges.moddingx.org/modrinth/versions/hpVTpNjB) ![Modrinth](https://badges.moddingx.org/modrinth/downloads/hpVTpNjB)](https://modrinth.com/mod/botaniaoverpowered)

An addon of [Botania](https://github.com/VazkiiMods/Botania) about some enhancements / un-nerfs which are kind of overpowered.

## Features

-   Dandelifeon changes
    -   consuming center cells no longer destroys outer cells
    -   modifyable GoL rules
        -   API under `io.yukkuric.botania_overpowered.api.DandelifeonRules`; change them with KubeJS at will
            ```js
            // requires: botania_overpowered
            let API = Java.loadClass("io.yukkuric.botania_overpowered.api.DandelifeonRules")
            API.NEW = API.GenFromNums(1, 3, 5)
            API.KEEP = (x) => x >= 1 && x <= 5
            ```
-   Entropinnyum changes
    -   disables unethical duper TNT check
    -   TNTs exploding in liquids now count as valid
-   Narslimmus changes
    -   accepts all slimes, including ones from cages/eggs/split, magma cubes, (theoretically) TConstruct varieties, etc.
-   Forge exclusive: MekaSuit from Mekanism as mana provider
-   Config for all features above
