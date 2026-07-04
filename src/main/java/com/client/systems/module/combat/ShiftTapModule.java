package com.client.systems.module.combat;

import com.client.event.client.KeyboardInputEvent;
import com.client.event.player.PlayerTickEvent;
import com.client.systems.module.AbstractModule;
import com.client.systems.module.Category;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.client.util.MinecraftVariables.mc;

public final class ShiftTapModule extends AbstractModule {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("shifttap.json");

    private int ticksAmount = 5;
    private boolean onlyInAir = false;
    private boolean onlyOnGround = false;

    private int sneakTicksRemaining = 0;

    public ShiftTapModule() {
        super("shifttap", "Автоматически зажимает шифт при ударе", Category.Combat);
        setKey(GLFW.GLFW_KEY_UNKNOWN);
        loadConfig();
    }

    public int getTicksAmount() {
        return ticksAmount;
    }

    public void setTicksAmount(int ticksAmount) {
        this.ticksAmount = Math.max(1, Math.min(20, ticksAmount));
        saveConfig();
    }

    public boolean isOnlyInAir() {
        return onlyInAir;
    }

    public void setOnlyInAir(boolean onlyInAir) {
        this.onlyInAir = onlyInAir;
        if (onlyInAir) {
            this.onlyOnGround = false;
        }
        saveConfig();
    }

    public boolean isOnlyOnGround() {
        return onlyOnGround;
    }

    public void setOnlyOnGround(boolean onlyOnGround) {
        this.onlyOnGround = onlyOnGround;
        if (onlyOnGround) {
            this.onlyInAir = false;
        }
        saveConfig();
    }

    public void onAttack() {
        if (!isEnabled()) return;
        if (mc.player == null) return;

        if (mc.player.isTouchingWater() || mc.player.isInLava()) return;

        if (isInWeb(mc.player)) return;

        boolean onGround = mc.player.isOnGround();
        if (onlyInAir && onGround) return;
        if (onlyOnGround && !onGround) return;

        sneakTicksRemaining = ticksAmount;
    }

    private boolean isInWeb(PlayerEntity player) {
        if (mc.world == null) return false;
        return mc.world.getBlockState(player.getBlockPos()).isOf(Blocks.COBWEB)
            || mc.world.getBlockState(player.getBlockPos().up()).isOf(Blocks.COBWEB);
    }

    @Subscribe
    public void onPlayerTick(final PlayerTickEvent event) {
        if (sneakTicksRemaining > 0) {
            sneakTicksRemaining--;
        }
    }

    @Subscribe
    public void onKeyboardInput(final KeyboardInputEvent event) {
        if (sneakTicksRemaining > 0) {
            event.setSneak(true);
        }
    }

    @Override
    public void onEnable() {
        saveConfig();
    }

    @Override
    public void onDisable() {
        sneakTicksRemaining = 0;
        saveConfig();
    }

    private void loadConfig() {
        if (!Files.exists(CONFIG_PATH)) {
            saveConfig();
            return;
        }
        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            if (json.has("enabled")) {
                setEnabled(json.get("enabled").getAsBoolean());
            }
            if (json.has("ticksAmount")) {
                this.ticksAmount = json.get("ticksAmount").getAsInt();
            }
            if (json.has("onlyInAir")) {
                this.onlyInAir = json.get("onlyInAir").getAsBoolean();
            }
            if (json.has("onlyOnGround")) {
                this.onlyOnGround = json.get("onlyOnGround").getAsBoolean();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                JsonObject json = new JsonObject();
                json.addProperty("enabled", isEnabled());
                json.addProperty("ticksAmount", ticksAmount);
                json.addProperty("onlyInAir", onlyInAir);
                json.addProperty("onlyOnGround", onlyOnGround);
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
