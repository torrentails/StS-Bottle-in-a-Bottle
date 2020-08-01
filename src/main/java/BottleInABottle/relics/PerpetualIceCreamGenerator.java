package BottleInABottle.relics;

import BottleInABottle.BottleInABottle;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import static BottleInABottle.BottleInABottle.makeRelicOutlinePath;
import static BottleInABottle.BottleInABottle.makeRelicPath;


public class PerpetualIceCreamGenerator extends CustomRelic {
    public static final String ID = BottleInABottle.makeID(PerpetualIceCreamGenerator.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(PerpetualIceCreamGenerator.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(PerpetualIceCreamGenerator.class.getSimpleName()));


    public PerpetualIceCreamGenerator() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }


    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
