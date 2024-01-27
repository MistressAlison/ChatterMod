package ChatterMod.cards.abstracts;

import ChatterMod.MainModfile;
import ChatterMod.util.CardArtRoller;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static ChatterMod.MainModfile.makeImagePath;
import static ChatterMod.MainModfile.modID;
import static ChatterMod.util.Wiz.atb;
import static ChatterMod.util.Wiz.att;

public abstract class AbstractEasyCard extends CustomCard {
    // https://en.wikipedia.org/wiki/Tertiary_color#Tertiary-,_quaternary-,_and_quinary-_terms
    protected static final Color RED = new Color(0xFF0000FF);
    protected static final Color SCARLET = new Color(0xFF2000FF);
    protected static final Color VERMILION = new Color(0xFF4000FF);
    protected static final Color PERSIMMON = new Color(0xFF6000FF);
    protected static final Color ORANGE = new Color(0xFF8000FF);
    protected static final Color ORANGE_PEEL = new Color(0xFFA000FF);
    protected static final Color AMBER = new Color(0xFFC000FF);
    protected static final Color GOLDEN_YELLOW = new Color(0xFFE000FF);
    protected static final Color YELLOW = new Color(0xFFFF00FF);
    protected static final Color LEMON = new Color(0xE0FF00FF);
    protected static final Color LIME = new Color(0xC0FF00FF);
    protected static final Color SPRING_BUG = new Color(0xA0FF00FF);
    protected static final Color CHARTREUSE = new Color(0x80FF00FF);
    protected static final Color BRIGHT_GREEN = new Color(0x60FF00FF);
    protected static final Color HARLEQUIN = new Color(0x40FF00FF);
    protected static final Color NEON_GREEN = new Color(0x20FF00FF);
    protected static final Color GREEN =  new Color(0x00FF00FF);
    protected static final Color JADE = new Color(0x00FF20FF);
    protected static final Color ERIN = new Color(0x00FF40FF);
    protected static final Color EMERALD = new Color(0x00FF60FF);
    protected static final Color SPRING_GREEN = new Color(0x00FF80FF);
    protected static final Color MINT = new Color(0x00FFA0FF);
    protected static final Color AQUAMARINE = new Color(0x00FFC0FF);
    protected static final Color TURQUOISE = new Color(0x00FFE0FF);
    protected static final Color CYAN =  new Color(0x00FFFFFF);
    protected static final Color SKY_BLUE = new Color(0x00E0FFFF);
    protected static final Color CAPRI = new Color(0x00C0FFFF);
    protected static final Color CORNFLOWER = new Color(0x00A0FFFF);
    protected static final Color AZURE = new Color(0x0080FFFF);
    protected static final Color COBALT = new Color(0x0060FFFF);
    protected static final Color CERULEAN = new Color(0x40FFFF);
    protected static final Color SAPPHIRE = new Color(0x0020FFFF);
    protected static final Color BLUE = new Color(0x0000FFFF);
    protected static final Color IRIS = new Color(0x2000FFFF);
    protected static final Color INDIGO = new Color(0x4000FFFF);
    protected static final Color VERONICA = new Color(0x6000FFFF);
    protected static final Color VIOLET = new Color(0x8000FFFF);
    protected static final Color AMETHYST = new Color(0xA000FFFF);
    protected static final Color PURPLE = new Color(0xC000FFFF);
    protected static final Color PHLOX = new Color(0xE000FFFF);
    protected static final Color MAGENTA = new Color(0xFF00FFFF);
    protected static final Color FUCHSIA = new Color(0xFF00E0FF);
    protected static final Color CERISE = new Color(0xFF00C0FF);
    protected static final Color DEEP_PINK = new Color(0xFF00A0FF);
    protected static final Color ROSE = new Color(0xFF0080FF);
    protected static final Color RASPBERRY = new Color(0xFF0060FF);
    protected static final Color CRIMSON = new Color(0xFF0040FF);
    protected static final Color AMARANTH = new Color(0xFF0020FF);

    protected static final Color PLATINUM = new Color(0xE5E4E2FF);
    protected static final Color GOLD = new Color(0xFFD700FF);
    protected static final Color SILVER = new Color(0xC0C0C0FF);
    protected static final Color BRONZE = new Color(0xCD7F32FF);

