package com.client.event.player;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerJumpEvent extends Event {
	public static final class Pre extends SendMovementEvent {
		public Pre() {
			super();
		}
	}

	public static final class Post extends SendMovementEvent {
		public Post() {
			super();
		}
	}
}
