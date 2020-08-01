package BottleInABottle.actions;

import BottleInABottle.relics.Flywheel;
import BottleInABottle.relics.PerpetualIceCreamGenerator;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.IceCream;


public class FlywheelSynergyAction extends AbstractGameAction {

    public FlywheelSynergyAction() {
        super();
    }


    @Override
    public void update() {
        AbstractDungeon.player.loseRelic(Flywheel.ID);
        AbstractDungeon.player.loseRelic(IceCream.ID);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, new PerpetualIceCreamGenerator());

        this.isDone = true;
    }
}
