package com.client.util.rotation;

import lombok.Getter;

public enum RotationPriority {
	LOW(0),
	NORMAL(1),
	HIGH(2),
	CRITICAL(3);
	
	@Getter
	private final int level;

	RotationPriority(final int level) {
		this.level = level;
	}
}