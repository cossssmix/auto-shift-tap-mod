package com.client.mixin.client;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.client.Client;
import com.client.event.client.PacketEvent;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
    @Inject(method = "handlePacket", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacketPre(
		Packet<T> packet,
		PacketListener listener,
		CallbackInfo info
	) {
		var packets = (packet instanceof BundleS2CPacket bundle) ? bundle.getPackets() : List.of(packet);

		for (final Packet<?> p : packets) {
			final PacketEvent.Receive packetReceiveEvent = new PacketEvent.Receive(p);

			Client.getContext().getEventBus().post(packetReceiveEvent);

			if (packetReceiveEvent.isCancelled()) {
				info.cancel();
				break;
			}
		}
    }

    @Inject(method = "handlePacket", at = @At("TAIL"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacketPost(
		Packet<T> packet,
		PacketListener listener,
		CallbackInfo info
	) {
		var packets = (packet instanceof BundleS2CPacket bundle) ? bundle.getPackets() : List.of(packet);

		for (final Packet<?> p : packets) {
			final PacketEvent.ReceivePost packetReceivePostEvent = new PacketEvent.ReceivePost(p);

			Client.getContext().getEventBus().post(packetReceivePostEvent);

			if (packetReceivePostEvent.isCancelled()) {
				info.cancel();
				break;
			}
		}
    }

    @Inject(method = "send", at = @At("HEAD"), cancellable = true)
    private void onSendPacketPre(Packet<?> packet, CallbackInfo info) {;
        final PacketEvent.Send packetSendEvent = new PacketEvent.Send(packet);

        Client.getContext().getEventBus().post(packetSendEvent);

        if (packetSendEvent.isCancelled()) {
			info.cancel();
		}
    }

    @Inject(method = "send", at = @At("TAIL"), cancellable = true)
    private void onSendPacketPost(Packet<?> packet, CallbackInfo info) {
        final PacketEvent.SendPost packetSendPostEvent = new PacketEvent.SendPost(packet);

        Client.getContext().getEventBus().post(packetSendPostEvent);

        if (packetSendPostEvent.isCancelled()) {
			info.cancel();
		}
    }
}
