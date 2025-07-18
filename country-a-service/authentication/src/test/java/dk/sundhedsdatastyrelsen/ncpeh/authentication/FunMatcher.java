package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.function.Function;

/**
 * Hamcrest matcher that asserts on the result of calling with a function, e.g.
 * where(Foo::getName, equalTo("Donald Duck"))
 */
class FunMatcher<T, U> extends TypeSafeMatcher<T> {
    Function<T, U> f;
    Matcher<? super U> matcher;

    public FunMatcher(Function<T, U> f, Matcher<? super U> matcher) {
        this.f = f;
        this.matcher = matcher;
    }

    public static <T, U> Matcher<T> where(Function<T, U> f, Matcher<? super U> m) {
        return new FunMatcher<T, U>(f, m);
    }

    @Override
    protected boolean matchesSafely(T item) {
        var x = f.apply(item);
        return matcher.matches(x);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("object that matches ").appendDescriptionOf(matcher);
    }
}
