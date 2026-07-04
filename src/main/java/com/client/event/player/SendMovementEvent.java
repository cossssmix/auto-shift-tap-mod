package com.client.event.player;

import com.client.event.Event;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendMovementEvent extends Event {
	private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;

    public SendMovementEvent(
		final double x,
		final double y,
		final double z,
		final float yaw,
		final float pitch,
		final boolean onGround
	) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

	public SendMovementEvent() {}

	public static final class Pre extends SendMovementEvent {
		public Pre(
			final double x,
			final double y,
			final double z,
			final float yaw,
			final float pitch,
			final boolean onGround
		) {
			super(x, y, z, yaw, pitch, onGround);
		}
	}

	public static final class Post extends SendMovementEvent {
		public Post() {
			super();
		}
	}
}
