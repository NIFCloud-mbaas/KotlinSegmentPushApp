package mbaas.com.nifcloud.kotlinsegmentpushapp;

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBInstallation
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var _objectId: TextView
    lateinit var _appversion: TextView
    lateinit var _channels: Spinner
    lateinit var _devicetoken: TextView
    lateinit var _sdkversion: TextView
    lateinit var _timezone: TextView
    lateinit var _createdate: TextView
    lateinit var _updatedate: TextView
    lateinit var _txtPrefectures: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(applicationContext, "27d6ef658a713529090f5d98963517fee7ae861d3864d66c7f7ab211ccf907c4",
                "ed76d3b2bee59bd4b203013034c522ae246e588067fdc830973055e4bb520e03");


        //表示する端末情報のデータを反映
        _objectId = findViewById<View>(R.id.txtObject) as TextView
        _appversion = findViewById<View>(R.id.txtAppversion) as TextView
        _channels = findViewById<View>(R.id.spinChannel) as Spinner
        _devicetoken = findViewById<View>(R.id.txtDevicetoken) as TextView
        _sdkversion = findViewById<View>(R.id.txtSdkversion) as TextView
        _timezone = findViewById<View>(R.id.txtTimezone) as TextView
        _createdate = findViewById<View>(R.id.txtCreatedate) as TextView
        _updatedate = findViewById<View>(R.id.txtUpdatedate) as TextView
        _txtPrefectures = findViewById<View>(R.id.txtPrefecture) as EditText

        var installation = NCMBInstallation.getCurrentInstallation()
        installation.getDeviceTokenInBackground { token, e ->
            val query = NCMBInstallation.getQuery()
            //同じRegistration IDをdeviceTokenフィールドに持つ端末情報を検索する
            query.whereEqualTo("deviceToken", token)

            //データストアの検索を実行
            query.findInBackground { results, e ->
                //検索された端末情報のobjectIdを設定
                if (e == null) {
                    installation = results[0]

                    //表示する端末情報を指定
                    _objectId.text = installation.objectId
                    _devicetoken.text = token
                    _appversion.text = installation.appVersion
                    try {
                        if (installation.channels != null) {
                            val selectChannel = installation.channels.get(0).toString()
                            val channelArray = arrayOf("A", "B", "C", "D")
                            val selectId = Arrays.asList(*channelArray).indexOf(selectChannel)
                            _channels.setSelection(selectId)
                        }
                    } catch (e2: JSONException) {
                        e2.printStackTrace()
                    }
                    _sdkversion.text = installation.sdkVersion
                    _timezone.text = installation.timeZone
                    _createdate.text = installation.createDate.toString()
                    _updatedate.text = installation.updateDate.toString()
                    if (installation.getString("Prefectures") != null) {
                        _txtPrefectures.setText(installation.getString("Prefectures"))
                    }
                }

            }
        }

        val _btnSave = findViewById<View>(R.id.btnSave) as Button
        _btnSave.setOnClickListener {
            _channels = findViewById<View>(R.id.spinChannel) as Spinner
            _txtPrefectures = findViewById<View>(R.id.txtPrefecture) as EditText
            val prefectures = _txtPrefectures.text.toString()
            val item = _channels.selectedItem as String
            val tmpArray = JSONArray()
            tmpArray.put(item)
            installation.channels = tmpArray
            installation.put("Prefectures", prefectures)
            installation.saveInBackground { e ->
                if (e != null) {
                    //保存失敗
                    Toast.makeText(this@MainActivity, "端末情報の保存に失敗しました。" + e.message, Toast.LENGTH_LONG).show()
                } else {
                    //保存成功
                    Toast.makeText(this@MainActivity, "端末情報の保存に成功しました。", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
