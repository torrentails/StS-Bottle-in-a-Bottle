package BottleInABottle.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.ShineLinesEffect;
import com.megacrit.cardcrawl.vfx.TouchPickupGold;

import java.lang.reflect.Field;
import java.util.HashMap;


// TODO: write a better coin blood drop
public class CoinBloodDrop extends TouchPickupGold {
    // private boolean centerOnPlayer;
    private float xStart;
    private float yStart;
    private float xEnd;
    private float yEnd;

    public CoinBloodDrop(boolean centerOnPlayer) {
        super(centerOnPlayer);
        // this.centerOnPlayer = centerOnPlayer;

        try {
            Field bounceXField = TouchPickupGold.class.getDeclaredField("bounceX");
            bounceXField.setAccessible(true);

            bounceXField.set(this, MathUtils.random(-4.0F, -1.0F));

            // Field xField = TouchPickupGold.class.getDeclaredField("bounceX");
            // xField.setAccessible(true);
            //
            //     for (int i = 0; i < 10; ++i) {
            //         AbstractDungeon.effectsQueue.add(new DanceOfDeathStanceChangeParticle((Float) xField.get(inst)));
            //     }
            //
            //     inst.isDone = true;
            //
            //     return SpireReturn.Return(null);
            //
            // }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void update() {
        HashMap<String, Field> fields = new HashMap<>();

        try {
            Class<TouchPickupGold> cls = TouchPickupGold.class;
            fields.put("isPickupable", cls.getDeclaredField("isPickupable"));
            fields.put("x", cls.getDeclaredField("x"));
            fields.put("y", cls.getDeclaredField("y"));

            fields.forEach((String s, Field f) -> f.setAccessible(true));

            float time = Gdx.graphics.getDeltaTime();
            float dx = Interpolation.pow2Out.apply(0.0f, 1.0f, time);
            float dy = Interpolation.bounceOut.apply(0.0f, 1.0f, time);



            // float x = (float)fields.get("x").get(this);
            // float y = (float)fields.get("y").get(this);
            //
            // if ((boolean)fields.get("isPickupable").get(this)) {
            //     this.isDone = true;
            //     this.playGainGoldSFX();
            //     AbstractDungeon.effectsQueue.add(new ShineLinesEffect(x, y));
            // }
            //
            // fields.get("x").set(this, 0 - x * Gdx.graphics.getDeltaTime() * 60.0F);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();

        } finally {
            fields.forEach((String s, Field f) -> f.setAccessible(false));
        }

        super.update();
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        super.render(spriteBatch);
    }


    @Override
    public void dispose() {

    }


    private void playGainGoldSFX() {
        switch(MathUtils.random(2)) {
            case 0:
                CardCrawlGame.sound.play("GOLD_GAIN", 0.1F);
                break;
            case 1:
                CardCrawlGame.sound.play("GOLD_GAIN_3", 0.1F);
                break;
            default:
                CardCrawlGame.sound.play("GOLD_GAIN_5", 0.1F);
        }
    }
}
