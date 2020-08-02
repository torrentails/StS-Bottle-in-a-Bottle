package BottleInABottle.relics;

import BottleInABottle.BottleInABottle;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.IceCream;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static BottleInABottle.BottleInABottle.makeRelicOutlinePath;
import static BottleInABottle.BottleInABottle.makeRelicPath;


public class Flywheel extends CustomRelic {
    public static final String ID = BottleInABottle.makeID(Flywheel.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(Flywheel.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(Flywheel.class.getSimpleName()));
    private String synstr;


    public Flywheel() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(DESCRIPTIONS[1] + this.synstr, DESCRIPTIONS[2]));
    }


    @Override
    public void atBattleStart() {
        if (this.counter > 0) {
            this.flash();
            AbstractDungeon.player.gainEnergy(this.counter);
        }

        this.counter = -1;
        this.grayscale = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
    }


    @Override
    public void onVictory() {
        if (!this.grayscale &&
            AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            this.counter = EnergyPanel.getCurrentEnergy();
        }
    }

    @Override
    public String getUpdatedDescription() {
        this.synstr = FontHelper.colorString((new IceCream().name), "y");
        return DESCRIPTIONS[0];
    }
}
