package me.caseload.knockbacksync.player;

import com.github.retrooper.packetevents.util.Vector3d;
import me.caseload.knockbacksync.world.PlatformWorld;
import me.caseload.knockbacksync.world.SpigotWorld;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class BukkitPlayer implements PlatformPlayer {
    public final Player bukkitPlayer;

    public BukkitPlayer(Player player) {
        this.bukkitPlayer = player;
    }

    @Override
    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }

    @Override
    public String getName() {
        return bukkitPlayer.getName();
    }

    @Override
    public double getX() {
        return bukkitPlayer.getLocation().getX();
    }

    @Override
    public double getY() {
        return bukkitPlayer.getLocation().getY();
    }

    @Override
    public double getZ() {
        return bukkitPlayer.getLocation().getZ();
    }

    @Override
    public float getPitch() {
        return bukkitPlayer.getLocation().getPitch();
    }

    @Override
    public float getYaw() {
        return bukkitPlayer.getLocation().getYaw();
    }

    @Override
    public boolean isOnGround() {
    /* Inconsistent with Entity.isOnGround()
    /  Checks to see if this player is currently standing on a block.
    /  This information may not be reliable, as it is a state provided by the client, and may therefore not be accurate.
    /  It can also easily be spoofed. We may want to cast to LivingEntity and call isOnGround() instead
    */
        return bukkitPlayer.isOnGround();
    }


    @Override
    public int getPing() {
        return bukkitPlayer.getPing();
    }

    @Override
    public boolean isGliding() {
        return bukkitPlayer.isGliding();
    }

    @Override
    public PlatformWorld getWorld() {
        return new SpigotWorld(bukkitPlayer.getWorld());
    }

    @Override
    public Vector3d getLocation() {
        Location location = bukkitPlayer.getLocation();
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void sendMessage(@NotNull String s) {
        bukkitPlayer.sendMessage(s);
    }

    @Override
    public double getAttackCooldown() {
        return bukkitPlayer.getAttackCooldown();
    }

    @Override
    public boolean isSprinting() {
        return bukkitPlayer.isSprinting();
    }

    @Override
    public int getMainHandKnockbackLevel() {
        return bukkitPlayer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.KNOCKBACK);
    }

    @Override
    public @Nullable Integer getNoDamageTicks() {
        return bukkitPlayer.getNoDamageTicks();
    }

    @Override
    public void setVelocity(Vector3d adjustedVelocity) {
        bukkitPlayer.setVelocity(new Vector(adjustedVelocity.x, adjustedVelocity.y, adjustedVelocity.z));
    }
}