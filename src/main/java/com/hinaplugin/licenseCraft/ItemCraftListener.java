package com.hinaplugin.licenseCraft;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemCraftListener implements Listener {

    @EventHandler
    public void onItemCraft(CraftItemEvent event){
        final ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null){
            return;
        }

        if (itemStack.isEmpty()){
            return;
        }

        if (itemStack.getType().isAir()){
            return;
        }

        if (itemStack.getItemMeta().getPersistentDataContainer().has(LicenseCraft.key)){
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            return;
        }

        final String licensePlayer = LicenseCraft.config.getString("item-license." + itemStack.getType(), "");
        final String newLicensePlayer = event.getWhoClicked().getName();
        if (licensePlayer.isEmpty()){
            for (Player player : LicenseCraft.plugin.getServer().getOnlinePlayers()) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>" + itemStack.getType() + "の著作権を" + newLicensePlayer + "が取得しました．</green>"));
            }
        }else {
            for (Player player : LicenseCraft.plugin.getServer().getOnlinePlayers()) {
                player.sendMessage(MiniMessage.miniMessage().deserialize("<aqua>" + itemStack.getType() + "の著作権が" + licensePlayer + "から" + newLicensePlayer + "に移りました．</aqua>"));
            }
        }

        LicenseCraft.config.set("item-license." + itemStack.getType(), event.getWhoClicked().getName());
        LicenseCraft.plugin.saveConfig();
        LicenseCraft.plugin.reloadConfig();
        LicenseCraft.config = LicenseCraft.plugin.getConfig();
    }
}
