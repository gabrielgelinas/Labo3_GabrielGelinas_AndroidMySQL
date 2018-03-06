package com.example.gggab.labo3_gabrielgelinas_androidmysql

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.button1
import kotlinx.android.synthetic.main.activity_main.button2
import kotlinx.android.synthetic.main.activity_main.username
import kotlinx.android.synthetic.main.activity_main.password
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            login()
        }

        button2.setOnClickListener {
            signIn()
        }
    }

    fun login() {
        val user_log = object {
            var user_id: String =  username.text.toString()
            var user_pw: String =  password.text.toString()
        }
        System.out.println(user_log)
        Login().execute(user_log)
    }

    fun signIn() {

    }
}

class Login() : AsyncTask<Any, Int?, String?>() {
    override fun doInBackground(vararg params: Any?): String? {

        try {
            val url = URL("http://192.168.0.134/AndroidToMySQL/dbwrite.php")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            //                connection.setDoOutput(true);
            connection.requestMethod = "POST"

            val outputStream = connection.outputStream
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

            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
            var line: String = bufferedReader.readLine()
            val stringBuilder = StringBuilder()

            while (!(line.isBlank())) {
                stringBuilder.append(line + "\n")

                line = bufferedReader.readLine()
                //                    System.out.println("PHP file returned: " + line);
            }
            bufferedReader.close()

            return stringBuilder.toString()
        } catch (e: Exception) {
            return e.message
        }


    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}
//    fun SendToDatabase() {
//        for (product in productList) {
//            val databaseWorker = DatabaseWorker(this, "http://192.168.0.134/AndroidToMySQL/dbwrite.php")
//            databaseWorker.execute(product)
//        }
//    }

