package com.client.gui.screen;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;

import com.client.systems.module.ModuleRepository;
import com.client.systems.module.combat.ShiftTapModule;
import com.client.systems.module.movement.AutoSprintModule;
import com.client.systems.module.visuals.ClickGui;

import static com.client.util.MinecraftVariables.mc;

public class ClickGuiScreen extends Screen {
	private final ModuleRepository moduleRepository;
	private final int rectWidth = 240, rectHeight = 190; 

	public ClickGuiScreen(final String title, final ModuleRepository moduleRepository) {
		super(Text.of(title));
		this.moduleRepository = moduleRepository;
	}

	@Override
	public void close() {
		final ClickGui clickGuiModule = this.moduleRepository.getModule(ClickGui.class);
		if (clickGuiModule != null && clickGuiModule.isEnabled()) {
			this.moduleRepository.toggle(clickGuiModule);
		}
		super.close();
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
		super.render(context, mouseX, mouseY, deltaTicks);

		int centerX = this.width / 2;
		int centerY = this.height / 2;

		int x1 = centerX - rectWidth / 2;
		int y1 = centerY - rectHeight / 2;
		int x2 = centerX + rectWidth / 2;
		int y2 = centerY + rectHeight / 2;

		context.fill(x1, y1, x2, y2, new Color(20, 20, 22, 240).getRGB());
		context.fill(x1, y1, x2, y1 + 1, new Color(75, 75, 80).getRGB());
		context.fill(x1, y2 - 1, x2, y2, new Color(75, 75, 80).getRGB());
		context.fill(x1, y1, x1 + 1, y2, new Color(75, 75, 80).getRGB());
		context.fill(x2 - 1, y1, x2, y2, new Color(75, 75, 80).getRGB());

		String titleText = "SHIFT-TAP SETTINGS";
		context.drawText(
			mc.textRenderer,
			titleText,
			centerX - mc.textRenderer.getWidth(titleText) / 2,
			y1 + 12,
			new Color(220, 220, 230).getRGB(),
			false
		);

		final ShiftTapModule shiftTap = this.moduleRepository.getModule(ShiftTapModule.class);
		final AutoSprintModule autoSprint = this.moduleRepository.getModule(AutoSprintModule.class);
		if (shiftTap == null) return;

		int btnWidth = 200;
		int btnHeight = 20;
		int btnX = centerX - btnWidth / 2;

		// 1. Кнопка Enabled (ShiftTap)
		int btnY1 = y1 + 35;
		boolean hover1 = mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY1 && mouseY <= btnY1 + btnHeight;
		drawButton(context, btnX, btnY1, btnWidth, btnHeight, hover1);
		
		String enabledLabel = "Module: ";
		int labelW1 = mc.textRenderer.getWidth(enabledLabel);
		String statusText = shiftTap.isEnabled() ? "ENABLED" : "DISABLED";
		int statusColor = shiftTap.isEnabled() ? new Color(80, 220, 150).getRGB() : new Color(240, 100, 100).getRGB();
		
		context.drawText(mc.textRenderer, enabledLabel, btnX + 10, btnY1 + 6, new Color(200, 200, 200).getRGB(), false);
		context.drawText(mc.textRenderer, statusText, btnX + 10 + labelW1, btnY1 + 6, statusColor, false);

		// 2. Кнопка AutoSprint
		int btnY1_sprint = y1 + 65;
		boolean hoverSprint = mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY1_sprint && mouseY <= btnY1_sprint + btnHeight;
		drawButton(context, btnX, btnY1_sprint, btnWidth, btnHeight, hoverSprint);
		
		String sprintLabel = "AutoSprint: ";
		int sprintLabelW = mc.textRenderer.getWidth(sprintLabel);
		String sprintStatus = (autoSprint != null && autoSprint.isEnabled()) ? "ENABLED" : "DISABLED";
		int sprintColor = (autoSprint != null && autoSprint.isEnabled()) ? new Color(80, 220, 150).getRGB() : new Color(240, 100, 100).getRGB();
		
		context.drawText(mc.textRenderer, sprintLabel, btnX + 10, btnY1_sprint + 6, new Color(200, 200, 200).getRGB(), false);
		context.drawText(mc.textRenderer, sprintStatus, btnX + 10 + sprintLabelW, btnY1_sprint + 6, sprintColor, false);

		// 3. Кнопка Ticks Amount
		int btnY2 = y1 + 95;
		boolean hoverMinus = mouseX >= btnX && mouseX <= btnX + 40 && mouseY >= btnY2 && mouseY <= btnY2 + btnHeight;
		boolean hoverPlus = mouseX >= btnX + btnWidth - 40 && mouseX <= btnX + btnWidth && mouseY >= btnY2 && mouseY <= btnY2 + btnHeight;
		
		drawButton(context, btnX, btnY2, btnWidth, btnHeight, false);
		if (hoverMinus) {
			context.fill(btnX, btnY2, btnX + 40, btnY2 + btnHeight, new Color(255, 255, 255, 30).getRGB());
		}
		if (hoverPlus) {
			context.fill(btnX + btnWidth - 40, btnY2, btnX + btnWidth, btnY2 + btnHeight, new Color(255, 255, 255, 30).getRGB());
		}

		context.drawText(mc.textRenderer, "-", btnX + 18, btnY2 + 6, new Color(240, 100, 100).getRGB(), false);
		String ticksText = "Ticks: " + shiftTap.getTicksAmount();
		context.drawText(mc.textRenderer, ticksText, centerX - mc.textRenderer.getWidth(ticksText) / 2, btnY2 + 6, new Color(200, 200, 200).getRGB(), false);
		context.drawText(mc.textRenderer, "+", btnX + btnWidth - 22, btnY2 + 6, new Color(80, 220, 150).getRGB(), false);

		// 4. Кнопка Only in Air
		int btnY3 = y1 + 125;
		boolean hover3 = mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY3 && mouseY <= btnY3 + btnHeight;
		drawButton(context, btnX, btnY3, btnWidth, btnHeight, hover3);
		
		String airLabel = "Only In Air: ";
		int labelW3 = mc.textRenderer.getWidth(airLabel);
		String airStatus = shiftTap.isOnlyInAir() ? "ON" : "OFF";
		int airColor = shiftTap.isOnlyInAir() ? new Color(80, 220, 150).getRGB() : new Color(160, 160, 160).getRGB();
		
		context.drawText(mc.textRenderer, airLabel, btnX + 10, btnY3 + 6, new Color(200, 200, 200).getRGB(), false);
		context.drawText(mc.textRenderer, airStatus, btnX + 10 + labelW3, btnY3 + 6, airColor, false);

		// 5. Кнопка Only on Ground
		int btnY4 = y1 + 155;
		boolean hover4 = mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY4 && mouseY <= btnY4 + btnHeight;
		drawButton(context, btnX, btnY4, btnWidth, btnHeight, hover4);
		
		String groundLabel = "Only On Ground: ";
		int labelW4 = mc.textRenderer.getWidth(groundLabel);
		String groundStatus = shiftTap.isOnlyOnGround() ? "ON" : "OFF";
		int groundColor = shiftTap.isOnlyOnGround() ? new Color(80, 220, 150).getRGB() : new Color(160, 160, 160).getRGB();
		
		context.drawText(mc.textRenderer, groundLabel, btnX + 10, btnY4 + 6, new Color(200, 200, 200).getRGB(), false);
		context.drawText(mc.textRenderer, groundStatus, btnX + 10 + labelW4, btnY4 + 6, groundColor, false);
	}

