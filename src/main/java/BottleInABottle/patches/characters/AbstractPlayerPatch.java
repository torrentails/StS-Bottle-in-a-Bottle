package BottleInABottle.patches.characters;

import BottleInABottle.relics.GoldArmour;
import BottleInABottle.vfx.BleedGoldEffect;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

import java.util.ArrayList;

import static BottleInABottle.BottleInABottle.logger;


@SuppressWarnings("unused")
public class AbstractPlayerPatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class damage {
        private static Integer amount = null;

        @SpireInsertPatch(locator = Locator.class,
                          localvars={"damageAmount"})
        public static void Insert(AbstractPlayer inst, DamageInfo info, @ByRef int[] damageAmount) {
            if (!AbstractDungeon.player.hasRelic(GoldArmour.ID)) return;

            if (amount == null) {
                amount = damageAmount[0];
                if (damageAmount[0] <= 0) return;

                AbstractPlayer player = AbstractDungeon.player;
                player.getRelic(GoldArmour.ID).flash();

                if (player.gold >= damageAmount[0]) {
                    player.loseGold(damageAmount[0]);
                    damageAmount[0] = 0;

                } else {
                    damageAmount[0] -= player.gold;
                    player.loseGold(player.gold);
                }

                AbstractDungeon.effectsQueue.add(new BleedGoldEffect(amount, inst));

            } else {
                damageAmount[0] = amount;
                amount = null;
            }
        }


        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "currentHealth");
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);

                int[] retVal = new int[2];
                retVal[0] = found[1];
                retVal[1] = found[1] + 1;

                return retVal;
            }
        }
    }
}
