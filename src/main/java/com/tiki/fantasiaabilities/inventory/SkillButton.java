package com.tiki.fantasiaabilities.inventory;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.util.SkillTree.Skill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SkillButton extends Button {
	private static final Minecraft mc = Minecraft.getInstance();
	private static final ResourceLocation TEXTURE = new ResourceLocation(FantasiaAbilities.MOD_ID, "textures/gui/skills_buttons.png");
	protected int normalX, normalY, hoveredX, hoveredY;
	protected int overlayX, overlayY;
	protected int overlayWidth = 16;
	protected int overlayHeight = 16;
	private final int ooX, ooY, boX, boY;
	private boolean enabled = false;
	private boolean golden = false;
	public IPressable press;
	
	public SkillButton(int x, int y, int width, int height, int overlayX, int overlayY, int normalX, int normalY, int hoveredX, int hoveredY, int overlayOffsetX, int overlayOffsetY, int blitOffsetX, int blitOffsetY, ITextComponent message, IPressable pressable, ITooltip tooltip) {
		super(x, y, width, height, message, pressable, tooltip);
		this.normalX = normalX;
		this.normalY = normalY;
		this.hoveredX = hoveredX;
		this.hoveredY = hoveredY;
		this.overlayX = overlayX;
		this.overlayY = overlayY;
		this.press = pressable;
		this.ooX = overlayOffsetX;
		this.ooY = overlayOffsetY;
		this.boX = blitOffsetX;
		this.boY = blitOffsetY;
	}
	
	public SkillButton(int x, int y, int width, int height, int overlayX, int overlayY, int overlayWidth, int overlayHeight, int normalX, int normalY, int hoveredX, int hoveredY, int overlayOffsetX, int overlayOffsetY, int blitOffsetX, int blitOffsetY, ITextComponent message, IPressable pressable, ITooltip tooltip) {
		this(x, y, width, height, overlayX, overlayY, normalX, normalY, hoveredX, hoveredY, overlayOffsetX, overlayOffsetY, blitOffsetX, blitOffsetY, message, pressable, tooltip);
		this.overlayWidth = overlayWidth;
		this.overlayHeight = overlayHeight;
	}
	
	public void setNewOverlayBlitLocation(int x, int y, int width, int height) {
		this.overlayX = x;
		this.overlayY = y;
		this.overlayWidth = width;
		this.overlayHeight = height;
	}
	
	public void setNewBlitLocation(int normX, int normY, int hovX, int hovY) {
		this.normalX = normX;
		this.normalY = normY;
		this.hoveredX = hovX;
		this.hoveredY = hovY;
	}
	
	public void setGolden(boolean golden) {
		this.golden = golden;
	}
	
	public void setLocation(int x, int y) {
		this.x += x;
		this.y += y;
		//System.out.println("X: " + this.x + ", X Start: " + SkillsScreen.getScaledXOffset() + ", X End: " + (SkillsScreen.getScaledXOffset() + SkillsScreen.getScaledWidth() - SkillsScreen.SKILLSIZE));
		//System.out.println("Y: " + this.y + ", Y Start: " + SkillsScreen.getScaledYOffset() + ", Y End: " + (SkillsScreen.getScaledYOffset() + SkillsScreen.getScaledHeight() - SkillsScreen.SKILLSIZE));
		if(this.x >= SkillsScreen.getScaledXOffset() + SkillsScreen.getScaledWidth() - SkillsScreen.SKILLSIZE || this.y >= SkillsScreen.getScaledYOffset() + SkillsScreen.getScaledHeight() - SkillsScreen.SKILLSIZE || this.x < SkillsScreen.getScaledXOffset() || this.y < SkillsScreen.getScaledYOffset()) {
			this.active = false;
		}else {
			this.active = true;
		}
	}
	
	public void setAbsoluteLocation(int x, int y) {
		this.x = x;
		this.y = y;
		if(this.x >= SkillsScreen.getScaledXOffset() + SkillsScreen.getScaledWidth() - SkillsScreen.SKILLSIZE || this.y >= SkillsScreen.getScaledYOffset() + SkillsScreen.getScaledHeight() - SkillsScreen.SKILLSIZE || this.x < SkillsScreen.getScaledXOffset() || this.y < SkillsScreen.getScaledYOffset()) {
			this.active = false;
		}else {
			this.active = true;
		}
	}
	
	public boolean toggleEnabled(Skill skill) {
		this.enabled = this.enabled == false;
		if(this.enabled) {
			this.setNewBlitLocation(94, 0, 152, 0);
			this.setNewOverlayBlitLocation(overlayX, overlayY - 96, 16, 16);
			if(mc.screen instanceof SkillsScreen) {
				((SkillsScreen)mc.screen).enableAbility(skill);
			}
		}else {
			this.setNewBlitLocation(123, 0, 152, 0);
			this.setNewOverlayBlitLocation(overlayX, overlayY + 96, 16, 16);
			if(mc.screen instanceof SkillsScreen) {
				((SkillsScreen)mc.screen).disableAbility(skill);
			}
		}
		return enabled;
	}
	
	public void setEnabled() {
		this.enabled = this.enabled == false;
	}
	
	public boolean enabled() {
		return this.enabled;
	}
	
	@Override
	public void onPress() {
		this.press.onPress(this);
	}
	
	@Override
	public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
		Minecraft mc = Minecraft.getInstance();
		mc.textureManager.bind(TEXTURE);
		if(this.active) {
			if(this.isHovered()) {
				this.blit(matrix, this.x + boX, this.y + boY, hoveredX, hoveredY, this.width, this.height);
				this.blit(matrix, this.x + ((this.width - this.overlayWidth - ooX) / 2), this.y + ((this.height - this.overlayHeight - ooY) / 2), overlayX, overlayY, overlayWidth, overlayHeight);
				this.renderToolTip(matrix, mouseX, mouseY);
			}else {
				if(this.golden) {
					this.blit(matrix, this.x + boX, this.y + boY, 31, 0, this.width, this.height);
				}else {
					this.blit(matrix, this.x + boX, this.y + boY, normalX, normalY, this.width, this.height);
				}
				this.blit(matrix, this.x + ((this.width - this.overlayWidth - ooX) / 2), this.y + ((this.height - this.overlayHeight - ooY) / 2), overlayX, overlayY, overlayWidth, overlayHeight);
			}
		}
	}
	
	@Override
	public String toString() {
		return "SkillButton: " + this.getMessage().getString();
	}
	
//	private static void BlitScale(MatrixStack matrixstack, int xStart, int xEnd, int yStart, int yEnd, int blitOffset, float minU, float maxU, float minV, float maxV) {
//		Matrix4f matrix = matrixstack.last().pose();
//		BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
//		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//	    bufferbuilder.vertex(matrix, (float)xStart, (float)yEnd, (float)blitOffset).uv(minU, maxV).endVertex();
//	    bufferbuilder.vertex(matrix, (float)xEnd, (float)yEnd, (float)blitOffset).uv(maxU, maxV).endVertex();
//	    bufferbuilder.vertex(matrix, (float)xEnd, (float)yStart, (float)blitOffset).uv(maxU, minV).endVertex();
//	    bufferbuilder.vertex(matrix, (float)xStart, (float)yStart, (float)blitOffset).uv(minU, minV).endVertex();
//	    bufferbuilder.end();
//	    RenderSystem.enableAlphaTest();
//	    WorldVertexBufferUploader.end(bufferbuilder);
//	}
}
