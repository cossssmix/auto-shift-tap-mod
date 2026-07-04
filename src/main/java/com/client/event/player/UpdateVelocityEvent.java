package com.client.event.player;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.Vec3d;

@AllArgsConstructor
public final class UpdateVelocityEvent extends Event {
	@Getter
	private Vec3d movementInput;
	@Getter
	private float speed, yaw;
	@Getter @Setter
	private Vec3d velocity;
}
