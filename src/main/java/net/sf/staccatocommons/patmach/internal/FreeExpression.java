package net.sf.staccatocommons.patmach.internal;

import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.patmach.TypedPatternExpression;

public class FreeExpression<A> implements TypedPatternExpression<A, A> {


  @Override
  public Option<A> bind(A value) {
    return Option.some(value);
  }

}
