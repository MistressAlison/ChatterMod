package ChatterMod;

import ChatterMod.util.TexLoader;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class MainModfile implements
        EditCardsSubscriber,
        EditStringsSubscriber,
        PostInitializeSubscriber {

    public static final String modID = "ChatterMod";
    public static final Logger logger = LogManager.getLogger(MainModfile.class.getName());

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static final String BADGE_IMAGE = modID + "Resources/images/Badge.png";

    public static UIStrings uiStrings;
    public static String[] TEXT;
    public static String[] EXTRA_TEXT;
    private static final String AUTHOR = "Mistress Autumn";

    public MainModfile() {
        BaseMod.subscribe(this);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        MainModfile thismod = new MainModfile();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(modID+".cards")
                .setDefaultSeen(true)
                .cards();
    }

    private void loadLangStrings(String language) {
        String path = modID+"Resources/localization/" + language + "/";

        tryLoadStringsFile(CardStrings.class,path + "Cardstrings.json");
        tryLoadStringsFile(UIStrings.class, path + "UIstrings.json");
    }

    private void tryLoadStringsFile(Class<?> stringType, String filepath) {
        try {
            BaseMod.loadCustomStringsFile(stringType, filepath);
        } catch (GdxRuntimeException e) {
            // Ignore file not found
            if (!e.getMessage().startsWith("File not found:")) {
                throw e;
            }
        }
    }

    @Override
    public void receiveEditStrings() {
        String language = Settings.language.name().toLowerCase();
        loadLangStrings("eng");
        if (!language.equals("eng")) {
            loadLangStrings(language);
        }
    }

    @Override
    public void receivePostInitialize() {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ModConfigs"));
        EXTRA_TEXT = uiStrings.EXTRA_TEXT;
        TEXT = uiStrings.TEXT;
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Load the Mod Badge
        Texture badgeTexture = TexLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, EXTRA_TEXT[0], AUTHOR, EXTRA_TEXT[1], settingsPanel);

        //Add Config stuff

    }
}
