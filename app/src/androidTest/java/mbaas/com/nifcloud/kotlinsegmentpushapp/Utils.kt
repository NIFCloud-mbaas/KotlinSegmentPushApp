package mbaas.com.nifcloud.kotlinsegmentpushapp

import android.util.Log
import com.nifcloud.mbaas.core.NCMBInstallation
import com.nifcloud.mbaas.core.NCMBPush
import com.nifcloud.mbaas.core.NCMBQuery
import org.json.JSONArray
import org.json.JSONException

class Utils {
    private val TAG = "FcmService"
    companion object {
        val NOTIFICATION_TITLE = "UITest push notification"
        val NOTIFICATION_TEXT =
            "Thank you! We appreciate your business, and we’ll do our best to continue to give you the kind of service you deserve."
    }


    fun sendPushWithSearchCondition() {
        val installation = NCMBInstallation.getCurrentInstallation()
        installation.getDeviceTokenInBackground { token, e ->
            val query = NCMBQuery<NCMBInstallation>("installation")
            query.whereEqualTo("deviceToken", token)
            val push = NCMBPush()
            push.setSearchCondition(query)
            push.title = NOTIFICATION_TITLE
            push.message = NOTIFICATION_TEXT
            try {
                push.target = JSONArray("[android]")
            } catch (jsonException: JSONException) {
                jsonException.printStackTrace()
            }
            push.sendInBackground { e ->
                if (e != null) {
                    Log.d(TAG, "Send push fail")
                } else {
                    Log.d(TAG, "Send push success!")
                }
            }
        }
    }
}