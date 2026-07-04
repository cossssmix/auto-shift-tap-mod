package com.client.systems.module;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AbstractModule {
	private final String name, description;
	@Setter
	private int key;
	private final Category category;
	@Setter
	private boolean enabled;

	public AbstractModule(final String name, final String description, final Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
	}

	public void onEnable() {}
	public void onDisable() {}
}
