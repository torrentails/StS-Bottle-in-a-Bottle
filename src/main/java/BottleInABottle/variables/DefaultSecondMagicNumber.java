package BottleInABottle.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static BottleInABottle.BottleInABottle.makeID;

public class DefaultSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("SecondMagic");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "BottleInABottle:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false /*((AbstractDefaultCard) card).isDefaultSecondMagicNumberModified*/;

    }

    @Override
    public int value(AbstractCard card) {
        return 0 /*((AbstractDefaultCard) card).defaultSecondMagicNumber*/;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return 0 /*((AbstractDefaultCard) card).defaultBaseSecondMagicNumber*/;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false /*((AbstractDefaultCard) card).upgradedDefaultSecondMagicNumber*/;
    }
}