	private void drawButton(DrawContext context, int x, int y, int w, int h, boolean hover) {
		int bgColor = hover ? new Color(42, 42, 48, 255).getRGB() : new Color(30, 30, 34, 255).getRGB();
		int borderColor = hover ? new Color(90, 90, 100).getRGB() : new Color(60, 60, 65).getRGB();
		
		context.fill(x, y, x + w, y + h, bgColor);
		context.fill(x, y, x + w, y + 1, borderColor);
		context.fill(x, y + h - 1, x + w, y + h, borderColor);
		context.fill(x, y, x + 1, y + h, borderColor);
		context.fill(x + w - 1, y, x + w, y + h, borderColor);
	}

	@Override
	public boolean mouseClicked(Click click, boolean doubled) {
		double mouseX = click.x();
		double mouseY = click.y();
		int button = click.button();

		int centerX = this.width / 2;
		int centerY = this.height / 2;

		int x1 = centerX - rectWidth / 2;
		int y1 = centerY - rectHeight / 2;

		final ShiftTapModule shiftTap = this.moduleRepository.getModule(ShiftTapModule.class);
		final AutoSprintModule autoSprint = this.moduleRepository.getModule(AutoSprintModule.class);
		if (shiftTap == null) return super.mouseClicked(click, doubled);

		int btnWidth = 200;
		int btnHeight = 20;
		int btnX = centerX - btnWidth / 2;

		if (button == 0) {
			// 1. Кнопка Enabled (ShiftTap)
			int btnY1 = y1 + 35;
			if (mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY1 && mouseY <= btnY1 + btnHeight) {
				this.moduleRepository.toggle(shiftTap);
				return true;
			}

			// 2. Кнопка AutoSprint
			int btnY1_sprint = y1 + 65;
			if (mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY1_sprint && mouseY <= btnY1_sprint + btnHeight) {
				if (autoSprint != null) {
					this.moduleRepository.toggle(autoSprint);
				}
				return true;
			}

			// 3. Кнопка Ticks Amount
			int btnY2 = y1 + 95;
			if (mouseX >= btnX && mouseX <= btnX + 40 && mouseY >= btnY2 && mouseY <= btnY2 + btnHeight) {
				shiftTap.setTicksAmount(shiftTap.getTicksAmount() - 1);
				return true;
			}
			if (mouseX >= btnX + btnWidth - 40 && mouseX <= btnX + btnWidth && mouseY >= btnY2 && mouseY <= btnY2 + btnHeight) {
				shiftTap.setTicksAmount(shiftTap.getTicksAmount() + 1);
				return true;
			}

			// 4. Кнопка Only in Air
			int btnY3 = y1 + 125;
			if (mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY3 && mouseY <= btnY3 + btnHeight) {
				shiftTap.setOnlyInAir(!shiftTap.isOnlyInAir());
				return true;
			}

			// 5. Кнопка Only on Ground
			int btnY4 = y1 + 155;
			if (mouseX >= btnX && mouseX <= btnX + btnWidth && mouseY >= btnY4 && mouseY <= btnY4 + btnHeight) {
				shiftTap.setOnlyOnGround(!shiftTap.isOnlyOnGround());
				return true;
			}
		}

		return super.mouseClicked(click, doubled);
	}
}
