package net.sf.staccatocommons.patmach;

import java.util.LinkedList;
import java.util.List;

import net.sf.staccatocommons.collections.stream.Streams;
import net.sf.staccatocommons.defs.Applicable;
import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.defs.function.Function2;
import net.sf.staccatocommons.defs.function.Function3;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.defs.tuple.Tuple3;
import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.function.Functions;

public class PatternMatcher<A, B> {

  private final List<Applicable<A, Option<B>>> cases = new LinkedList<Applicable<A, Option<B>>>();

  public PatternMatcher<A, B> match(UntypedPatternExpression<A> expression) {
    return match(expression, Functions.<B> identity());
  }

  public PatternMatcher<A, B> match(UntypedPatternExpression<A> expression,
    Function<?, ? extends B> function) {
    cases.add(new UntypedCase<A, B>(expression, function));
    return this;
  }

  public <C> PatternMatcher<A, B> match(TypedPatternExpression<A, C> expression,
    Function<? super C, ? extends B> function) {
    cases.add(new TypedCase<A, C, B>(expression, function));
    return this;
  }

  public PatternMatcher<A, B> match(UntypedPatternExpression<A> expression,
    Function2<?, ?, ? extends B> function) {
    cases.add(new UntypedCase2<A, B>(expression, function));
    return this;
  }

  public PatternMatcher<A, B> match(UntypedPatternExpression<A> expression,
    Function3<?, ?, ?, ? extends B> function) {
    cases.add(new UntypedCase3<A, B>(expression, function));
    return this;
  }

  public Function<A, B> default_(Function<? super A, ? extends B> function) {
    match(Patterns.<A> free(), function);
    return new AbstractFunction<A, B>() {
      public B apply(A arg0) {
        return Streams
          .from(cases)
          .map(FunctionsExt.<A, Option<B>> apply(arg0))
          .find(FunctionsExt.<B> isDefined())
          .value();
      }
    };
  }

  public Function<A, B> defaultFail() {
    return default_(new AbstractFunction<A, B>() {
      public B apply(A arg0) {
        throw new IllegalArgumentException(String.valueOf(arg0));
      }
    });
  }

  public static final class UntypedCase<A, B> implements Applicable<A, Option<B>> {
    private final UntypedPatternExpression<A> expression;
    private final Applicable<?, ? extends B> function;

    public UntypedCase(UntypedPatternExpression<A> expression, Applicable<?, ? extends B> function) {
      this.expression = expression;
      this.function = function;
    }

    public Option<B> apply(A arg0) {
      return expression.bind(arg0).map((Applicable<Object, B>) function);
    }
  }

  public static final class UntypedCase2<A, B> implements Applicable<A, Option<B>> {
    private final UntypedPatternExpression<A> expression;
    private final Function<Tuple2<Object, Object>, B> function;

    public UntypedCase2(UntypedPatternExpression<A> expression, Function2<?, ?, ? extends B> f) {
      this.expression = expression;
      this.function = ((Function2<Object, Object, B>) f).uncurry();
    }

    public Option<B> apply(A arg0) {
      return expression.bind2(arg0).map(function);
    }
  }

  public static final class UntypedCase3<A, B> implements Applicable<A, Option<B>> {
    private final UntypedPatternExpression<A> expression;
    private final Function<Tuple3<Object, Object, Object>, B> function;

    public UntypedCase3(UntypedPatternExpression<A> expression, Function3<?, ?, ?, ? extends B> f) {
      this.expression = expression;
      this.function = ((Function3<Object, Object, Object, B>) f).uncurry();
    }

    public Option<B> apply(A arg0) {
      return expression.bind3(arg0).map(function);
    }
  }

  public static final class TypedCase<A, B, C> implements Applicable<A, Option<C>> {
    private final TypedPatternExpression<A, B> expression;
    private final Applicable<? super B, ? extends C> function;

    public TypedCase(TypedPatternExpression<A, B> expression,
      Applicable<? super B, ? extends C> function) {
      this.expression = expression;
      this.function = function;
    }

    public Option<C> apply(A arg0) {
      return expression.bind(arg0).map(function);
    }
  }

}