package net.sf.staccatocommons.patmach.internal;

import static net.sf.staccatocommons.lang.tuple.Tuples.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.staccatocommons.check.Ensure;
import net.sf.staccatocommons.defs.tuple.Tuple2;
import net.sf.staccatocommons.defs.tuple.Tuple3;
import net.sf.staccatocommons.lang.Option;
import net.sf.staccatocommons.patmach.UntypedPatternExpression;

public class RegexpExpression implements UntypedPatternExpression<String> {

  private final Pattern pattern;

  public RegexpExpression(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public Option<Tuple2<Object, Object>> bind2(String value) {
    Matcher matcher = pattern.matcher(value);
    if (find(matcher, 2))
      return Option.some(_((Object) matcher.group(1), (Object) matcher.group(2)));
    return Option.none();
  }

  @Override
  public Option<Object> bind(String value) {
    Matcher matcher = pattern.matcher(value);
    if (find(matcher, 1)) return Option.some((Object) matcher.group(1));
    return Option.none();
  }

  @Override
  public Option<Tuple3<Object, Object, Object>> bind3(String value) {
    Matcher matcher = pattern.matcher(value);
    if (find(matcher, 3))
      return Option.some(_(
        (Object) matcher.group(1),
        (Object) matcher.group(2),
        (Object) matcher.group(3)));
    return Option.none();
  }

  protected boolean find(Matcher matcher, int argCount) {
    if (matcher.find()) {
      int groupCount = matcher.groupCount();
      Ensure.that(
        groupCount >= argCount,
        "Bad biding. Expected %s variables, but only got %s",
        argCount,
        groupCount);
      return true;
    }
    return false;
  }

}