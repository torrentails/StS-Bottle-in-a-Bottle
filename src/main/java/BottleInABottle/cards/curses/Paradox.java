package BottleInABottle.cards.curses;

import BottleInABottle.BottleInABottle;
import BottleInABottle.relics.BottledTime;
import basemod.abstracts.CustomCard;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.common.LosePercentHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Paradox
        extends CustomCard
        implements CustomSavable<Integer> {

    public static String ID = BottleInABottle.makeID(Paradox.class.getSimpleName());
    public static final String IMG = BottleInABottle.makeCardPath(Paradox.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.CURSE;
    public static final CardColor COLOR = CardColor.CURSE;
    private static final int COST = -1;

    public static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = STRINGS.NAME;
    public static final String DESCRIPTION = STRINGS.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = STRINGS.UPGRADE_DESCRIPTION;


    public Paradox() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
        this.selfRetain = true;
        SoulboundField.soulbound.set(this, true);

        if (AbstractDungeon.player == null) {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    public Paradox(Integer cost) {
        this();

        if (cost != null) {
            this.misc = 1;
            this.upgradeBaseCost(cost);
            this.costForTurn = cost;
        } else {
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


    @SuppressWarnings("unused")
    @SpireOverride
    protected String getCost() {
        if (AbstractDungeon.player == null) return "¯\\_(ˆ‹ˆ)_/¯";
        if (this.misc == 0 && this.cost == -1) return "?";

        return SpireSuper.call();
    }


    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.superFlash();
        this.addToTop(new LosePercentHPAction(50));
        BottledTime.shocked = true;
    }


    @Override
    public void upgrade() {}


    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}


    @Override
    public Integer onSave() {
        return this.cost;
    }


    @Override
    public void onLoad(Integer cost) {
        this.misc = 1;
        this.upgradeBaseCost(cost);
        this.costForTurn = cost;
    }
}
