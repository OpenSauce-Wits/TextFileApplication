package com.example.textfileapplication;

import android.Manifest;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    String poemName="ToMySister";
    String poem = "To My Sister\n" +
            "\n" +
            "It is the first mild day of March:\n" +
            "Each minute sweeter than before\n" +
            "The redbreast sings from the tall larch\n" +
            "That stands beside our door.\n" +
            "\n" +
            "There is a blessing in the air,\n" +
            "Which seems a sense of joy to yield\n" +
            "To the bare trees, and mountains bare,\n" +
            "And grass in the green field.\n" +
            "\n" +
            "My sister! (’tis a wish of mine)\n" +
            "Now that our morning meal is done,\n" +
            "Make haste, your morning task resign;\n" +
            "Come forth and feel the sun.\n" +
            "\n" +
            "Edward will come with you—and, pray,\n" +
            "Put on with speed your woodland dress;\n" +
            "And bring no book: for this one day\n" +
            "We’ll give to idleness.\n" +
            "\n" +
            "No joyless forms shall regulate\n" +
            "Our living calendar:\n" +
            "We from to-day, my Friend, will date\n" +
            "The opening of the year.\n" +
            "\n" +
            "Love, now a universal birth,\n" +
            "From heart to heart is stealing,\n" +
            "From earth to man, from man to earth:\n" +
            "—It is the hour of feeling.\n" +
            "\n" +
            "One moment now may give us more\n" +
            "Than years of toiling reason:\n" +
            "Our minds shall drink at every pore\n" +
            "The spirit of the season.\n" +
            "\n" +
            "Some silent laws our hearts will make,\n" +
            "Which they shall long obey:\n" +
            "We for the year to come may take\n" +
            "Our temper from to-day.\n" +
            "\n" +
            "And from the blessed power that rolls\n" +
            "About, below, above,\n" +
            "We’ll frame the measure of our souls:\n" +
            "They shall be tuned to love.\n" +
            "\n" +
            "Then come, my Sister! come, I pray,\n" +
            "With speed put on your woodland dress;\n" +
            "And bring no book: for this one day\n" +
            "We’ll give to idleness.";
    private String errEmptyFileName="Please input file name!",
    errSpecialCharacters="No special characters in file name!",
    fileDeleted="File Deleted!",
    errFileNotFound="File Does Not Exist!";

    @Test
    public void readButtonExists(){
        ViewInteraction button = onView(withId(R.id.btnRead));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void deleteButtonExists(){
        ViewInteraction button = onView(withId(R.id.btnDelete));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void saveButtonExists(){
        ViewInteraction button = onView(withId(R.id.btnSave));
        button.check(matches(isDisplayed()));
    }

    @Test
    public void emptyFileName(){
        ViewInteraction Read = onView(withId(R.id.btnRead));
        ViewInteraction fileName = onView(withId(R.id.edtFileName));
        ViewInteraction Display = onView(withId(R.id.txtDisplay));
        Read.perform(click());
        Display.check(matches(withText(errEmptyFileName)));
    }

    @Test
    public void noSpecialCharacters(){
        ViewInteraction Read = onView(withId(R.id.btnRead));
        ViewInteraction fileName = onView(withId(R.id.edtFileName));
        ViewInteraction Display = onView(withId(R.id.txtDisplay));
        fileName.perform(typeText("To My Sister"));
        closeSoftKeyboard();
        Read.perform(click());
        Display.check(matches(withText(errSpecialCharacters)));
    }

    @Test
    public void unsavedFile(){
        ViewInteraction Read = onView(withId(R.id.btnRead));
        ViewInteraction fileName = onView(withId(R.id.edtFileName));
        ViewInteraction Display = onView(withId(R.id.txtDisplay));
        fileName.perform(typeText("boom"));
        closeSoftKeyboard();
        Read.perform(click());
        Display.check(matches(withText(errFileNotFound)));
    }

    @Test
    public void deleteFile(){
        ViewInteraction Save = onView(withId(R.id.btnSave));
        ViewInteraction Display = onView(withId(R.id.txtDisplay));
        ViewInteraction Read = onView(withId(R.id.btnRead));
        ViewInteraction fileName = onView(withId(R.id.edtFileName));
        ViewInteraction fileContent = onView(withId(R.id.edtFileContent));
        ViewInteraction Delete = onView(withId(R.id.btnDelete));
        fileName.perform(typeText(poemName));
        closeSoftKeyboard();
        fileContent.perform(typeText("poem"));
        closeSoftKeyboard();
        Save.perform(click());
        Delete.perform(click());
        Display.check(matches(withText(fileDeleted)));
    }

//    @Test
//    public void createAndDisplayFile(){
//        ViewInteraction Save = onView(withId(R.id.btnSave));
//        ViewInteraction Display = onView(withId(R.id.txtDisplay));
//        ViewInteraction Read = onView(withId(R.id.btnRead));
//        ViewInteraction fileName = onView(withId(R.id.edtFileName));
//        ViewInteraction fileContent = onView(withId(R.id.edtFileContent));
//        ViewInteraction Delete = onView(withId(R.id.btnDelete));
//        fileName.perform(typeText(poemName));
//        closeSoftKeyboard();
//        Delete.perform(click());
//        fileContent.perform(replaceText(poem));
//        closeSoftKeyboard();
//        Save.perform(scrollTo()).perform(click());
//        Display.perform(scrollTo()).check(matches(withText(poem)));
//    }

}
