package BottleInABottle.relics.Bottles;

import BottleInABottle.BottleInABottle;
import BottleInABottle.cards.curses.Paradox;
import BottleInABottle.patches.cards.AbstractCardPatch;
import BottleInABottle.util.TextureLoader;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;
import java.util.function.Predicate;

import static BottleInABottle.BottleInABottle.*;


public class BottledTime extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {

    private static AbstractCard card;
    private boolean cardSelected = true;
    private static AbstractRoom.RoomPhase curPhase;
    public static boolean shocked;


    public static final String ID = BottleInABottle.makeID(BottledTime.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath(BottledTime.class.getSimpleName()));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath(BottledTime.class.getSimpleName()));

    private static final SpireField<Boolean> bottledTimeCardField = AbstractCardPatch.Fields.bottledTimeCardField;


    public BottledTime() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
        tips.clear();
        tips.add(new PowerTip(name, description));
        shocked = false;
    }


    @Override
    public Predicate<AbstractCard> isOnCard() {
        return bottledTimeCardField::get;
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
                bottledTimeCardField.set(card, true);
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

        CardGroup group = getPlayableCardsGroup(
                CardGroup.getGroupWithoutBottledCards(
                        AbstractDungeon.player.masterDeck));
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3] + name, false, false, false, false);
    }


    @Override
    public void onUnequip() {
        AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
        if (cardInDeck != null) {
            bottledTimeCardField.set(cardInDeck, false);
        }
    }


    @Override
    public void update() {
        super.update();

        if (shocked) {
            shocked = false;
            setShockedDescription();
        }

        ArrayList<AbstractCard> selectedCards = AbstractDungeon.gridSelectScreen.selectedCards;
        if (!cardSelected && !selectedCards.isEmpty()) {
            cardSelected = true;
            card = selectedCards.get(0);
            bottledTimeCardField.set(card, true);

            UnlockTracker.markCardAsSeen(Paradox.ID);
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(new Paradox(card.cost), (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));

            AbstractDungeon.getCurrRoom().phase = curPhase;
            selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }


    @Override
    public void atBattleStart() {
        if (card != null) {
            AbstractCard cardToPlay = AbstractDungeon.player.drawPile.getSpecificCard(card);
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                 if (bottledTimeCardField.get(c)) {
                     cardToPlay = c;
                     break;
                 }
            }

            if (cardToPlay != null) {
                this.flash();
                cardToPlay.freeToPlayOnce = true;
                AbstractDungeon.actionManager.addToBottom(new NewQueueCardAction(cardToPlay, true));
                AbstractDungeon.player.drawPile.removeCard(cardToPlay);
            } else {
                logger.warn(BottledTime.class.getSimpleName() + ": Can't find card to play!");
            }
        }
    }


    public void setDescriptionAfterLoading() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    public void setShockedDescription() {
        this.description = DESCRIPTIONS[1] + FontHelper.colorString(card.name, "y") + DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }


    @Override
    public String getUpdatedDescription() { return DESCRIPTIONS[0]; }


    public static CardGroup getPlayableCardsGroup(CardGroup group) {
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : group.group) {
            if (c.cost > -2) {
                retVal.addToTop(c);
            }
        }

        return retVal;
    }
}
