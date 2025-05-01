package io.yukkuric.botania_overpowered.forge;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

{% set data_common = filter_val.side.common -%}
{% set data_forge = filter_val.side.forge -%}
public class BotaniaOPConfigForge implements BotaniaOPConfig.CommonAccess {
    public static BotaniaOPConfigForge INSTANCE;
    {%- for line in data_forge %}
    private static final String desc_{{line.name}} = "{{line.descrip}}";
    public static {{line.type}} {{line.name}}() {
        return INSTANCE.cfg_{{line.name}}.get();
    }
    {%- endfor %}
    {%- for line in data_common %}
    public {{line.type}} {{line.name}}() {
        return cfg_{{line.name}}.get();
    }
    {%- endfor %}
    {%- for grp,lines in group_val(data,'type') %}
    public ForgeConfigSpec.{{grp.capitalize()}}Value {% for line in lines -%}
            cfg_{{line.name}}
            {%- if loop.last %};{% else %}, {% endif %}
        {%- endfor %}
    {%- endfor %}

    public BotaniaOPConfigForge(ForgeConfigSpec.Builder builder) {
        {%- for grp,lines in group_val(data,'category') %}
        builder.push("{{grp}}");
        {%- for line in lines %}
        cfg_{{line.name}} = builder.comment(desc_{{line.name}}) {{-''-}}
                .define{% if line.type == 'boolean' %}{% else %}InRange{% endif %}("{{line.name}}", {{line.default}});
        {%- endfor %}
        builder.pop();{% if not loop.last %}{{'\n'}}{% endif %}
        {%- endfor %}

        INSTANCE = this;
        BotaniaOPConfig.bindConfig(this);
    }

    private static final Pair<BotaniaOPConfigForge, ForgeConfigSpec> CFG_REGISTRY;

    static {
        CFG_REGISTRY = new ForgeConfigSpec.Builder().configure(BotaniaOPConfigForge::new);
    }

    public static void register(ModLoadingContext ctx) {
        ctx.registerConfig(ModConfig.Type.COMMON, CFG_REGISTRY.getValue());
    }
}
