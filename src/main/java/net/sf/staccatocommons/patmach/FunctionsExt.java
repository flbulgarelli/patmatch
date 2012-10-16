package net.sf.staccatocommons.patmach;

import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.predicate.Predicate;
import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.predicate.AbstractPredicate;
import net.sf.staccatocommons.restrictions.Constant;

public class FunctionsExt {

  @Constant
  public static Function<String, Integer> parseInteger() {
    return new AbstractFunction<String, Integer>() {
      public Integer apply(String arg0) {
        return Integer.parseInt(arg0);
      }
    };
  }
  
  @Constant
  public static Function<String, Long> parseLong() {
    return new AbstractFunction<String, Long>() {
      public Long apply(String arg0) {
        return Long.parseLong(arg0);
      }
    };
  }


  @Constant
  public static final <A> Predicate<Option<A>> isDefined() {
    return new AbstractPredicate<Option<A>>() {
      public boolean eval(Option<A> arg0) {
        return arg0.isDefined();
      }
    };
  }

  @Constant
  public static final <A, B> Function<Applicable<A, B>, B> apply(final A argument) {
    return new AbstractFunction<Applicable<A, B>, B>() {
      public B apply(Applicable<A, B> arg0) {
        return arg0.apply(argument);
      }
    };
  }

}
