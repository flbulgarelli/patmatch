package net.sf.staccatocommons.patmach;

import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.defs.function.Function3;

public class PatternMatchers {

  public static <A, B> PatternMatcher<A, B> match(UntypedPatternExpression<A> expression,
    Function<?, ? extends B> function) {
    return new PatternMatcher<A, B>().match(expression, function);
  }

  public static <A, B> PatternMatcher<A, B> match(UntypedPatternExpression<A> expression,
    Function2<?, ?, ? extends B> function) {
    return new PatternMatcher<A, B>().match(expression, function);
  }

  public static <A, B> PatternMatcher<A, B> match(UntypedPatternExpression<A> expression,
    Function3<?, ?, ?, ? extends B> function) {
    return new PatternMatcher<A, B>().match(expression, function);
  }

  public static <A, B, C> PatternMatcher<A, C> match(TypedPatternExpression<A, B> expression,
    Function<? super B, ? extends C> function) {
    return new PatternMatcher<A, C>().match(expression, function);
  }

}