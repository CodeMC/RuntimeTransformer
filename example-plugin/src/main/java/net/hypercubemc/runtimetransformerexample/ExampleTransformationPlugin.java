package net.hypercubemc.runtimetransformerexample;

import net.hypercubemc.runtimetransformer.RuntimeTransformer;
import net.hypercubemc.runtimetransformerexample.transformer.CraftServerTransformer;
import net.hypercubemc.runtimetransformerexample.transformer.EntityLivingTransformer;
import net.hypercubemc.runtimetransformerexample.transformer.SkullMetaTransformer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleTransformationPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        new RuntimeTransformer(
                EntityLivingTransformer.class,
                CraftServerTransformer.class,
                SkullMetaTransformer.class
        );

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack stack = ((Player) sender).getInventory().getItemInMainHand();
        SkullMeta itemMeta = (SkullMeta) stack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setOwner("Justsnoopy30");
        }
        stack.setItemMeta(itemMeta);
        ((Player) sender).getInventory().setItemInMainHand(stack);
        return true;
    }
}
