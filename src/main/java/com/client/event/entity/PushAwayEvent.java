package com.client.event.entity;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@Setter
@Getter
@AllArgsConstructor
public final class PushAwayEvent extends Event {
	private Entity entity;
	private Vec3d velocity;
}
