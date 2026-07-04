package com.client.event.render;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;

@Getter
@AllArgsConstructor
public final class UpdateRenderStateEvent extends Event {
	private LivingEntity livingEntity;
	private LivingEntityRenderState livingEntityRenderState;
	private float tickProgress;
}