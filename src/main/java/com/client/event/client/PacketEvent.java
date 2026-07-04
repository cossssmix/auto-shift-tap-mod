package com.client.event.client;

import com.client.event.Event;

import lombok.Getter;
import net.minecraft.network.packet.Packet;

@Getter
public class PacketEvent extends Event {
    private final Packet<?> packet;

    private PacketEvent(final Packet<?> packet) {
        this.packet = packet;
    }

    public static final class Send extends PacketEvent {
        public Send(final Packet<?> packet) {
            super(packet);
        }
    }

    public static final class Receive extends PacketEvent {
        public Receive(final Packet<?> packet) {
            super(packet);
        }
    }

    public static final class SendPost extends PacketEvent {
        public SendPost(final Packet<?> packet) {
            super(packet);
        }
    }

    public static final class ReceivePost extends PacketEvent {
        public ReceivePost(final Packet<?> packet) {
            super(packet);
        }
    }
}
