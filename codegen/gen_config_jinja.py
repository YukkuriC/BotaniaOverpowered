from YukkuriC.minecraft.codegen.jinja import *

load_data_yaml('config.yaml')
batch_gen(
    load_env(__file__),
    {
        'common': 'common/src/main/java/io/yukkuric/botania_overpowered/BotaniaOPConfig.java',
        'forge': 'forge/src/main/java/io/yukkuric/botania_overpowered/forge/BotaniaOPConfigForge.java',
        'fabric': 'fabric/src/main/java/io/yukkuric/botania_overpowered/fabric/BotaniaOPConfigFabric.java',
    },
)