    protected static final Color WHITE = new Color(1, 1, 1, 1);
    protected static final Color LIGHT_GRAY =  new Color(0.75f, 0.75f, 0.75f, 1);
    protected static final Color GRAY =  new Color(0.5f, 0.5f, 0.5f, 1);
    protected static final Color DARK_GRAY =  new Color(0.25f, 0.25f, 0.25f, 1);
    protected static final Color BLACK = new Color(0, 0, 0, 1);

    protected static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    private List<TooltipInfo> addedTips;
    protected final CardStrings cardStrings;

    public int secondMagic = -1;
    public int baseSecondMagic = -1;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int secondDamage = -1;
    public int baseSecondDamage = -1;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public int secondBlock = -1;
    public int baseSecondBlock = -1;
    public boolean upgradedSecondBlock;
    public boolean isSecondBlockModified;

    public int info = -1;
    public int baseInfo = -1;
    public boolean upgradedInfo;
    public boolean isInfoModified;

    private float rotationTimer = 0;
    private int previewIndex;
    protected ArrayList<AbstractCard> cyclePreviewCards = new ArrayList<>();

    protected boolean needsArtRefresh = false;
    protected boolean manualD2 = false;

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.getAllCards() != null && !CardLibrary.getAllCards().isEmpty()) {
                CardArtRoller.computeCard(this);
                if (cardsToPreview instanceof AbstractEasyCard) {
                    CardArtRoller.computeCard((AbstractEasyCard) cardsToPreview);
                }
            } else {
                needsArtRefresh = true;
            }
        }
    }


    @Override
    protected Texture getPortraitImage() {
        if (super.getPortraitImage() == null) {
            return CardArtRoller.getPortraitTexture(this);
        }
        return super.getPortraitImage();
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString;

        switch (cardType) {
            case ATTACK:
            case POWER:
            case SKILL:
                textureString = makeImagePath("cards/" + cardName + ".png");
                break;
            default:
                textureString = makeImagePath("ui/missing.png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1 && !manualD2) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    protected void applyPowersToBlock() {
        if (baseSecondBlock > -1) {
            secondBlock = baseSecondBlock;

            int tmp = baseBlock;
            baseBlock = baseSecondBlock;

            super.applyPowersToBlock();

            secondBlock = block;
            baseBlock = tmp;

            super.applyPowersToBlock();

            isSecondBlockModified = secondBlock != baseSecondBlock;
        } else {
            super.applyPowersToBlock();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1 && !manualD2) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
        secondBlock = baseSecondBlock;
        isSecondBlockModified = false;
        info = baseInfo;
        isInfoModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
        if (upgradedSecondBlock) {
            secondBlock = baseSecondBlock;
            isSecondBlockModified = true;
        }
        if (upgradedInfo) {
            info = baseInfo;
            isInfoModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void upgradeSecondBlock(int amount) {
        baseSecondBlock += amount;
        secondBlock = baseSecondBlock;
        upgradedSecondBlock = true;
    }

    protected void upgradeInfo(int amount) {
        baseInfo += amount;
        info = baseInfo;
        upgradedInfo = true;
    }

    protected void uDesc() {
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    protected void upgradeCardToPreview() {
        for (AbstractCard q : cyclePreviewCards) {
            q.upgrade();
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    public abstract void upp();

    public void update() {
        super.update();
        if (needsArtRefresh) {
            CardArtRoller.computeCard(this);
            if (cardsToPreview instanceof AbstractEasyCard) {
                CardArtRoller.computeCard((AbstractEasyCard) cardsToPreview);
            }
            for (AbstractCard c : cyclePreviewCards) {
                if (c instanceof AbstractEasyCard) {
                    CardArtRoller.computeCard((AbstractEasyCard) c);
                }
            }
            for (AbstractCard c : MultiCardPreview.multiCardPreview.get(this)) {
                if (c instanceof AbstractEasyCard) {
                    CardArtRoller.computeCard((AbstractEasyCard) c);
                }
            }
        }
        if (!cyclePreviewCards.isEmpty()) {
            if (hb.hovered) {
                if (rotationTimer <= 0F) {
                    rotationTimer = getRotationTimeNeeded();
                    cardsToPreview = cyclePreviewCards.get(previewIndex);
                    if (previewIndex == cyclePreviewCards.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                } else {
                    rotationTimer -= Gdx.graphics.getDeltaTime();
                }
            }
        }
    }

    protected float getRotationTimeNeeded() {
        return 2.5f;
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractCreature t, AbstractGameAction.AttackEffect fx) {
        dmg(t, fx, false);
    }

    protected void dmg(AbstractCreature t, AbstractGameAction.AttackEffect fx, boolean fast) {
        atb(new DamageAction(t, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx, fast));
    }

    protected void dmgTop(AbstractCreature t, AbstractGameAction.AttackEffect fx) {
        dmgTop(t, fx, false);
    }

    protected void dmgTop(AbstractCreature t, AbstractGameAction.AttackEffect fx, boolean fast) {
        att(new DamageAction(t, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx, fast));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        allDmg(fx, false);
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx, boolean fast) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx, fast));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.5f, 0.5f, 0.5f, 0.5f, false);
    }

    public String itemArt() {
        return "";
    }

    public String rollerKey() {
        return cardID;
    }

    public float itemScale() {
        return 1.0f;
    }

    public static Color mix(Color c1, Color c2) {
        return c1.cpy().lerp(c2, 0.5f);
    }

    public static Color lighten(Color c) {
        return c.cpy().lerp(Color.WHITE, 0.25f);
    }

    public static Color darken(Color c) {
        return c.cpy().lerp(Color.BLACK, 0.25f);
    }

    public static Color pastel(Color c) {
        return colorFromHSL(getHue(c), getSat(c), 0.8f, c.a);
    }

    private static float getHue(Color c) {
        float max = c.r;
        float min = c.r;
        if (c.g > max) {
            max = c.g;
        }
        if (c.b > max) {
            max = c.b;
        }
        if (c.g < min) {
            min = c.g;
        }
        if (c.b < min) {
            min = c.b;
        }
        float delta = max - min;
        if (delta == 0) {
            return 0;
        }
        if (c.g >= c.b) {
            return (float) Math.toDegrees(Math.acos((c.r - c.g/2 - c.b/2)/Math.sqrt(c.r*c.r + c.g*c.g + c.b*c.b - c.r*c.g - c.r*c.b - c.g*c.b)));
        } else {
            return 360 - (float) Math.toDegrees(Math.acos((c.r - c.g/2 - c.b/2)/Math.sqrt(c.r*c.r + c.g*c.g + c.b*c.b - c.r*c.g - c.r*c.b - c.g*c.b)));
        }
    }

    private static float getSat(Color c) {
        float max = c.r;
        float min = c.r;
        if (c.g > max) {
            max = c.g;
        }
        if (c.b > max) {
            max = c.b;
        }
        if (c.g < min) {
            min = c.g;
        }
        if (c.b < min) {
            min = c.b;
        }
        float delta = max - min;
        if (delta == 0) {
            return 0;
        }
        float lightness = (max + min)/2f;
        return delta / (1 - Math.abs(2*lightness - 1));
    }

    public static Color colorFromHSL(float hue, float sat, float light, float alpha) {
        float d = sat * (1 - Math.abs(2*light - 1));
        float x = d * (1 - Math.abs(((hue/60f)%2) - 1));
        float m = light - d/2f;
        if (0 <= hue && hue < 60) {
            return new Color(d+m, x+m, m, alpha);
        } else if (60 <= hue && hue < 120) {
            return new Color(x+m, d+m, m, alpha);
        } else if (120 <= hue && hue < 180) {
            return new Color(m, d+m, x+m, alpha);
        } else if (180 <= hue && hue < 240) {
            return new Color(m, x+m, d+m, alpha);
        } else if (240 <= hue && hue < 300) {
            return new Color(x+m, m, d+m, alpha);
        } else {
            return new Color(d+m, m, x+m, alpha);
        }
    }

    protected void addCustomKeyword(String key) {
        if (addedTips == null) {
            addedTips = new ArrayList<>();
        }
        addedTips.add(new TooltipInfo(BaseMod.getKeywordTitle(key), BaseMod.getKeywordDescription(key)));
    }

    protected void addVanillaKeyword(Keyword key) {
        if (addedTips == null) {
            addedTips = new ArrayList<>();
        }
        addedTips.add(new TooltipInfo(TipHelper.capitalize(key.NAMES[0]), key.DESCRIPTION));
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (addedTips != null) {
            return addedTips;
        }
        return super.getCustomTooltips();
    }
}

