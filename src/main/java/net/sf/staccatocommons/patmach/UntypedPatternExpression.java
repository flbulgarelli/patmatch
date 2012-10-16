package net.sf.staccatocommons.patmach;

import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.defs.tuple.Tuple3;
import net.sf.staccatocommons.lang.Option;

public interface UntypedPatternExpression<A> {

  Option<Object> bind(A value);

  Option<Tuple2<Object, Object>> bind2(A value);

  Option<Tuple3<Object, Object, Object>> bind3(A value);

}