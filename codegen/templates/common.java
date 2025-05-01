package io.yukkuric.botania_overpowered;

{% set data_common = filter_val.side.common -%}
public class BotaniaOPConfig {
    static CommonAccess CFG;
    public static void bindConfig(CommonAccess cfg) {
        CFG = cfg;
    }

    {%- for line in data_common %}
    public static {{line.type}} {{line.name}}() {
        return CFG.{{line.name}}();
    }
    {%- endfor %}
    public interface CommonAccess {
        {%- for line in data_common %}
        String desc_{{line.name}} = "{{line.descrip}}";
        {%- endfor %}
        {%- for line in data_common %}
        {{line.type}} {{line.name}}();
        {%- endfor %}
    }
}