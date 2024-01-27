package ChatterMod.util.matcher;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.Expr;
import javassist.expr.FieldAccess;

public class FuzzyFieldAccessMatcher extends Matcher.FieldAccessMatcher {
    String classToCheck;
    String fieldName;

    public FuzzyFieldAccessMatcher(Class<?> clazz, String fieldName) {
        super(clazz, fieldName);
        this.classToCheck = clazz.getName();
        this.fieldName = fieldName;
    }

    public FuzzyFieldAccessMatcher(String className, String fieldName) {
        super(className, fieldName);
        this.classToCheck = className;
        this.fieldName = fieldName;
    }

    @Override
    public boolean match(Expr toMatch) {
        FieldAccess expr = (FieldAccess) toMatch;
        try {
            CtClass ctClassToCheck = Loader.getClassPool().getCtClass(classToCheck);
            return expr.getFieldName().equals(this.fieldName) && expr.getEnclosingClass().subclassOf(ctClassToCheck);
        } catch (NotFoundException ignored) {
        }
        return false;
    }
}
