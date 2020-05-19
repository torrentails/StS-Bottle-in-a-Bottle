package BottleInABottle.patches.core;

import BottleInABottle.relics.GoldArmour;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


@SuppressWarnings("unused")
public class AbstractCreaturePatch {

    @SpirePatch(clz = AbstractCreature.class, method = "increaseMaxHp")
    public static class increaseMaxHp {

        @SuppressWarnings("rawtypes")
        public static SpireReturn Prefix(AbstractCreature inst, int amount, boolean showEffect) {

            if (AbstractDungeon.player.hasRelic(GoldArmour.ID) && inst instanceof AbstractPlayer) {
                AbstractDungeon.actionManager.addToBottom(new GainGoldAction((int) ((float)amount * GoldArmour.MULTIPLIER)));
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }
}
