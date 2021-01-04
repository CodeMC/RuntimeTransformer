package me.yamakaja.runtimetransformer.plugin.transformer;

import me.yamakaja.runtimetransformer.annotation.Inject;
import me.yamakaja.runtimetransformer.annotation.InjectionType;
import me.yamakaja.runtimetransformer.annotation.Transform;

import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

@Transform(CraftServer.class)
public class CraftServerTransformer {

    @Inject(InjectionType.OVERRIDE)
    public String getVersion() {
        return "42 (Minecraft 2.0)";
    }

}
