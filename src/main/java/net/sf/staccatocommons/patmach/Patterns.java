package net.sf.staccatocommons.patmach;

import java.util.regex.Pattern;

import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.patmach.internal.FreeExpression;
import net.sf.staccatocommons.patmach.internal.RegexpExpression;
import net.sf.staccatocommons.restrictions.Constant;

public class Patterns {



  @Constant
  public static <A> TypedPatternExpression<A, A> free() {
    return new FreeExpression<A>();
  }

  public static UntypedPatternExpression<String> rx(String regex) {
    return new RegexpExpression(Pattern.compile(regex));
  }

  public static <A, B> TypedPatternExpression<A, B> type(final Class<B> clazz) {
     return new TypedPatternExpression<A, B>() {
         @Override
         public Option<B> bind(A value) {
            if(clazz.isAssignableFrom(value.getClass()))
                return Option.some((B)value);
             return Option.none();
         }
     };
  }

  public static <A> TypedPatternExpression<A, A> eq(final A value) {
    return new TypedPatternExpression<A, A>() {
      public Option<A> bind(A value2) {
        if (value.equals(value2)) return Option.some(value);
        return Option.none();
      }
    };
  }

}
