package BottleInABottle.relics;

import BottleInABottle.BottleInABottle;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;
import java.util.Collections;

import static BottleInABottle.BottleInABottle.makeRelicOutlinePath;
import static BottleInABottle.BottleInABottle.makeRelicPath;


public class StaminaBar extends CustomRelic {
    public static final String ID = BottleInABottle.makeID(StaminaBar.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(StaminaBar.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(StaminaBar.class.getSimpleName()));


    public StaminaBar() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.HEAVY);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public void atBattleStart() {
        ArrayList<AbstractPower> lst = new ArrayList<>();
        lst.add(new StrengthPower(AbstractDungeon.player, 4));
        lst.add(new LoseStrengthPower(AbstractDungeon.player, 5));
        lst.add(new DexterityPower(AbstractDungeon.player, 4));
        lst.add(new LoseDexterityPower(AbstractDungeon.player, 5));

        Collections.shuffle(lst);

        for (AbstractPower p : lst) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, p, p.amount, true, AbstractGameAction.AttackEffect.NONE));
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}