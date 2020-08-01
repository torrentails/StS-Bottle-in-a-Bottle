package BottleInABottle.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class Flywheel extends CustomRelic {
    public Flywheel(String id,
                    Texture texture,
                    RelicTier tier, LandingSound sfx) {
        super(id, texture, tier, sfx);
    }
}
