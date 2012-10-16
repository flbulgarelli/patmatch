package net.sf.staccatocommons.patmach;

import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.defs.function.Function3;

public class PatternMatchers {

  public static <A, B> PatternMatcher<A, B> case_(UntypedPatternExpression<A> expression,
    Function<?, ? extends B> function) {
    return new PatternMatcher<A, B>().case_(expression, function);
  }

  public static <A, B> PatternMatcher<A, B> case_(UntypedPatternExpression<A> expression,
    Function2<?, ?, ? extends B> function) {
    return new PatternMatcher<A, B>().case_(expression, function);
  }

  public static <A, B> PatternMatcher<A, B> case_(UntypedPatternExpression<A> expression,
    Function3<?, ?, ?, ? extends B> function) {
    return new PatternMatcher<A, B>().case_(expression, function);
  }

  public static <A, B, C> PatternMatcher<A, C> case_(TypedPatternExpression<A, B> expression,
    Function<? super B, ? extends C> function) {
    return new PatternMatcher<A, C>().case_(expression, function);
  }

}