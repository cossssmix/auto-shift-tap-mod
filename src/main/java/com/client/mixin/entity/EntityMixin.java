package com.client.mixin.entity;

import static com.client.util.MinecraftVariables.mc;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import com.client.Client;
import com.client.event.entity.PushAwayEvent;
import com.client.event.player.UpdateVelocityEvent;
import com.client.mixin.accessor.EntityAccessor;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
	private void onUpdateVelocity(float speed, Vec3d movementInput, CallbackInfo ci) {
		if ((Object) this == mc.player) {
			ci.cancel();
			
			final UpdateVelocityEvent updateVelocityEvent = new UpdateVelocityEvent(
				movementInput,
				speed,
				mc.player.getYaw(),
				EntityAccessor.movementInputToVelocity(movementInput, speed, mc.player.getYaw())
			);

			Client.getContext().getEventBus().post(updateVelocityEvent);

			mc.player.setVelocity(
				mc.player.getVelocity().add(updateVelocityEvent.getVelocity())
			);
		}
	}

	@ModifyArgs(
		method = "pushAwayFrom",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"
		)
	)
	private void onPushAwayFrom(Args args) {
		final PushAwayEvent pushAwayEvent = new PushAwayEvent(
			(Entity) (Object) this,
			new Vec3d(
				args.get(0),
				args.get(1),
				args.get(2)
			)
		);

		Client.getContext().getEventBus().post(pushAwayEvent);

		Vec3d velocity = pushAwayEvent.getVelocity();

		args.set(0, velocity.getX());
		args.set(1, velocity.getY());
		args.set(2, velocity.getZ());
	}
}
