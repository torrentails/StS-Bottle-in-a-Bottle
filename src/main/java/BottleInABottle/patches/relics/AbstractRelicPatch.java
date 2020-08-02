package BottleInABottle.patches.relics;

import BottleInABottle.relics.Flywheel;
import BottleInABottle.relics.PerpetualIceCreamGenerator;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.IceCream;


@SuppressWarnings("unused")
public class AbstractRelicPatch {
    private static void FlywheelSynergy() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;

        if (p.hasRelic(Flywheel.ID) && p.hasRelic(IceCream.ID)) {
            p.loseRelic(IceCream.ID);
            p.loseRelic(Flywheel.ID);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new PerpetualIceCreamGenerator());
        }
    }


    @SpirePatch(clz = AbstractRelic.class,
                method = "instantObtain",
                paramtypez = {})
    public static class instantObtainPatch {
        public static void Postfix(AbstractRelic _inst) {
            FlywheelSynergy();
        }
    }


    @SpirePatch(clz = AbstractRelic.class,
                method = "instantObtain",
                paramtypez = {
                    AbstractPlayer.class,
                    int.class,
                    boolean.class
                })
    public static class instantObtainPatch2  {
        public static void Postfix(AbstractRelic _inst) {
            FlywheelSynergy();
        }
    }


    @SpirePatch(clz = AbstractRelic.class,
                method = "obtain")
    public static class obtainPatch {
        public static void Postfix(AbstractRelic _inst) {
            FlywheelSynergy();
        }
    }


    @SpirePatch(clz = AbstractRelic.class,
                method = "reorganizeObtain")
    public static class reorganizeObtainPatch {
        public static void Postfix(AbstractRelic _inst) {
            FlywheelSynergy();
        }
    }
}
