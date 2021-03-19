package com.nifcloud.mbaas.ncmbpushquickstart

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import mbaas.com.nifcloud.kotlinsegmentpushapp.MainActivity
import mbaas.com.nifcloud.kotlinsegmentpushapp.R
import mbaas.com.nifcloud.kotlinsegmentpushapp.Utils
import mbaas.com.nifcloud.kotlinsegmentpushapp.Utils.Companion.NOTIFICATION_TEXT
import mbaas.com.nifcloud.kotlinsegmentpushapp.Utils.Companion.NOTIFICATION_TITLE
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ExecuteUITest {

    var _objectId: ViewInteraction? = null
    var _appversion: ViewInteraction? = null
    var _channels: ViewInteraction? = null
    var _devicetoken: ViewInteraction? = null
    var _sdkversion: ViewInteraction? = null
    var _timezone: ViewInteraction? = null
    var _createdate: ViewInteraction? = null
    var _updatedate: ViewInteraction? = null
    var _txtPrefectures: ViewInteraction? = null
    var btnSave: ViewInteraction? = null

    private var decorView: View? = null
    val TIMEOUT = 150000
    var device: UiDevice? = null

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        _objectId = Espresso.onView(withId(R.id.txtObject))
        _appversion = Espresso.onView(withId(R.id.txtAppversion))
        _channels = Espresso.onView(withId(R.id.spinChannel))
        _devicetoken = Espresso.onView(withId(R.id.txtDevicetoken))
        _sdkversion = Espresso.onView(withId(R.id.txtSdkversion))
        _timezone = Espresso.onView(withId(R.id.txtTimezone))
        _createdate = Espresso.onView(withId(R.id.txtCreatedate))
        _updatedate = Espresso.onView(withId(R.id.txtUpdatedate))
        _txtPrefectures = Espresso.onView(withId(R.id.txtPrefecture))
        btnSave = Espresso.onView(withId(R.id.btnSave))
        activityRule.scenario.onActivity { activity -> decorView = activity.window.decorView }
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun initialScreen() {
        Espresso.closeSoftKeyboard()
        Espresso.onView(ViewMatchers.withText("Current Installation"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("ObjectId"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _objectId!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("appVersion"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _appversion!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("channels"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _channels!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("deviceToken"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _devicetoken!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("sdkVersion"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _sdkversion!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("timeZone"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _timezone!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("createDate"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _createdate!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("updateDate"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _updatedate!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Prefectures"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        _txtPrefectures!!.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        btnSave!!.perform(ViewActions.scrollTo())
            .check(ViewAssertions.matches(ViewMatchers.withText("SAVE")))
    }

    @Test
    @Throws(InterruptedException::class)
    fun doSave() {
        Thread.sleep(2000)
        _txtPrefectures!!.perform(
            ViewActions.typeText("Hoge"),
            ViewActions.closeSoftKeyboard() as ViewAction
        )
        btnSave!!.perform(ViewActions.scrollTo(), ViewActions.click())
        Espresso.onView(ViewMatchers.withText("端末情報の保存に成功しました。"))
            .inRoot(RootMatchers.withDecorView(CoreMatchers.not(decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    @Throws(InterruptedException::class)
    fun onSendNotification() {
        val utils = Utils()
        utils.sendPushWithSearchCondition()
        Thread.sleep(30000)
        device!!.openNotification()
        device!!.wait(Until.hasObject(By.text(NOTIFICATION_TITLE)), TIMEOUT.toLong())
        val title: UiObject2 = device!!.findObject(By.text(NOTIFICATION_TITLE))
        val text: UiObject2 = device!!.findObject(By.text(NOTIFICATION_TEXT))
        assertEquals(NOTIFICATION_TITLE, title.text)
        assertEquals(NOTIFICATION_TEXT, text.text)
        title.click()
    }
}