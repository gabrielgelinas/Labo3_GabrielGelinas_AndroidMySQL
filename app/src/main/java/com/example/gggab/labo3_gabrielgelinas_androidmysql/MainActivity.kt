package com.example.gggab.labo3_gabrielgelinas_androidmysql

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.username
import kotlinx.android.synthetic.main.activity_main.password
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import android.net.wifi.WifiManager
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.json.JSONObject
import java.io.*


class MainActivity : AppCompatActivity() {

    private lateinit var mainContext: Context
    lateinit var parentVG: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parentVG = findViewById(R.id.parent)



        mainContext = this.applicationContext

        button1.setOnClickListener {
            login()
        }

        button2.setOnClickListener {
            signIn()
        }
    }

    fun login() {
        val wm = getSystemService(Context.WIFI_SERVICE) as WifiManager
        val networkWorker = NetworkWorker(wm)
        var ip = networkWorker.ip

        ip = "192.168.0.134"

        System.out.println(ip)
        val user_log = object {
            var user_id: String = username.text.toString()
            var user_pw: String = password.text.toString()
        }
        System.out.println(user_log)

        val url = URL("http://" + ip.toString() + "/AndroidToMySQL/userconnect.php")
        Login(url, mainContext, parentVG).execute(ip, user_log)

    }

    fun signIn() {

    }
}

class Login(url: URL, mainContext: Context, var parentVG: ViewGroup) : AsyncTask<Any, Int?, JSONObject>() {


    private val httpConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
    private var mContext: Context = mainContext as Context

    init {

    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: Any?): JSONObject {

        try {

            httpConnection.doInput = true
            //                connection.setDoOutput(true);
            httpConnection.doOutput = true

            val outputStream = httpConnection.outputStream
            val bufferedWriter = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))

            val msg = URLEncoder.encode("username", "utf-8") + "=" +
                    URLEncoder.encode(params[0].toString(), "utf-8") + "&" +
                    URLEncoder.encode("password", "utf-8") + "=" +
                    URLEncoder.encode(params[1].toString(), "utf-8")
            //            String msg =
            //                    URLEncoder.encode("ProductId", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getProductId(), "utf-8") + "&" +
            //                            URLEncoder.encode("FullDisplayName", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getName(), "utf-8") + "&" +
            //                            URLEncoder.encode("BrandName", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getBrandName(), "utf-8") + "&" +
            //                            URLEncoder.encode("IsAgeRequired", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getAgeRequired().toString(), "utf-8") + "&" +
            //                            URLEncoder.encode("SizeLabel", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getSizeLabel(), "utf-8") + "&" +
            //                            URLEncoder.encode("Size", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getSize(), "utf-8") + "&" +
            //                            URLEncoder.encode("ProductUrl", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getProductUrl(), "utf-8") + "&" +
            //                            URLEncoder.encode("ProductImageUrl", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getProductImageUrl(), "utf-8") + "&" +
            //                            URLEncoder.encode("HasNewPrice", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getHasNewPrice().toString(), "utf-8") + "&" +
            //                            URLEncoder.encode("RegularPrice", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getRegularPrice().toString(), "utf-8") + "&" +
            //                            URLEncoder.encode("PromotionName", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getPromotionName(), "utf-8") + "&" +
            //                            URLEncoder.encode("SalesPrice", "utf-8") + "=" + URLEncoder.encode((String) objects[0].getSalesPrice().toString(), "utf-8");

            bufferedWriter.write(msg)
            bufferedWriter.flush()
            bufferedWriter.close()
            outputStream.close()

            //                System.out.println("Sent to PHP file: " + msg);

            val inputStream = httpConnection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
            var line: String = bufferedReader.readLine()
//            val stringBuilder = StringBuilder()

            val answer = JSONObject(line)
//            while (!(line.isBlank())) {
            System.out.println(line)
            System.out.println(answer)
//                stringBuilder.append(line + "\n")

//                line = bufferedReader.readLine()
            //                    System.out.println("PHP file returned: " + line);
//            }
//            bufferedReader.close()

            return answer
        } catch (e: Exception) {
            return JSONObject(e.message)
        }


    }

    override fun onPostExecute(result: JSONObject) {
        if (result["auth"] == "accept")
            parentVG.inflate(R.layout.sign_in,false)
            parentVG.getChildAt(1).display
            mContext.toast(result["auth"].toString())
    }

}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

}

fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

//    fun SendToDatabase() {
//        for (product in productList) {
//            val databaseWorker = DatabaseWorker(this, "http://192.168.0.134/AndroidToMySQL/dbwrite.php")
//            databaseWorker.execute(product)
//        }
//    }

