package com.tiki.fantasiaabilities.util;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tiki.fantasiaabilities.data.capabilities.Abilities;
import com.tiki.fantasiaabilities.inventory.SkillButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.gui.widget.button.Button.ITooltip;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SkillTree {
	private static final Minecraft mc = Minecraft.getInstance();
	
	public static final SkillTree MINING = new SkillTree(
			new Skill("Acquire Stoneware", "Mined blocks are immediately added to inventory", "You notice that blocks aren't dropping anymore. Where did they go?", 10, 16 * Abilities.MINING, 64 + (16) + 96, Abilities.MINING, 0), 
			new Skill("Ore Clusters", "20% chance of receiving doubled ores when mining", "A well-trained eye can see additional yield from the earth.", 20, 16, 64 + (16*2) + 96, Abilities.MINING, 1), 
			new Skill("Miner's Bounty", "5% chance of getting gold/iron nuggets from mining stones", "You could swear you just saw something shinning inside the stone.", 30, 16 * Abilities.MINING, 64 + (16*3) + 96, Abilities.MINING, 2), 
			new Skill("Strip Mining", "Regular pickaxes can mine 3x3 (not when crouching)", "This must be how the abandoned mineshafts were carved out.", 40, 16 * Abilities.MINING, 64 + (16*4) + 96, Abilities.MINING, 3), 
			new Skill("Vein Miner", "Vein-mining (ores only) and pickaxes no longer get damaged when used", "You learn more about how resources naturally form.", 50, 16 * Abilities.MINING, 64 + (16*5) + 96, Abilities.MINING, 4));
	
	public static final SkillTree DIGGING = new SkillTree(
			new Skill("Acquire Dirtware", "Dug blocks are immadiately added to inventory", "There's some sand in your pocket. How did it get there?", 10, 16 * Abilities.DIGGING, 64 + (16) + 96, Abilities.DIGGING, 0), 
			new Skill("Archeologist", "10% chance of getting a random mob drop when digging soft blocks", "Some things take a while to decompose.", 20, 16 * Abilities.DIGGING, 64 + (16*2) + 96, Abilities.DIGGING, 1), 
			new Skill("Digger's Bounty", "3% chance of getting gold/iron nuggets from digging soft blocks", "People used to find riches by sifting dirt and sand.", 30, 16 * Abilities.DIGGING, 64 + (16*3) + 96, Abilities.DIGGING, 2), 
			new Skill("Flattened Earth", "Regular shovels can dig 3x3 (not when crouching)", "Terraforming the earth itself.", 40, 16 * Abilities.DIGGING, 64 + (16*4) + 96, Abilities.DIGGING, 3), 
			new Skill("Hot Spade", "Auto-Smelt and shovels no longer get damaged when used", "There's  heat emanating from the end of your shovel. ", 50, 16 * Abilities.DIGGING, 64 + (16*5) + 96, Abilities.DIGGING, 4));
	
	public static final SkillTree CHOPPING = new SkillTree(
			new Skill("Guillotine", "20% chance of getting a mob's head drop from an axe kill", "You start to notice how easy it would be to go for the head.", 10, 16 * Abilities.CHOPPING, 64 + (16) + 96, Abilities.CHOPPING, 0), 
			new Skill("Chop & Drop", "Axes deal 20% more damage", "More than a humble tool, your axe really packs a punch.", 20, 16 * Abilities.CHOPPING, 64 + (16*2) + 96, Abilities.CHOPPING, 1), 
			new Skill("Lumberjack", "Breaking wooden blocks has a 100% chance of not damaging your axe", "Your axe starts to feel like it could cut through wood like it's butter.", 30, 16 * Abilities.CHOPPING, 64 + (16*3) + 96, Abilities.CHOPPING, 2), 
			new Skill("Berserker", "Killing a mob with an axe gives you 5 seconds of Strength II", "The thrill of victory remains with you for a while", 40, 16 * Abilities.CHOPPING, 64 + (16*4) + 96, Abilities.CHOPPING, 3), 
			new Skill("Deadly Force", "25% chance of insta-killing mobs with 20% health or less (except bosses)", "Axes can pin-point your enemies weakest points.", 50, 16 * Abilities.CHOPPING, 64 + (16*5) + 96, Abilities.CHOPPING, 4));
	
	public static final SkillTree COMBAT = new SkillTree(
			new Skill("Weak Spots", "15% chance of dealing a critical hit without the need to jump", "A skilled swordsman can deliver a punishing blow in an elegant way.", 10, 16 * Abilities.COMBAT, 64 + (16) + 96, Abilities.COMBAT, 0), 
			new Skill("Slice & Dice", "Swords deal 20% more damage", "You begin to understand more about your blade and how to use it.", 20, 16 * Abilities.COMBAT, 64 + (16*2) + 96, Abilities.COMBAT, 1), 
			new Skill("Martyrdom", "Dying spawns 2 Splash Potions of Harming (Instant Damage II) at your feet", "Your death will bring pain to those around you.", 30, 16 * Abilities.COMBAT, 64 + (16*3) + 96, Abilities.COMBAT, 2), 
			new Skill("Executioner", "Critical hits with an axe inflict the target with Weakness and Slowness for 5s", "Your attacks with axes can be truly devastating.", 40, 16 * Abilities.COMBAT, 64 + (16*4) + 96, Abilities.COMBAT, 3), 
			new Skill("Excellent Strike", "10% chance of your swing dealing triple the amount of damage", "Even the most skilled champions get lucky every once in a while.", 50, 16 * Abilities.COMBAT, 64 + (16*5) + 96, Abilities.COMBAT, 4));
	
	public static final SkillTree DEFENSE = new SkillTree(
			new Skill("Steadfast", "Immune to knockback", "You feel immovable. Like your feet are rooted to the ground. ", 10, 16 * Abilities.DEFENSE, 64 + (16) + 96, Abilities.DEFENSE, 0), 
			new Skill("Power Stance", "Gain Resistance when holding crouch", "Lowering your center of gravity improves your stance.", 20, 16 * Abilities.DEFENSE, 64 + (16*2) + 96, Abilities.DEFENSE, 1), 
			new Skill("Shield bash", "20% chance of hurting attacker when blocking melee strike with your shield", "Defense is the best offense.", 30, 16 * Abilities.DEFENSE, 64 + (16*3) + 96, Abilities.DEFENSE, 2), 
			new Skill("Unbreakable", "Shields and armor will never go below 1 durability but will break if you die", "Your equipment is as durable as you are.", 40, 16 * Abilities.DEFENSE, 64 + (16*4) + 96, Abilities.DEFENSE, 3), 
			new Skill("Max Immunity", "Immune to all negative status effects", "Facing the struggles of this world has made you numb to them.", 50, 16 * Abilities.DEFENSE, 64 + (16*5) + 96, Abilities.DEFENSE, 4));
	
	public static final SkillTree ARCHERY = new SkillTree(
			new Skill("Skilled Shot", "33.3% chance of not consuming an arrow when firing a bow or crossbow", "Did you just fire that arrow?", 10, 16 * Abilities.ARCHERY, 64 + (16) + 96, Abilities.ARCHERY, 0), 
			new Skill("Quickdraw", "Bows and crossbows pull back 1.5 times faster than usual", "Your arms grow accostumed to the tension.", 20, 16 * Abilities.ARCHERY, 64 + (16*2) + 96, Abilities.ARCHERY, 1), 
			new Skill("Explosive Shot", "10% chance to have arrow explode on impact (not breaking blocks)", "Sometimes you will add too much power to your shots.", 30, 16 * Abilities.ARCHERY, 64 + (16*3) + 96, Abilities.ARCHERY, 2), 
			new Skill("Spreadshot", "All bows and crossbows shoot 3 arrows when used", "You feel like you could triple your damage output.", 40, 16 * Abilities.ARCHERY, 64 + (16*4) + 96, Abilities.ARCHERY, 3), 
			new Skill("Sniper Shot", "Shooting when crouching will shoot a single arrow that deals triple damage", "You are certain that one perfect shot could be lethal.", 50, 16 * Abilities.ARCHERY, 64 + (16*5) + 96, Abilities.ARCHERY, 4));
	
	public static final SkillTree AGILITY = new SkillTree(
			new Skill("Runner", "Can sprint regardless of hunger bar status", "Nothing can stop you from running. Not even yourself.", 10, 16 * Abilities.AGILITY, 64 + (16) + 96, Abilities.AGILITY, 0), 
			new Skill("Powerful Grip", "Can climbs walls like a spider but slowly", "Studying spiders has given you a hint on how to immitate them.", 20, 16 * Abilities.AGILITY, 64 + (16*2) + 96, Abilities.AGILITY, 1), 
			new Skill("Perfect Landing", "Immune to fall damage", "Your legs start getting used to the impact from landing.", 30, 16 * Abilities.AGILITY, 64 + (16*3) + 96, Abilities.AGILITY, 2), 
			new Skill("Double Jump", "Can perform a 2nd jump before touching the ground again", "You feel like you could kick the air.", 40, 16 * Abilities.AGILITY, 64 + (16*4) + 96, Abilities.AGILITY, 3), 
			new Skill("Dodge", "20% chance of ignoring damage received", "Your reflexes have been honed to perfection. Can your body keep up?", 50, 16 * Abilities.AGILITY, 64 + (16*5) + 96, Abilities.AGILITY, 4));
	
	public static final SkillTree MAGIC = new SkillTree(
			new Skill("Magical Tether", "Surrounding Endermen cannot teleport away", "Your mere presence is magnetic to a certain mob.", 10, 16 * Abilities.MAGIC, 64 + (16) + 96, Abilities.MAGIC, 0), 
			new Skill("Advanced Splash", "Splash potions  have are twice as efficient", "Brewing potions just became your favorite hobby.", 20, 16 * Abilities.MAGIC, 64 + (16*2) + 96, Abilities.MAGIC, 1), 
			new Skill("Second Splash", "33.3% chance of not consuming a splash potion when thrown", "Did you just throw that potion?", 30, 16 * Abilities.MAGIC, 64 + (16*3) + 96, Abilities.MAGIC, 2), 
			new Skill("Enchanting Drops", "20% of killed mobs dropping a random enchanted book or enchanted g. apple", "Your enermies turn into knowledge and strength for you.", 40, 16 * Abilities.MAGIC, 64 + (16*4) + 96, Abilities.MAGIC, 3), 
			new Skill("Magical Crafting", "Diamond tools, weapons, and armor you craft will be automatically enchanted", "There's some magical potential hidden inside diamonds.", 50, 16 * Abilities.MAGIC, 64 + (16*5) + 96, Abilities.MAGIC, 4));
	
	public static final SkillTree FARMING = new SkillTree(
			new Skill("Seed Finder", "Breaking grass can drop beetroot, pumpkin, melon, or wheat seeds", "Often overlooked, grass can contain all sorts of things.", 10, 16 * Abilities.FARMING, 64 + (16) + 96, Abilities.FARMING, 0), 
			new Skill("Green Thumb", "Standing near crops or saplings will sometimes trigger a bone meal effect", "Plants need love and care. Luckily you have both.", 20, 16 * Abilities.FARMING, 64 + (16*2) + 96, Abilities.FARMING, 1), 
			new Skill("Harmless Harvest", "Harvesting crops will yield twice the regular amount", "A sustainable food source means you'll never be hungry.", 30, 16 * Abilities.FARMING, 64 + (16*3) + 96, Abilities.FARMING, 2), 
			new Skill("Flower Power", "Player gets Strength I, Speed I, and Regeneration I when holding a flower", "Some flowers have the ability to increase your strength.", 40, 16 * Abilities.FARMING, 64 + (16*4) + 96, Abilities.FARMING, 3), 
			new Skill("Strong Diet", "Eating non-meats grants Strength II for 30 seconds", "Winners don't eat meat.", 50, 16 * Abilities.FARMING, 64 + (16*5) + 96, Abilities.FARMING, 4));
	
	public static final SkillTree CHARISMA = new SkillTree(
			new Skill("Instant Loyalty", "Mobs will be automatically tamed on the first attempt", "Animals seem to have a natural attraction to you.", 10, 16 * Abilities.CHARISMA, 64 + (16) + 96, Abilities.CHARISMA, 0), 
			new Skill("Love Inspiration", "Crouching near animals will trigger animal's \"love mode\" status (if possible)", "Love is in the air.", 20, 16 * Abilities.CHARISMA, 64 + (16*2) + 96, Abilities.CHARISMA, 1), 
			new Skill("Trading Expertise", "Trading with villagers will give them 3 times as much experience than normal", "You slowly learn how to share your knowledge with others.", 30, 16 * Abilities.CHARISMA, 64 + (16*3) + 96, Abilities.CHARISMA, 2), 
			new Skill("Heroic Status", "Permanent Hero of the Village effect", "Your reputation precedes you.", 40, 16 * Abilities.CHARISMA, 64 + (16*4) + 96, Abilities.CHARISMA, 3), 
			new Skill("Everyone's Favorite", "All mobs are neutral to the player (including bosses)", "Do not harm and no harm will come your way.", 50, 16 * Abilities.CHARISMA, 64 + (16*5) + 96, Abilities.CHARISMA, 4));
	
	public static final SkillTree TALENT = new SkillTree(
			new Skill("Delicate Usage", "10% chance of weapons and tools not consuming durability", "The more you use your tools and weapons the more you care for them.", 10, 16 * Abilities.TALENT, 64 + (16) + 96, Abilities.TALENT, 0), 
			new Skill("Stop, Drop, and Roll", "Cannot be caught on fire for more than 3 seconds", "Fire can't harm you if you know how to deal with it.", 20, 16 * Abilities.TALENT, 64 + (16*2) + 96, Abilities.TALENT, 0), 
			new Skill("Beast Rider", "Your mounts cannot be killed while you're riding them", "You know how to uncover your mount's hidden potential.", 30, 16 * Abilities.TALENT, 64 + (16*3) + 96, Abilities.TALENT, 2), 
			new Skill("Disaster Artist", "Creepers will not explode but instead just disappear and drop gunpowder", "You are now capable of defusing any situation.", 40, 16 * Abilities.TALENT, 64 + (16*4) + 96, Abilities.TALENT, 3), 
			new Skill("Natural Instincts", "Gains Strength II, Speed II, and Fire Resistance when HP is 20% or lower", "When death is knocking at your door you kick that door down.", 50, 16 * Abilities.TALENT, 64 + (16*5) + 96, Abilities.TALENT, 4));
	
	public static final SkillTree LUCK = new SkillTree(
			new Skill("Pickpocket", "10% chance that mobs will drop 1 emerald when killed", "Where were they hiding this? Do they even have pockets?", 10, 16 * Abilities.LUCK, 64 + (16) + 96, Abilities.LUCK, 0), 
			new Skill("Enchanter's Grace", "20% chance that enchanting won't consume experience", "Lady Luck and Lady Magic seem to be on your side.", 20, 16 * Abilities.LUCK, 64 + (16*2) + 96, Abilities.LUCK, 1), 
			new Skill("Soulbound All", "100% chance to keep all of your inventory and EXP upon death", "\"You can't take it with you\" they said.", 30, 16 * Abilities.LUCK, 64 + (16*3) + 96, Abilities.LUCK, 2), 
			new Skill("Death's Gable", "50% chance of applying Totem of Undying on lethal damage", "You weel totemic powers emanating from within you.", 40, 16 * Abilities.LUCK, 64 + (16*4) + 96, Abilities.LUCK, 3), 
			new Skill("Sudden Treasure", "5% of getting a medley of materials and resources from any ore", "Mining ores just became a whole lot more fun for you.", 50, 16 * Abilities.LUCK, 64 + (16*5) + 96, Abilities.LUCK, 4));
	
	public static final SkillTree CREATION = new SkillTree(
			new Skill("Gentle Worker", "Anvils no longer get damaged when you use them", "A good blacksmith takes good care of their tools.", 10, 16 * Abilities.CREATION, 64 + (16) + 96, Abilities.CREATION, 0), 
			new Skill("Architect's Dream", "Double crafting for stairs, ladders, glass panes, bottles, doors, trapdoors, and slabs", "You learn to use every part of the materials you're building with.", 20, 16 * Abilities.CREATION, 64 + (16*2) + 96, Abilities.CREATION, 1), 
			new Skill("Handcrafted Discount", "Using anvils and smithing tables give you XP instead of costing XP", "Repairing and reinforcing your equipment is a rewarding experience.", 30, 16 * Abilities.CREATION, 64 + (16*3) + 96, Abilities.CREATION, 2), 
			new Skill("Skilled Hands", "Repairing equipment once will fully repair it", "Fixing things is what a good craftsman does.", 40, 16 * Abilities.CREATION, 64 + (16*4) + 96, Abilities.CREATION, 3), 
			new Skill("Crafting Genius", "Crafting iron/gold/diamond tools, weapons, and armor gives you experience", "The more you make things the more you learn.", 50, 16 * Abilities.CREATION, 64 + (16*5) + 96, Abilities.CREATION, 4));
	
	public static final SkillTree SURVIVAL = new SkillTree(
			new Skill("Quick Reel", "Fishing rod reels back automatically and give you double loot", "Fishermen have shared some secrets with you.", 10, 16 * Abilities.SURVIVAL, 64 + (16) + 96, Abilities.SURVIVAL, 0), 
			new Skill("Foraging", "Breaking leaves has 20% chance of dropping apple or sweet berries", "Nature provides food if you know where to look for it.", 20, 16 * Abilities.SURVIVAL, 64 + (16*2) + 96, Abilities.SURVIVAL, 1), 
			new Skill("Hunting Expert", "Double loot drops when killing animals", "Butchers have taught you how to take full advantage of a carcass.", 30, 16 * Abilities.SURVIVAL, 64 + (16*3) + 96, Abilities.SURVIVAL, 2), 
			new Skill("Well Rested", "Sleeping fully heals you and removes all negative effects / No Phantom spawn", "A good night's rest is what keeps you going.", 40, 16 * Abilities.SURVIVAL, 64 + (16*4) + 96, Abilities.SURVIVAL, 3), 
			new Skill("Callus", "Immune to cactus, berry bushes, magma blocks, and the Thorns enchant", "Your skin feels thicker.", 50, 16 * Abilities.SURVIVAL, 64 + (16*5) + 96, Abilities.SURVIVAL, 4));
	
	public static final SkillTree BODY = new SkillTree(
			new Skill("Appetite", "Drinking/eating is done twice as fast", "Your body hungers for nutrition.", 10, 16 * Abilities.BODY, 64 + (16) + 96, Abilities.BODY, 0), 
			new Skill("Steel Lungs", "Water breathing", "Your lung capacity feels nearly endless.", 20, 16 * Abilities.BODY, 64 + (16*2) + 96, Abilities.BODY, 1), 
			new Skill("Block Breaker", "Bare hands act as iron sword/shovel/pick/axe", "Your hands have become tools.", 30, 16 * Abilities.BODY, 64 + (16*3) + 96, Abilities.BODY, 2), 
			new Skill("Nutrition", "Having a full hunger bar gives you Strength, Speed, and Regeneration", "A full stomach is what every warrior needs.", 40, 16 * Abilities.BODY, 64 + (16*4) + 96, Abilities.BODY, 3), 
			new Skill("Semi-Immortal", "Fire, Wither, and explosion damage can't lower your health below half a heart", "You have mastered control over your own body. You feel invincible.", 50, 16 * Abilities.BODY, 64 + (16*5) + 96, Abilities.BODY, 4));

	private final Skill[] tree;
	
	SkillTree(Skill skill1, Skill skill2, Skill skill3, Skill skill4, Skill skill5) {
		tree = new Skill[] {skill1, skill2, skill3, skill4, skill5};
	}
	
	public Skill getLevel10() {
		return tree[0];
	}
	
	public Skill getLevel20() {
		return tree[1];
	}
	
	public Skill getLevel30() {
		return tree[2];
	}
	
	public Skill getLevel40() {
		return tree[3];
	}
	
	public Skill getLevel50() {
		return tree[4];
	}
	
	public Skill[] getTree() {
		return this.tree;
	}
	
	public static SkillTree getSkillTree(int ability) {
		switch(ability) {
		case 0: return MINING;
		case 1: return CHOPPING;
		case 2: return DIGGING;
		case 3: return COMBAT;
		case 4: return DEFENSE;
		case 5: return ARCHERY;
		case 6: return AGILITY;
		case 7: return MAGIC;
		case 8: return SURVIVAL;
		case 9: return FARMING;
		case 10: return CHARISMA;
		case 11: return BODY;
		case 12: return LUCK;
		case 13: return CREATION;
		case 14: return TALENT;
		default: return MINING;
		}
	}
	
	public static class Skill {
		private final int ability;
		private final int skill;
		private final SkillButton questionButton;
		private final SkillButton hintButton;
		private final String name;
		private final String desc;
		private final String hint;
		private final int levelUnlocked;
		private boolean enabled;
		private final SkillButton button;
		
		public Skill(String name, String desc, String hint, int levelUnlocked, int x, int y, int ability, int skill) {
			this.name = name;
			this.desc = desc;
			this.hint = hint;
			this.levelUnlocked = levelUnlocked;
			this.ability = ability;
			this.skill = skill;
			this.enabled = false;
			this.questionButton = new SkillButton(0, 0, 24, 24, 246, 63, 10, 12, 232, 0, 232, 0, 0, 0, 0, 0, new StringTextComponent(name + " question"), new IPressable() {
				@Override
				public void onPress(Button button) {}
			}, new ITooltip() {
				@Override
				public void onTooltip(Button button, MatrixStack matrix, int mouseX, int mouseY) {
					List<ITextComponent> list = new ArrayList<ITextComponent>(2);
					list.add(new StringTextComponent("Level up this attribute to learn more about the next skill you'll unlock."));
					mc.screen.renderComponentTooltip(matrix, list, mouseX, mouseY);
				}
			});
			this.hintButton = new SkillButton(0, 0, 24, 24, 250, 51, 6, 12, 232, 0, 232, 0, 0, 0, 0, 0, new StringTextComponent(name + " hint"), new IPressable() {
				@Override
				public void onPress(Button button) {}
			}, new ITooltip() {
				@Override
				public void onTooltip(Button button, MatrixStack matrix, int mouseX, int mouseY) {
					List<ITextComponent> list = new ArrayList<ITextComponent>(2);
					list.add(new StringTextComponent(TextFormatting.BOLD + "" + TextFormatting.ITALIC + Skill.this.name));
					list.add(new StringTextComponent(Skill.this.hint));
					list.add(new StringTextComponent("Level up this attribute to learn more about the next skill you'll unlock."));
					mc.screen.renderComponentTooltip(matrix, list, mouseX, mouseY);
				}
			});
			this.button = new SkillButton(0, 0, 29, 24, x, y, 123, 0, 152, 0, 5, 0, -5, 0, new StringTextComponent(name), new IPressable() {
				@Override
				public void onPress(Button button) {
					((SkillButton)button).toggleEnabled(Skill.this);
				}
			}, new ITooltip() {
				@Override
				public void onTooltip(Button button, MatrixStack matrix, int mouseX, int mouseY) {
					List<ITextComponent> list = new ArrayList<ITextComponent>(2);
					list.add(new StringTextComponent(Skill.this.name));
					list.add(new StringTextComponent(Skill.this.desc));
					mc.screen.renderComponentTooltip(matrix, list, mouseX, mouseY);
				}
			});
		}
		
		public String getName() {
			return this.name;
		}
		
		public String getDesc() {
			return this.desc;
		}
		
		public String getHint() {
			return this.hint;
		}
		
		public boolean shouldHint(int level) {
			return this.levelUnlocked - 5 <= level;
		}

		public int getLevelUnlocked() {
			return this.levelUnlocked;
		}
		
		public int getAbility() {
			return this.ability;
		}
		
		public int getSkillLevel() {
			return this.skill;
		}
		
		public SkillButton getQuestionButton() {
			return this.questionButton;
		}
		
		public SkillButton getHintButton() {
			return this.hintButton;
		}
		
		public SkillButton getSkillButton() {
			return this.button;
		}
		
		public boolean enabled() {
			return this.enabled;
		}
	}
}
