package ChatterMod.cards;

import ChatterMod.MainModfile;
import ChatterMod.actions.RecordAndPlaybackAction;
import ChatterMod.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static ChatterMod.MainModfile.makeID;

public class Chatter extends AbstractEasyCard {
    public final static String ID = makeID(Chatter.class.getSimpleName());

    public Chatter() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CardColor.COLORLESS);
        baseDamage = damage = 12;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RecordAndPlaybackAction(f -> {
            MainModfile.logger.info("Chatter Volume: "+f);
            for (int i = 0 ; i < AbstractDungeon.getMonsters().monsters.size() ; i++) {
                multiDamage[i] *= Math.min(1f, Math.max(0f,f-40)/45f);
            }
            addToTop(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public String cardArtCopy() {
        return PiercingWail.ID;
    }
}