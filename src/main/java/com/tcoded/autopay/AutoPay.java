package com.tcoded.autopay;

import com.tcoded.autopay.util.VaultHook;
import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.impl.ServerImplementation;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public final class AutoPay extends JavaPlugin {

    private VaultHook vaultHook;
    
    private double payAmount;
    private ServerImplementation schedulerImpl;
    private FoliaLib foliaLib;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        vaultHook = new VaultHook(this);
        vaultHook.init();

        int interval = getConfig().getInt("payment-interval", 60 * 20); // 1 per minute default
        payAmount = getConfig().getDouble("payment-amount", 1.0d);

        this.foliaLib = new FoliaLib(this);
        this.schedulerImpl = foliaLib.getImpl();
        this.schedulerImpl.runTimer(this::autoPay, 20 * 50, interval * 50L, TimeUnit.MILLISECONDS);
    }

    private void autoPay() {
        for (Player player : this.getServer().getOnlinePlayers()) addMoney(player);
    }

    private void addMoney(Player player) {
        this.vaultHook.addMoney(player, payAmount);
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);
    }
}
