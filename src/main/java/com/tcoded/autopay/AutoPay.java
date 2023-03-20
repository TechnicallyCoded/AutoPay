package com.tcoded.autopay;

import com.tcoded.autopay.util.VaultHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoPay extends JavaPlugin {

    private VaultHook vaultHook;
    
    private double payAmount;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        vaultHook = new VaultHook(this);
        vaultHook.init();

        int interval = getConfig().getInt("payment-interval", 60 * 20); // 1 per minute default
        payAmount = getConfig().getDouble("payment-amount", 1.0d);

        this.getServer().getScheduler().runTaskTimer(this, this::autoPay, 20, interval);
    }

    private void autoPay() {
        for (Player player : this.getServer().getOnlinePlayers()) {
            this.vaultHook.addMoney(player, payAmount);
        }
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }
}
