package BottleInABottle.relics;

import BottleInABottle.BottleInABottle;
import BottleInABottle.actions.UpdateCardCostAction;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import java.util.HashMap;

import static BottleInABottle.BottleInABottle.makeRelicOutlinePath;
import static BottleInABottle.BottleInABottle.makeRelicPath;


public class PreCharger
        extends CustomRelic {
    public static final String ID = BottleInABottle.makeID(PreCharger.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(PreCharger.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(PreCharger.class.getSimpleName()));

    private final HashMap<AbstractCard, Boolean> cards = new HashMap<>();


    public PreCharger() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.SOLID);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }


    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard c : p.hand.group) reduceCost(c);
        for (AbstractCard c : p.drawPile.group) reduceCost(c);
        for (AbstractCard c : p.discardPile.group) reduceCost(c);
    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction cardAction) {
        if (cards.containsKey(card) && !cards.get(card)) {
            this.flash();
            cards.put(card, true);
            AbstractDungeon.actionManager.addToBottom(new UpdateCardCostAction(card, 1));
        }
    }


    private void reduceCost(AbstractCard card) {
        if (card.cost >= 2) {
            card.updateCost(-1);
            cards.put(card, false);
        }
    }
}
