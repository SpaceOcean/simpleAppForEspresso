package ru.kkuzmichev.simpleappforespresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */




@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Before //Выполняется перед тестами
    public void registerIdlingResources() { //Подключаемся к “счетчику”
            IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource);
    }


    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("ru.kkuzmichev.simpleappforespresso", appContext.getPackageName());
    }

    @Test
    // positive Test
    public void positiveCheckTextHome(){
        ViewInteraction mainText = onView(
                withId(R.id.text_home)
        );

        mainText.perform(click()).check(
                matches(withText("This is home fragment")
                ));
    }
    @Test
    // negative Test
    public void negativeCheckTextHome(){
        ViewInteraction mainText = onView(
                withId(R.id.text_home)
        );

        mainText.perform(click()).check(
                matches(withText("This is gallery fragment")
                ));
    }

    @Test
    // Intent Test
    public void checkIntent(){
        String url = "https://google.com";
        ViewInteraction options = onView(
                withContentDescription("More options")
        );
        options.perform(click());
        ViewInteraction settings = onView(
                withId(R.id.title)
        );
        Intents.init();
        settings.perform(click());
        intended(hasData(url));
        intended(hasAction(Intent.ACTION_VIEW));
        Intents.release();
    }

    @Test
    // List Test
    public void checkList(){
        ViewInteraction navigation = onView(
                withContentDescription("Open navigation drawer")
        );
        navigation.check(matches(isDisplayed()));
        navigation.perform(click());

        ViewInteraction gallery = onView(withText("Gallery"));
        gallery.check(matches(isDisplayed()));
        gallery.perform(click());

        ViewInteraction recyclerView = onView(withId(R.id.recycle_view));
        recyclerView.check(CustomViewAssertions.isRecyclerView());
        recyclerView.check(matches(CustomViewMatcher.recyclerViewSizeMatcher(10)));
    }

    @After // Выполняется после тестов
    public void unregisterIdlingResources() { //Отключаемся от “счетчика”
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource);
    }
}
