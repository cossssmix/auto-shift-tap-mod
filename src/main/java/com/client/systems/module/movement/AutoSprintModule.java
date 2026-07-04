package com.client.systems.module.movement;

import com.client.event.player.PlayerTickEvent;
import com.client.systems.module.AbstractModule;
import com.client.systems.module.Category;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.glfw.GLFW;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.client.util.MinecraftVariables.mc;

public final class AutoSprintModule extends AbstractModule {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("autosprint.json");

    public AutoSprintModule() {
        super("autosprint", "Автоматически зажимает клавишу спринта", Category.Movement);
        setKey(GLFW.GLFW_KEY_G);
        loadConfig();
    }

    @Subscribe
    public void onPlayerTick(final PlayerTickEvent event) {
        if (isEnabled() && mc.player != null) {
            mc.options.sprintKey.setPressed(true);
        }
    }

    @Override
    public void onEnable() {
        saveConfig();
    }

    @Override
    public void onDisable() {
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
                GSON.toJson(json, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
