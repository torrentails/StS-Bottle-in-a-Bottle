package BottleInABottle.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;


@SuppressWarnings("unused")
public class AbstractCardPatch {

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<Boolean> bottledTimeCardField = new SpireField<>(() -> false);
        public static SpireField<Boolean> invertedBottleCardField = new SpireField<>(() -> false);


        @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
        public static class CardField {
            public static AbstractCard Postfix(AbstractCard result, AbstractCard _inst) {
                bottledTimeCardField.set(result, bottledTimeCardField.get(_inst));
                invertedBottleCardField.set(result, invertedBottleCardField.get(_inst));

                return result;
            }
        }
    }
}

