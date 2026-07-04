package com.client.event;

import java.io.Serializable;

import lombok.Getter;

@Getter
public abstract class Event implements Serializable {
	private boolean cancelled;
	
	public void cancel() {
		this.cancelled = true;
	}
}
