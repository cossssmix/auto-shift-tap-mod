package com.client.event.client;

import com.client.event.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class KeyboardInputEvent extends Event {
    private float movementForward;
    private float movementStrafe;
    private boolean jump;
    private boolean sneak;
    private boolean sprint;
}