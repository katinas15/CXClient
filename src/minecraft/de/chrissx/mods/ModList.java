package de.chrissx.mods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.chrissx.mods.building.BedFucker;
import de.chrissx.mods.building.FastBreak;
import de.chrissx.mods.building.FastPlace;
import de.chrissx.mods.building.Nuker;
import de.chrissx.mods.chat.AuthMeCrack;
import de.chrissx.mods.chat.Home;
import de.chrissx.mods.chat.MassTpa;
import de.chrissx.mods.chat.Spam;
import de.chrissx.mods.combat.Aimbot;
import de.chrissx.mods.combat.AutoArmor;
import de.chrissx.mods.combat.Autoclicker;
import de.chrissx.mods.combat.Fasthit;
import de.chrissx.mods.combat.Killaura;
import de.chrissx.mods.combat.Noswing;
import de.chrissx.mods.combat.Reach;
import de.chrissx.mods.fun.JailsmcBot;
import de.chrissx.mods.fun.KillPotion;
import de.chrissx.mods.fun.MultiText;
import de.chrissx.mods.fun.SkinBlinker;
import de.chrissx.mods.fun.Text;
import de.chrissx.mods.fun.Throw;
import de.chrissx.mods.fun.TrollPotion;
import de.chrissx.mods.fun.Twerk;
import de.chrissx.mods.movement.ACFly1;
import de.chrissx.mods.movement.ACFly2;
import de.chrissx.mods.movement.ACSpeed1;
import de.chrissx.mods.movement.StepJump;
import de.chrissx.mods.movement.AntiAfk;
import de.chrissx.mods.movement.Autosprint;
import de.chrissx.mods.movement.FastLadder;
import de.chrissx.mods.movement.LegitSpeed;
import de.chrissx.mods.movement.Nofall;
import de.chrissx.mods.movement.Sneak;
import de.chrissx.mods.movement.Timer;
import de.chrissx.mods.movement.VanillaFly;
import de.chrissx.mods.movement.Velocity;
import de.chrissx.mods.render.Freecam;
import de.chrissx.mods.render.Fullbright;
import de.chrissx.mods.render.NoRender;
import de.chrissx.mods.render.Tracer;
import de.chrissx.mods.render.Xray;

public class ModList implements Iterable<Mod> {

	public final SkinBlinker skinBlinker = new SkinBlinker();
	public final FastBreak fastBreak = new FastBreak();
	public final FastPlace fastPlace = new FastPlace();
	public final Spam spam = new Spam();
	public final Nofall nofall = new Nofall();
	public final Fullbright fullbright = new Fullbright();
	public final Xray xray = new Xray();
	public final Fasthit fasthit = new Fasthit();
	public final Autoclicker autoclicker = new Autoclicker();
	public final Noswing noswing = new Noswing();
	public final AuthMeCrack authMeCrack = new AuthMeCrack();
	public final AntiAfk antiAfk = new AntiAfk();
	public final Autosteal autosteal = new Autosteal();
	public final Killaura killaura = new Killaura();
	public final Nuker nuker = new Nuker();
	public final Sneak sneak = new Sneak();
	public final Tracer tracer = new Tracer();
	public final MassTpa massTpa = new MassTpa();
	public final VanillaFly vanillaFly = new VanillaFly();
	public final Throw thrower = new Throw();
	public final AutoArmor autoArmor = new AutoArmor();
	public final Twerk twerk = new Twerk();
	public final FastLadder fastLadder = new FastLadder();
	public final Reach reach = new Reach();
	public final Velocity velocity = new Velocity();
	public final ACFly1 acFly1 = new ACFly1();
	public final ACFly2 acFly2 = new ACFly2();
	public final Timer timer = new Timer();
	public final ACSpeed1 acSpeed1 = new ACSpeed1();
	public final StepJump stepJump = new StepJump();
	public final LegitSpeed legitSpeed = new LegitSpeed();
	public final Autosprint autosprint = new Autosprint();
	public final BedFucker bedFucker = new BedFucker();
	public final Freecam freecam = new Freecam();
	public final Aimbot aimbot = new Aimbot();
	public final JailsmcBot jailsmcBot = new JailsmcBot();
	public final NoRender noRender = new NoRender();
	
	public final Home home = new Home();
	public final Panic panic = new Panic();
	public final Text text = new Text();
	public final MultiText multiText = new MultiText();
	public final KillPotion killPotion = new KillPotion();
	public final TrollPotion trollPotion = new TrollPotion();
	
	private final Map<String, Bindable> bindable = new HashMap<String, Bindable>();
	
	private final List<Mod> mods = Arrays.asList(new Mod[] {
			skinBlinker,
			fastBreak,
			fastPlace,
			spam,
			nofall,
			fullbright,
			xray,
			fasthit,
			autoclicker,
			noswing,
			authMeCrack,
			antiAfk,
			autosteal,
			killaura,
			nuker,
			sneak,
			tracer,
			massTpa,
			vanillaFly,
			thrower,
			autoArmor,
			twerk,
			fastLadder,
			reach,
			velocity,
			acSpeed1,
			stepJump,
			acFly1,
			acFly2,
			timer,
			legitSpeed,
			autosprint,
			bedFucker,
			freecam,
			aimbot,
			jailsmcBot,
			noRender
	});
	public final List<RenderedObject> renderedObjects = new ArrayList<RenderedObject>();
	public final List<TickListener> tickListeners = new ArrayList<TickListener>();
	public final List<StopListener> stopListeners = new ArrayList<StopListener>();
	
	@SuppressWarnings("unlikely-arg-type")
	public ModList() {
		for(Mod m : mods)
			addBindable(m);
		
		addBindable(home);
		addBindable(panic);
		addBindable(trollPotion);
		addBindable(killPotion);
		addBindable(text);
		addBindable(multiText);
		
		for(Mod m : mods) {
			renderedObjects.add(m);
			tickListeners.add(m);
			stopListeners.add(m);
		}
		
		for(Bindable b : bindable.values()) {
			if(b instanceof RenderedObject && !renderedObjects.contains(b))
				renderedObjects.add((RenderedObject) b);
			if(b instanceof TickListener && !tickListeners.contains(b))
				tickListeners.add((TickListener) b);
			if(b instanceof StopListener && !stopListeners.contains(b))
				stopListeners.add((StopListener) b);
		}
	}
	
	public void addBindable(Bindable bindable) {
		this.bindable.put(bindable.getName().toLowerCase(), bindable);
	}
	
	public Set<Entry<String, Bindable>> getBindEntrys() {
		return bindable.entrySet();
	}
	
	public Bindable getBindable(String name) {
		return bindable.get(name.toLowerCase());
	}
	
	public Mod get(int i) {
		return mods.get(i);
	}

	@Override
	public Iterator iterator() {
		return mods.iterator();
	}
}