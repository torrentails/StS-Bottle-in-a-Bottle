package BottleInABottle.relics.Bottles;

import BottleInABottle.BottleInABottle;
import BottleInABottle.patches.cards.AbstractCardPatch;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;
import java.util.function.Predicate;

import static BottleInABottle.BottleInABottle.*;


public class InvertedBottle extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {

    private static AbstractCard card;
    private boolean cardSelected = true;
    private static AbstractRoom.RoomPhase curPhase;

    public static final String ID = BottleInABottle.makeID(InvertedBottle.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(InvertedBottle.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(InvertedBottle.class.getSimpleName()));

    private static final SpireField<Boolean> invertedBottleCardField = AbstractCardPatch.Fields.invertedBottleCardField;

    public InvertedBottle() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
        tips.clear();
        tips.add(new PowerTip(name, description));
    }


    @Override
    public Predicate<AbstractCard> isOnCard() {
        return invertedBottleCardField::get;
    }

    @Override
    public Integer onSave() {
        if (card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(card);
        } else {
            return -1;
        }
    }


    @Override
    public void onLoad(Integer idx) {
        if (idx == null) { return; }

        if (idx >= 0 && idx < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(idx);
            if (card != null) {
                invertedBottleCardField.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }


    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        curPhase = AbstractDungeon.getCurrRoom().phase;
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;

        CardGroup group = CardGroup.getGroupWithoutBottledCards(
                        AbstractDungeon.player.masterDeck);
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name, false, false, false, false);
    }


    @Override
    public void update() {
        super.update();

        if (!cardSelected) {
            ArrayList<AbstractCard> selectedCards = AbstractDungeon.gridSelectScreen.selectedCards;

            if (!selectedCards.isEmpty()) {
                cardSelected = true;
                card = selectedCards.get(0);
                invertedBottleCardField.set(card, true);

                AbstractDungeon.getCurrRoom().phase = curPhase;
                selectedCards.clear();
                setDescriptionAfterLoading();
            }
        }
    }


    @Override
    public void atBattleStartPreDraw() {
        if (card != null) {
            AbstractCard cardToDiscard = AbstractDungeon.player.drawPile.getSpecificCard(card);
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (invertedBottleCardField.get(c)) {
                    cardToDiscard = c;
                    break;
                }
            }

            if (cardToDiscard != null) {
                this.flash();
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(cardToDiscard, AbstractDungeon.player.drawPile));
                // AbstractDungeon.player.drawPile.removeCard(cardToDiscard);
            } else {
                logger.warn(InvertedBottle.class.getSimpleName() + ": can't locate card");
            }
        }
    }


    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }


    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

}
