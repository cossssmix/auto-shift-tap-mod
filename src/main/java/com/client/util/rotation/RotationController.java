package com.client.util.rotation;

import static com.client.util.MinecraftVariables.mc;

import java.util.Optional;

import org.joml.Vector2f;

import com.client.core.ClientContext;
import com.client.event.player.PlayerJumpEvent;
import com.client.event.player.SendMovementEvent;
import com.client.event.render.UpdateRenderStateEvent;
import com.google.common.eventbus.Subscribe;

import lombok.Getter;
import net.minecraft.util.math.MathHelper;

public class RotationController {
	@Getter
	private Optional<Vector2f> serverRotation;
	@Getter
	private Optional<Vector2f> originalRotation;
	@Getter
	private Optional<Vector2f> prevRotation;

	public RotationController(final ClientContext ctx) {
		this.serverRotation = Optional.empty();
		this.originalRotation = Optional.empty();
		this.prevRotation = Optional.empty();

		ctx.getEventBus().register(this);
	}

    public void set(final Vector2f rotation) {
		this.serverRotation = Optional.of(rotation);
    }

	@Subscribe
	public void onPlayerJumpPre(final PlayerJumpEvent.Pre event) {
		this.serverRotation.ifPresent(rotation -> {
			this.originalRotation = Optional.of(new Vector2f(
				mc.player.getYaw(),
				mc.player.getPitch()
			));

			mc.player.setYaw(rotation.x());
			mc.player.setPitch(rotation.y());
		});
	}

	@Subscribe
	public void onPlayerJumpPost(final PlayerJumpEvent.Post event) {
		this.originalRotation.ifPresent(rotation -> {
			mc.player.setYaw(rotation.x());
			mc.player.setPitch(rotation.y());

			this.originalRotation = Optional.empty();
		});
	}

	@Subscribe
	public void onUpdateRenderState(final UpdateRenderStateEvent event) {
		if (event.getLivingEntity() != mc.player) return;
		
		this.serverRotation.ifPresent(rotation -> {
			this.prevRotation.ifPresentOrElse(prevRotation -> {
				float pitch = event.getTickProgress() == 1.0F
					? rotation.y()
					: MathHelper.lerp(event.getTickProgress(), prevRotation.y(), rotation.y());
				
				event.getLivingEntityRenderState().pitch = pitch;

				prevRotation.set(mc.player.getYaw(), pitch);
			}, () -> {
				this.prevRotation = Optional.of(new Vector2f(
					rotation.x(), rotation.y()
				));
			});
		});
	}

	@Subscribe
    public void onSendMovementPre(final SendMovementEvent.Pre event) {
		if (mc.getCameraEntity() != mc.player) return;

		this.serverRotation.ifPresent(rotation -> {
			this.originalRotation = Optional.of(new Vector2f(
				mc.player.getYaw(),
				mc.player.getPitch()
			));

			mc.player.setYaw(rotation.x());
			mc.player.setPitch(rotation.y());

			mc.player.setHeadYaw(rotation.x());
			mc.player.setBodyYaw(rotation.x());
		});
    }

	@Subscribe
	public void onSendMovementPost(final SendMovementEvent.Post event) {
		if (mc.getCameraEntity() != mc.player) return;

		this.originalRotation.ifPresent(rotation -> {
			mc.player.setYaw(rotation.x());
			mc.player.setPitch(rotation.y());

			this.originalRotation = Optional.empty();
		});
	}

	public void reset() {
		this.serverRotation = Optional.empty();
	}
}