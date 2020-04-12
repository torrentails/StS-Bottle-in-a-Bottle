package BottleInABottle.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;


@SuppressWarnings("unused")
@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class PersistentPatch {

    public static SpireField<Boolean> persistent = new SpireField<>(() -> false);


    public PersistentPatch() {}


    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class PersistentField {
        public static AbstractCard Postfix(AbstractCard result, AbstractCard _inst) {
            persistent.set(result, persistent.get(_inst));
            return result;
        }
    }
}
