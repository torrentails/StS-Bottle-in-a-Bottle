package BottleInABottle.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;


public class BleedGoldEffect
        extends AbstractGameEffect {
    private static final float baseStaggerTime = 0.3f;

    private int amount;
    private AbstractCreature target;
    // private boolean centerOnPlayer;
    private float staggerTimer;
    private int min;
    private int max;

    public BleedGoldEffect(int amount, AbstractCreature target) {
        // this.amount = Math.min(Math.max(amount / 2, 0), 100);
        this.amount = amount;
        this.target = target;
        // this.centerOnPlayer = centerOnPlayer;

        staggerTimer = baseStaggerTime;
        if (amount > 15) staggerTimer -= 0.05f;
        if (amount > 50) staggerTimer -= 0.05f;

        if (amount < 50) {
            this.min = 1;
            this.max = 7;
        } else {
            this.min = 3;
            this.max = 18;
        }
    }


    @Override
    public void update() {
        this.staggerTimer -= Gdx.graphics.getDeltaTime();
        if (this.staggerTimer < 0.0F) {
            int goldToSpawn = MathUtils.random(this.min, this.max);
            if (goldToSpawn <= this.amount) {
                this.amount -= goldToSpawn;
            } else {
                goldToSpawn = this.amount;
                this.isDone = true;
            }
            for (int i = 0; i < goldToSpawn; ++i) {
                // AbstractDungeon.effectsQueue.add(new CoinBloodDrop(this.centerOnPlayer));
                AbstractDungeon.effectsQueue.add(new GainPennyEffect(this.target.hb.cX, this.target.hb.cY));

                this.staggerTimer = MathUtils.random(baseStaggerTime);
            }
        }
    }


    @Override
    public void render(SpriteBatch spriteBatch) {}


    @Override
    public void dispose() {}
}
