package net.sf.staccatocommons.patmach;

import net.sf.staccatocommons.lang.Option;

public interface TypedPatternExpression<A, B> {

  Option<B> bind(A value);

}
