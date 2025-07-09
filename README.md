# Botania Overpowered

[![Curseforge](https://badges.moddingx.org/curseforge/versions/1205282) ![CurseForge](https://badges.moddingx.org/curseforge/downloads/1205282)](https://www.curseforge.com/minecraft/mc-mods/botaniaoverpowered)  
[![Modrinth](https://badges.moddingx.org/modrinth/versions/hpVTpNjB) ![Modrinth](https://badges.moddingx.org/modrinth/downloads/hpVTpNjB)](https://modrinth.com/mod/botaniaoverpowered)

An addon of [Botania](https://github.com/VazkiiMods/Botania) about some enhancements / un-nerfs which are kind of overpowered.

## Features (every entry can be toggled off in config file)

-   Dandelifeon changes
    -   consuming center cells no longer destroys outer cells
    -   modifyable GoL rules
        -   configurable by two bitset numbers for `New` and `Keep`
            -   for example, the default rule is `new=8; keep=12`:
                -   8 = 2 ^ **`3`** -> new cell with **`3`** neighbors
                -   12 = 8 + 4 = 2 ^ **`3`** + 2 ^ **`2`** -> cell keeps alive with **`2`** or **`3`** neighbors
        -   API under `io.yukkuric.botania_overpowered.api.DandelifeonRules`; change them with KubeJS at will (has higher priority)
            ```js
            // requires: botania_overpowered
            let API = Java.loadClass('io.yukkuric.botania_overpowered.api.DandelifeonRules')
            API.NEW = API.GenFromNums(1, 3, 5)
            API.KEEP = x => x >= 1 && x <= 5
            ```
-   Entropinnyum changes
    -   disables unethical duper TNT check
    -   TNTs exploding in liquids now count as valid
-   Narslimmus changes
    -   ignores slime chunk restrictions and accepts ALL slimes, including ones from cages/eggs/split, magma cubes, (theoretically) TConstruct varieties, etc.
-   Mana Pylon Pump
    -   places on a mana receiver (pool, spreader, etc.), binds to another mana receiver with _Wand of the Forest_, then the mana transport begins
    -   configurable speed, max distance, loss ratio & particle fx
    -   now supports vertical stacking, letting multiple pylon drain from single target at the same time
-   Exoflame changes (Create compat)
    -   lights up Blaze Burner blocks nearby, if they have valid working blocks above
        -   custom workstation blocks can be added with tag `#botania_overpowered:blazeburner_target` (currently only distillation tank from `Create Diesel Generator`)
    -   enables super-heat on enchanted soil
-   Thermalily changes
    -   no longer consumes lava during its cooldown
-   Mana Enchanter changes
    -   can create enchanted books from books
    -   accepts all enchantments rather than the first from provided enchanted books
    -   can create conflicted enchantments (like infinity & mending)
    -   accepts enchanted items as valid source
    -   after enchantment, breaking multiblock structure makes its enchantment split into separate books
-   Gaia Guardian
    -   removes damage cap 32 for single hit
    -   decreases invulnerable duration when attacked during summoning stage
    -   allows being attacked by & dropping loots for fake players (like deployer from `Create`)
-   (Forge exclusive) Mekanism interop:
    -   MekaSuit as mana provider
    -   MekaSuit Helmet can be combined with Ancient Will(s)
-   Misc features
    -   Displays the _`Evil`_ mana numbers
        -   also appear inside the wand HUDs when player crouches
    -   Terra Blade beams inherit damage (and fire aspect) from the sword
    -   Scales up output power for Power Generator block
-   Config for all features above
