package com.tcoded.autopay.util;

import com.tcoded.autopay.AutoPay;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    private final AutoPay plugin;

    private Economy eco;

    public VaultHook(AutoPay plugin) {
        this.plugin = plugin;

    }

    public boolean init() {
        if (this.plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        eco = rsp.getProvider();
        return true;
    }

    public void addMoney(Player player, double amount) {
        this.eco.depositPlayer(player, amount);
    }

}
