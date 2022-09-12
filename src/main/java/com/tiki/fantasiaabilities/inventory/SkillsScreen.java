package com.tiki.fantasiaabilities.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiki.fantasiaabilities.FantasiaAbilities;
import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.data.capabilities.IAbilities;
import com.tiki.fantasiaabilities.network.CSetExperiencePacket;
import com.tiki.fantasiaabilities.util.ExpTable;
import com.tiki.fantasiaabilities.util.SkillTree;
import com.tiki.fantasiaabilities.util.SkillTree.Skill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.*;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillsScreen extends Screen {
	public static final ResourceLocation TEXTURES = new ResourceLocation(FantasiaAbilities.MOD_ID, "textures/gui/skills.png");
	public static final ResourceLocation BACKGROUND = new ResourceLocation(FantasiaAbilities.MOD_ID, "textures/gui/skills_background.png");
	private static final Minecraft mc = Minecraft.getInstance();
	public static final int SKILLSIZE = 32;
	private final IAbilities cap;
	private PlayerEntity player;
	private int scrollX = 0;
	private int scrollY = 0;
	private final SkillButton survivalButton;
	private final SkillButton miningButton;
	private final SkillButton creationButton;
	private final SkillButton luckButton;
	private final SkillButton talentButton;
	private final SkillButton bodyButton;
	private final SkillButton diggingButton;
	private final SkillButton magicButton;
	private final SkillButton farmingButton;
	private final SkillButton agilityButton;
	private final SkillButton combatButton;
	private final SkillButton defenseButton;
	private final SkillButton choppingButton;
	private final SkillButton charismaButton;
	private final SkillButton archeryButton;
	private final SkillButton[] treeSkillButtonHeap = new SkillButton[75];
	private final List<SkillBar> skillBars = new ArrayList<SkillBar>(15);
	private boolean firstInit = false;
	
	public SkillsScreen(PlayerEntity player, IAbilities cap) {
		super(new TranslationTextComponent("container.abilities"));
		this.width = 340;
		this.height = 184;
		this.cap = cap;
		this.player = player;
		
		this.survivalButton = createButton(getNextSkillButtonXLocation(Abilities.SURVIVAL), getScaledYOffset(), 32, 42, (16 * Abilities.SURVIVAL), 64, 0, 0, 62, 0, "Survival", "No Level Bonus", null, Abilities.SURVIVAL);
		this.miningButton = createButton(getNextSkillButtonXLocation(Abilities.MINING), getScaledYOffset(), 32, 42, (16 * Abilities.MINING), 64, 0, 0, 62, 0, "Mining", "% Mining Speed", () -> cap.getMining()*2, Abilities.MINING);
		this.creationButton = createButton(getNextSkillButtonXLocation(Abilities.CREATION), getScaledYOffset(), 32, 42, (16 * Abilities.CREATION), 64, 0, 0, 62, 0, "Creation", "% Reach", () -> cap.getCreation()*2, Abilities.CREATION);
		this.luckButton = createButton(getNextSkillButtonXLocation(Abilities.LUCK), getScaledYOffset(), 32, 42, (16 * Abilities.LUCK), 64, 0, 0, 62, 0, "Luck", "% Chanec to Proc Looting/Fortune III", () -> cap.getLuck()*2, Abilities.LUCK);
		this.talentButton = createButton(getNextSkillButtonXLocation(Abilities.TALENT), getScaledYOffset(), 32, 42, (16 * Abilities.TALENT), 64, 0, 0, 62, 0, "Talent", "% EXP Gain", () -> cap.getTalent()*2, Abilities.TALENT);
		this.bodyButton = createButton(getNextSkillButtonXLocation(Abilities.BODY), getScaledYOffset(), 32, 42, (16 * Abilities.BODY), 64, 0, 0, 62, 0, "Body", " Hearts", () -> (int)(cap.getBody()/5), Abilities.BODY);
		this.diggingButton = createButton(getNextSkillButtonXLocation(Abilities.DIGGING), getScaledYOffset(), 32, 42, (16 * Abilities.DIGGING), 64, 0, 0, 62, 0, "Digging", "% Digging Speed", () -> cap.getDigging()*2, Abilities.DIGGING);
		this.magicButton = createButton(getNextSkillButtonXLocation(Abilities.MAGIC), getScaledYOffset(), 32, 42, (16 * Abilities.MAGIC), 64, 0, 0, 62, 0, "Magic", "No Level Bonus", null, Abilities.MAGIC);
		this.farmingButton = createButton(getNextSkillButtonXLocation(Abilities.FARMING), getScaledYOffset(), 32, 42, (16 * Abilities.FARMING), 64, 0, 0, 62, 0, "Farming", "No Level Bonus", null, Abilities.FARMING);
		this.agilityButton = createButton(getNextSkillButtonXLocation(Abilities.AGILITY), getScaledYOffset(), 32, 42, (16 * Abilities.AGILITY), 64, 0, 0, 62, 0, "Agility", "% Movement Speed", () -> (int)(cap.getAgility()*1.5), Abilities.AGILITY);
		this.combatButton = createButton(getNextSkillButtonXLocation(Abilities.COMBAT), getScaledYOffset(), 32, 42, (16 * Abilities.COMBAT), 64, 0, 0, 62, 0, "Combat", "% Swing Speed", () -> cap.getCombat(), Abilities.COMBAT);
		this.defenseButton = createButton(getNextSkillButtonXLocation(Abilities.DEFENSE), getScaledYOffset(), 32, 42, (16 * Abilities.DEFENSE), 64, 0, 0, 62, 0, "Defense", " Armor", () -> (cap.getDefense()/10), Abilities.DEFENSE);
		this.choppingButton = createButton(getNextSkillButtonXLocation(Abilities.CHOPPING), getScaledYOffset(), 32, 42, (16 * Abilities.CHOPPING), 64, 0, 0, 62, 0, "Chopping", "% Chopping Speed", () -> cap.getChopping()*2, Abilities.CHOPPING);
		this.charismaButton = createButton(getNextSkillButtonXLocation(Abilities.CHARISMA), getScaledYOffset(), 32, 42, (16 * Abilities.CHARISMA), 64, 0, 0, 62, 0, "Charisma", "No Level Bonus", null, Abilities.CHARISMA);
		this.archeryButton = createButton(getNextSkillButtonXLocation(Abilities.ARCHERY), getScaledYOffset(), 32, 42, (16 * Abilities.ARCHERY), 64, 0, 0, 62, 0, "Archery", "% Ranged Damage", () -> cap.getArchery(), Abilities.ARCHERY);
	}
	
	private static int getNextSkillButtonXLocation(int counter) {
		return getScaledXOffset() + (SKILLSIZE * counter) + (counter * 3);
	}
	
	@Override
	protected void init() {
		System.out.println("Scaled Width: " + getScaledWidth() + ", Scaled Height: " + getScaledHeight() + ", X Offset: " + getScaledXOffset() + ", Y Offset: " + getScaledYOffset());
		this.addButton(survivalButton);
		this.addButton(miningButton);
		this.addButton(creationButton);
		this.addButton(luckButton);
		this.addButton(talentButton);
		this.addButton(bodyButton);
		this.addButton(diggingButton);
		this.addButton(magicButton);
		this.addButton(farmingButton);
		this.addButton(agilityButton);
		this.addButton(combatButton);
		this.addButton(defenseButton);
		this.addButton(choppingButton);
		this.addButton(charismaButton);
		this.addButton(archeryButton);
		
		survivalButton.setGolden(cap.getSurvival() >= 50);
		miningButton.setGolden(cap.getMining() >= 50);
		creationButton.setGolden(cap.getCreation() >= 50);
		luckButton.setGolden(cap.getLuck() >= 50);
		talentButton.setGolden(cap.getTalent() >= 50);
		bodyButton.setGolden(cap.getBody() >= 50);
		diggingButton.setGolden(cap.getDigging() >= 50);
		magicButton.setGolden(cap.getMagic() >= 50);
		farmingButton.setGolden(cap.getFarming() >= 50);
		agilityButton.setGolden(cap.getAgility() >= 50);
		combatButton.setGolden(cap.getCombat() >= 50);
		defenseButton.setGolden(cap.getDefense() >= 50);
		choppingButton.setGolden(cap.getChopping() >= 50);
		charismaButton.setGolden(cap.getCharisma() >= 50);
		archeryButton.setGolden(cap.getArchery() >= 50);
		
		int[] arr = cap.getAbilityArray();
		boolean[] unlocked = cap.getUnlockedAbilitiesSimple();
		for(int i = 0; i < 15; i++) {
			Skill[] skills = SkillTree.getSkillTree(i).getTree();
			for(int j = 0; j < skills.length; j++) {
				Skill skill = skills[j];
				if(unlocked[(5*i)+j]) {
					this.treeSkillButtonHeap[(5*i)+j] = skill.getSkillButton();
					this.addWidget(this.treeSkillButtonHeap[(5*i)+j]);
					continue;
				}else if(skill.shouldHint(arr[i])) {
					this.treeSkillButtonHeap[(5*i)+j] = skill.getHintButton();
					break;
				}else {
					this.treeSkillButtonHeap[(5*i)+j] = skill.getQuestionButton();
					break;
				}
			}
		}
		firstInit();
		for(SkillBar bar : this.skillBars) {
			this.addButton(bar);
		}
		this.mouseDragged(0, 0, 0, 0, 0);
	}

	public void checkAndUpdateButtons(int ability) {
		int[] arr = cap.getAbilityArray();
		boolean[] unlocked = cap.getUnlockedAbilitiesSimple();
		int i = ability;
		int j = ((arr[ability]-1)/10);
		Skill[] skills = SkillTree.getSkillTree(i).getTree();
		Skill skill = skills[j];
		if(unlocked[(5*i)+j]) {
			this.treeSkillButtonHeap[(5*i)+j] = skill.getSkillButton();
			this.addWidget(this.treeSkillButtonHeap[(5*i)+j]);
			SkillBar bar = new SkillBar(getNextSkillButtonXLocation(i) + 14 + scrollX, getScaledYOffset() + (24 * (j)) + (10 * (j)) + 42 + scrollY, 181, 0);
			this.skillBars.add(bar);
			this.buttons.add(bar);
			if(arr[ability] < 50) {
				Skill skill2 = skills[j+1];
				this.treeSkillButtonHeap[(5*i)+j+1] = skill2.getQuestionButton();
				this.treeSkillButtonHeap[(5*i)+j+1].setAbsoluteLocation(getNextSkillButtonXLocation(i) + 3 + scrollX, getScaledYOffset() + (10 * ((j+1)+1)) + (24 * (j+1)) + 42 + scrollY);
				SkillBar bar2 = new SkillBar(getNextSkillButtonXLocation(i) + 14 + scrollX, getScaledYOffset() + (24 * (j+1)) + (10 * (j+1)) + 42 + scrollY, 184, 0);
				this.skillBars.add(bar2);
				this.buttons.add(bar2);
			}else {
				survivalButton.setGolden(cap.getSurvival() >= 50);
				miningButton.setGolden(cap.getMining() >= 50);
				creationButton.setGolden(cap.getCreation() >= 50);
				luckButton.setGolden(cap.getLuck() >= 50);
				talentButton.setGolden(cap.getTalent() >= 50);
				bodyButton.setGolden(cap.getBody() >= 50);
				diggingButton.setGolden(cap.getDigging() >= 50);
				magicButton.setGolden(cap.getMagic() >= 50);
				farmingButton.setGolden(cap.getFarming() >= 50);
				agilityButton.setGolden(cap.getAgility() >= 50);
				combatButton.setGolden(cap.getCombat() >= 50);
				defenseButton.setGolden(cap.getDefense() >= 50);
				choppingButton.setGolden(cap.getChopping() >= 50);
				charismaButton.setGolden(cap.getCharisma() >= 50);
				archeryButton.setGolden(cap.getArchery() >= 50);
			}
		}else if(skill.shouldHint(arr[i])) {
			this.treeSkillButtonHeap[(5*i)+j] = skill.getHintButton();
		}else {
			this.treeSkillButtonHeap[(5*i)+j] = skill.getQuestionButton();
		}
		this.treeSkillButtonHeap[(5*i)+j].setAbsoluteLocation(getNextSkillButtonXLocation(i) + 3 + scrollX, getScaledYOffset() + (10 * ((j)+1)) + (24 * (j)) + 42 + scrollY);
		this.mouseDragged(0, 0, 0, 0, 0);
	}
	
	@Override
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.textureManager.bind(TEXTURES);
		//Original aspect ratio was 256 x 156
		blit(stack, (this.width - 340) / 2, ((this.height - 184) / 2), 0, 184, 340, 184, 512, 512);
		
		super.render(stack, mouseX, mouseY, partialTicks);
		for(SkillButton button : this.treeSkillButtonHeap) {
			if(button != null) {
				button.render(stack, mouseX, mouseY, partialTicks);
			}
		}
		minecraft.textureManager.bind(TEXTURES);
		blit(stack, (this.width - 340) / 2, (this.height - 184) / 2, 0, 0, 340, 184, 512, 512);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		this.scrollX += (int)(dragX*1.2);
		this.scrollY += (int)(dragY*1.1);
		
		survivalButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		miningButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		creationButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		luckButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		talentButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		bodyButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		diggingButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		magicButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		farmingButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		agilityButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		combatButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		defenseButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		choppingButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		charismaButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		archeryButton.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		
		for(SkillButton b : this.treeSkillButtonHeap) {
			if(b != null) {
				b.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
			}
		}
		for(SkillBar bar : this.skillBars) {
			bar.setLocation((int)(dragX*1.2), (int)(dragY*1.1));
		}
		
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}
	
	private void firstInit() {
		//int[] arr = cap.getAbilityArray();
		boolean[] unlocked = cap.getUnlockedAbilitiesSimple();
		if(!firstInit) {
			System.out.println(Arrays.toString(this.treeSkillButtonHeap));
			for(int i = 0; i < 15; i++) {
				for(int j = 0; j < 5; j++) {
					if(this.treeSkillButtonHeap[(5*i)+j] != null) {
						this.treeSkillButtonHeap[(5*i)+j].setAbsoluteLocation(getNextSkillButtonXLocation(i) + 3, getScaledYOffset() + (10 * (j+1)) + (24 * (j)) + 42);
						if(unlocked[(5*i)+j]) {
							SkillBar bar = new SkillBar(getNextSkillButtonXLocation(i) + 14, getScaledYOffset() + (24 * (j)) + (10 * (j)) + 42, 181, 0);
							this.skillBars.add(bar);
						}else {
							SkillBar bar = new SkillBar(getNextSkillButtonXLocation(i) + 14, getScaledYOffset() + (24 * (j)) + (10 * (j)) + 42, 184, 0);
							this.skillBars.add(bar);
						}
					}
				}
			}
			firstInit = true;
		}
	}
	
	public void enableAbility(Skill skill) {
		System.out.println("Ability: " + skill.getAbility() + ", Skill: " + skill.getSkillLevel());
		cap.setAbilityEnabled(skill.getAbility(), skill.getSkillLevel());
		cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
	}
	
	public void disableAbility(Skill skill) {
		cap.setAbilityDisabled(skill.getAbility(), skill.getSkillLevel());
		cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
	}
	
	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	private SkillButton createButton(int x, int y, int xSize, int ySize, int overX, int overY, int normX, int normY, int hovX, int hovY, String skillName, String levelFunc, @Nullable Supplier<Number> funcAmount, int ability) {
		SkillButton button = new SkillButton(x, y, xSize, ySize, overX, overY, normX, normY, hovX, hovY, 0, 11, 0, 0, new StringTextComponent(skillName), new IPressable() {
			@Override
			public void onPress(Button button) {
				System.out.println(skillName + " Button was pressed!");
				int[] abilityArray = cap.getAbilityArray();
				if(abilityArray[ability] < 50) {
					ExpTable table = ExpTable.FIFTY;
					table = ExpTable.getExpforLevelup(abilityArray[ability]);
					CSetExperiencePacket packet = new CSetExperiencePacket(-table.getExp());
					if(player.totalExperience >= table.getExp()) {
						
						//Actually set the capability level
						//player.giveExperienceLevels(-table.getExp());
						cap.setAbility(ability, abilityArray[ability] + 1);
						if((abilityArray[ability] + 1) % 10 == 0) {
							if(ability == Abilities.DEFENSE) {
								Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), Attributes.ARMOR.getDescriptionId(), (1), Operation.ADDITION));
								player.getAttributes().addTransientAttributeModifiers(mods);
							}
							if(ability == Abilities.BODY) {
								Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), Attributes.MAX_HEALTH.getDescriptionId(), 1, Operation.ADDITION));
								player.getAttributes().addTransientAttributeModifiers(mods);
							}
							cap.setAbilityUnlocked((5*ability)+(abilityArray[ability]/10));
							cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
							if(mc.screen instanceof SkillsScreen) {
								((SkillsScreen)mc.screen).checkAndUpdateButtons(ability);
							}
						}else if((abilityArray[ability] + 1) % 5 == 0) {
							if(ability == Abilities.DEFENSE) {
								Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), Attributes.ARMOR.getDescriptionId(), (1), Operation.ADDITION));
								player.getAttributes().addTransientAttributeModifiers(mods);
							}
							if(ability == Abilities.BODY) {
								Multimap<Attribute, AttributeModifier> mods = ImmutableMultimap.of(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), Attributes.MAX_HEALTH.getDescriptionId(), 1, Operation.ADDITION));
								player.getAttributes().addTransientAttributeModifiers(mods);
							}
							cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
							if(mc.screen instanceof SkillsScreen) {
								((SkillsScreen)mc.screen).checkAndUpdateButtons(ability);
							}
						}
						
						cap.synchronise(mc.player.getUUID(), cap.getAbilityArray(), cap.getEnabledAbilities());
						Abilities.channel.sendToServer(packet);
					}
				}
			}}, new ITooltip() {
			@Override
			public void onTooltip(Button button, MatrixStack matrix, int mouseX, int mouseY) {
				List<ITextComponent> list = new ArrayList<ITextComponent>(3);
				int[] abilities = cap.getAbilityArray();
				list.add(new StringTextComponent(skillName + " lvl " + abilities[ability]));
				if(funcAmount == null) {
					list.add(new StringTextComponent(TextFormatting.RED + levelFunc));
				}else {
					list.add(new StringTextComponent(TextFormatting.BLUE + "+" + funcAmount.get() + levelFunc));
				}
				list.add(new StringTextComponent("Level Up Cost: " + ExpTable.getExpforLevelup(abilities[ability]).getExp() + " XP"));
				list.add(new StringTextComponent("Current: " + player.totalExperience + " XP"));
				list.add(new StringTextComponent("- Click to Level Up -"));
				SkillsScreen.this.renderComponentTooltip(matrix, list, mouseX, mouseY);
			}});
		return button;
	}
	
	public static int getScaledXOffset() {
//		return (int)((31.0) * 4.0);
//		return mc.getWindow().getGuiScaledWidth() - 420;
		return 60;
	}
	
	public static int getScaledYOffset() {
//		return (int)((19.0) * 4.0);
//		return mc.getWindow().getGuiScaledHeight() - 210;
		return 55;
	}
	
	public static int getScaledWidth() {
//		return (int)((58.0) * 4.0);
//		return (int)mc.getWindow().getGuiScaledWidth();
		return 340;
	}
	
	public static int getScaledHeight() {
//		return (int)((31.0) * 4.0);
//		return (int)mc.getWindow().getGuiScaledHeight();
		return 184;
	}
}
