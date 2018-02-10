package org.wikipedia.espresso.util;

import android.app.Activity;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public final class ViewTools {

    public static final int WAIT_FOR_500 = 500;
    public static final int WAIT_FOR_1000 = 1000;
    public static final int WAIT_FOR_2000 = 2000;


    public static boolean viewIsDisplayed(int viewId) {
        final boolean[] isDisplayed = {true};

        onView(withId(viewId))
                .withFailureHandler((Throwable error, Matcher<View> viewMatcher) -> isDisplayed[0] = false)
                .check(matches(isDisplayed()));

        return isDisplayed[0];
    }

    public static Matcher<View> childAtPosition(
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


    public static void pressBack() {
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    private static ViewAction doWaitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    public static void waitFor(final long millis) {
        onView(isRoot()).perform(doWaitFor(millis));
    }

    private static ViewAction doRotateScreen(final Activity activity, final int orientation) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Do screen rotation";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                activity.setRequestedOrientation(orientation);
            }
        };
    }

    public static void rotateScreen(final Activity activity, final int orientation) {
        onView(isRoot()).perform(doRotateScreen(activity, orientation));
    }

    private ViewTools() { }
}
