package com.hinaplugin.licenseCraft;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class ItemPrepareCraftListener implements Listener {

    @EventHandler
    public void onItemCraft(PrepareItemCraftEvent event){
        final ItemStack itemStack = event.getInventory().getResult();

        if (itemStack == null){
            return;
        }

        if (itemStack.isEmpty()){
            return;
        }

        final Material material = itemStack.getType();

        final ConfigurationSection configurationSection = LicenseCraft.config.getConfigurationSection("item-license");

        if (configurationSection == null){
            return;
        }

        final Set<String> keys = configurationSection.getKeys(false);

        if (keys.contains(material.toString())){
            final Player licensePlayer = LicenseCraft.plugin.getServer().getPlayer(LicenseCraft.config.getString("item-license." + material, ""));
            if (licensePlayer != null){
                if (!LicenseCraft.config.getString("item-license." + material, "").equalsIgnoreCase(event.getViewers().get(0).getName())){
                    event.getInventory().getResult().setType(Material.RED_STAINED_GLASS_PANE);
                    final ItemMeta itemMeta = event.getInventory().getResult().getItemMeta();
                    itemMeta.displayName(Component.text("このアイテムの著作権は" + LicenseCraft.config.getString("item-license." + material) + "にあります．"));
                    itemMeta.getPersistentDataContainer().set(LicenseCraft.key, PersistentDataType.STRING, "license");
                    event.getInventory().getResult().setItemMeta(itemMeta);
                }
            }
        }
    }
}
