import net.sf.staccatocommons.defs.function.Function;
import net.sf.staccatocommons.lang.function.AbstractFunction;
import net.sf.staccatocommons.lang.function.AbstractFunction2;
import net.sf.staccatocommons.lang.function.Functions;
import net.sf.staccatocommons.lang.internal.Add;
import net.sf.staccatocommons.lang.tuple.Tuples;
import net.sf.staccatocommons.patmach.PatternMatcher;
import net.sf.staccatocommons.patmach.PatternMatchers;
import net.sf.staccatocommons.patmach.Patterns;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static net.sf.staccatocommons.lang.function.Functions.constant;
import static net.sf.staccatocommons.lang.function.Functions.identity;
import static net.sf.staccatocommons.patmach.PatternMatchers.match;
import static net.sf.staccatocommons.patmach.Patterns.eq;
import static net.sf.staccatocommons.patmach.Patterns.rx;
import static net.sf.staccatocommons.patmach.Patterns.type;
import static org.junit.Assert.assertEquals;


public class PatternMatchersTest {

    @Test
    public void constantPatterns() {
        Function<Integer, Integer> matcher =
            match(eq(1), constant(10)).
            match(eq(2), constant(20)).
            default_(constant(100));

        assertEquals(10, (int) matcher.apply(1));
        assertEquals(20, (int) matcher.apply(2));
        assertEquals(100, (int) matcher.apply(3));
    }

    @Test
    public void constantPatternsWithAnonymousClass() {
        Function<Integer, Integer> matcher =
                match(eq(1), new AbstractFunction<Integer, Integer>(){
                    public Integer apply(Integer _) {
                        return 1;
                    }
                }).
                match(eq(2), new AbstractFunction<Integer, Integer>(){
                    public Integer apply(Integer _) {
                        return 2;
                    }
                }).
                default_(new AbstractFunction<Integer, Integer>(){
                    public Integer apply(Integer _) {
                        return 100;
                    }
                });

        assertEquals(1, (int) matcher.apply(1));
        assertEquals(2, (int) matcher.apply(2));
        assertEquals(100, (int) matcher.apply(3));
    }

    @Test
    public void patternsWithBinding() {
        Function<Integer, Integer> matcher =
                match(eq(1), constant(10)).
                match(eq(2), constant(20)).
                default_(Functions.<Integer>identity());

        assertEquals(10, (int) matcher.apply(1));
        assertEquals(20, (int) matcher.apply(2));
        assertEquals(3, (int) matcher.apply(3));
    }

    @Test
    public void patternsWithBindingWithAnonymousClass() {
        Function<Integer, Integer> patmatch =
                match(eq(1), new AbstractFunction<Integer, Integer>() {
                    public Integer apply(Integer arg) {
                        return 10;
                    }
                }).
                match(eq(2), new AbstractFunction<Integer, Integer>() {
                    public Integer apply(Integer arg) {
                        return 20;
                    }
                }).
                default_(new AbstractFunction<Integer, Integer>() {
                    public Integer apply(Integer x) {
                        return x;
                    }
                });

        assertEquals(10, (int) patmatch.apply(1));
        assertEquals(20, (int) patmatch.apply(2));
        assertEquals(3, (int) patmatch.apply(3));
    }

    @Test
    public void patternsWithMultipleBinding() {
        Function<String, String> matcher =
            match(rx("(.).(.)"), new AbstractFunction2<String, String, String>() {
                public String apply(String x, String y) {
                    return x + y;
                }
            }).
            match(rx("(.)."), Functions.<String>identity()).
            defaultFail();

        assertEquals("ac", matcher.apply("abc"));
        assertEquals("b", matcher.apply("bd"));
    }

    @Test
    public void typePatterns() {
        Function<Object, String> matcher =
            match(type(Integer.class), Functions.toString_()).
            match(type(BigInteger.class), new AbstractFunction<BigInteger, String>(){
                public String apply(BigInteger x) {
                    return x.toString(2);
                }
            }).
            match(type(String.class), Functions.<String>identity()).
            defaultFail();

        assertEquals("10", matcher.apply(10));
        assertEquals("10", matcher.apply("10"));
        assertEquals("1010", matcher.apply(BigInteger.TEN));
    }



}
