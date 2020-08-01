package BottleInABottle.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;


public class UpdateCardCostAction
        extends AbstractGameAction {
    private final AbstractCard card;

    public UpdateCardCostAction(AbstractCard card, int amt) {
        super();
        this.amount = amt;
        this.card = card;
    }


    @Override
    public void update() {
        this.card.updateCost(this.amount);
        int baseCost = card.makeCopy().cost;
        card.isCostModified = card.costForTurn != baseCost;
        this.isDone = true;
    }
}
