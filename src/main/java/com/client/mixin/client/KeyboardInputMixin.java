package com.client.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.client.KeyboardInputEvent;

import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec2f;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {

    @Shadow @Final
    private GameOptions settings;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        boolean forwardKey = settings.forwardKey.isPressed();
        boolean backwardKey = settings.backKey.isPressed();
        boolean leftKey = settings.leftKey.isPressed();
        boolean rightKey = settings.rightKey.isPressed();
        boolean jumpKey = settings.jumpKey.isPressed();
        boolean sneakKey = settings.sneakKey.isPressed();
        boolean sprintKey = settings.sprintKey.isPressed();

        float moveForward = calculateMovement(forwardKey, backwardKey);
        float moveSideways = calculateMovement(leftKey, rightKey);

        final KeyboardInputEvent event = new KeyboardInputEvent(
            moveForward, moveSideways, jumpKey, sneakKey, sprintKey
        );

        Client.getContext().getEventBus().post(event);

		super.movementVector = new Vec2f(
			event.getMovementStrafe(),
			event.getMovementForward()
		).normalize();

        super.playerInput = new PlayerInput(
            event.getMovementForward() > 0,
            event.getMovementForward() < 0,
            event.getMovementStrafe() > 0,
            event.getMovementStrafe() < 0,
            event.isJump(),
            event.isSneak(),
            event.isSprint()
        );

        ci.cancel();
    }

    private float calculateMovement(boolean positive, boolean negative) {
        return positive == negative ? 0F : (positive ? 1F : -1F);
    }
}
