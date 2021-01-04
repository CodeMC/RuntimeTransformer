package net.hypercubemc.runtimetransformerexample.transformer;

import net.hypercubemc.runtimetransformer.annotation.Inject;
import net.hypercubemc.runtimetransformer.annotation.InjectionType;Yamakaj
import net.hypercubemc.runtimetransformer.annotation.Transform;

import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

import java.lang.reflect.Field;

@Transform(CraftServer.class)
public class CraftServerTransformer {

    @Inject(InjectionType.OVERRIDE)
    public String getName() {
        Field serverName;
        try {
            serverName = CraftServer.class.getDeclaredField("serverName");
            return serverName.get(this) + " (Modified by Runtime Transformer Example Plugin)";
        } catch (NoSuchFieldException | IllegalAccessException error) {
            error.printStackTrace();
            return null;
        }
    }
}
