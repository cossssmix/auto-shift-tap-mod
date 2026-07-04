package com.client.util.player;

import static com.client.util.MinecraftVariables.mc;

import com.client.event.client.KeyboardInputEvent;
import com.client.event.player.UpdateVelocityEvent;
import com.client.mixin.accessor.EntityAccessor;
import com.client.util.rotation.RotationController;

import net.minecraft.util.math.MathHelper;

public class MovementController {
	private RotationController rotationController;

	public MovementController(final RotationController rotationController) {
		this.rotationController = rotationController;
	}

	public double direction(final float rotationYaw, final double moveForward, final double moveStrafing) {
		float yaw = rotationYaw;

		if (moveForward < 0.0) {
			yaw += 180.0F;
		}

		final float forwardMultiplier =
				moveForward < 0.0 ? -0.5F :
				moveForward > 0.0 ?  0.5F :
									1.0F;

		if (moveStrafing > 0.0) {
			yaw -= 90.0F * forwardMultiplier;
		} else if (moveStrafing < 0.0) {
			yaw += 90.0F * forwardMultiplier;
		}

		return Math.toRadians(yaw);
	}

	public void fixUpdateVelocity(final UpdateVelocityEvent event) {
		this.rotationController.getServerRotation().ifPresent(rotation -> {
			event.setVelocity(EntityAccessor.movementInputToVelocity(
				event.getMovementInput(),
				event.getSpeed(),
				rotation.x()
			));
		});
	}

    public void fixKeyboardInput(final KeyboardInputEvent event) {
		this.rotationController.getServerRotation().ifPresent(rotation -> {
			final float forward = event.getMovementForward();
			final float strafe = event.getMovementStrafe();
	
			final double angle = MathHelper.wrapDegrees(Math.toDegrees(this.direction(
					mc.player.isGliding() ? rotation.x() : mc.player.getYaw(),
					forward,
					strafe
			)));
	
			if (forward == 0 && strafe == 0) return;
	
			float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;
	
			for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
				for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
					if (predictedStrafe == 0 && predictedForward == 0) continue;
	
					final double predictedAngle = MathHelper.wrapDegrees(Math.toDegrees(direction(rotation.x(), predictedForward, predictedStrafe)));
					final double difference = Math.abs(angle - predictedAngle);
	
					if (difference < closestDifference) {
						closestDifference = (float) difference;
						closestForward = predictedForward;
						closestStrafe = predictedStrafe;
					}
				}
			}
	
			event.setMovementForward(closestForward);
			event.setMovementStrafe(closestStrafe);
		});
		
    }
}
