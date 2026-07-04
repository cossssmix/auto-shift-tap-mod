package com.client;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.client.core.ClientContext;

import lombok.Getter;

public final class Client implements ModInitializer {
	public static final String MOD_ID = "shifttap";
	@Getter
	private static ClientContext context;
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		context = ClientContext.getInstance();

		LOGGER.info("Modules loaded: {}", context.getModuleRepository().getModules().size());
		LOGGER.info("Client initialized");
	}
}
