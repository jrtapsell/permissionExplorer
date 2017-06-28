package uk.co.jrtapsell.appinfo.ui.multiple;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import uk.co.jrtapsell.appinfo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSimple {

    @Rule
    public ActivityTestRule<AppList> mActivityTestRule = new ActivityTestRule<>(AppList.class);

    @Test
    public void testSimple() {
        ViewInteraction gridLayout = onView(
                allOf(withId(R.id.appOuter),
                        childAtPosition(
                                allOf(withId(R.id.appList),
                                        withParent(withId(R.id.appListContainer))),
                                0),
                        isDisplayed()));
        gridLayout.perform(click());

        pressBack();

        ViewInteraction gridLayout2 = onView(
                allOf(withId(R.id.appOuter),
                        childAtPosition(
                                allOf(withId(R.id.appList),
                                        withParent(withId(R.id.appListContainer))),
                                5),
                        isDisplayed()));
        gridLayout2.perform(click());

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
