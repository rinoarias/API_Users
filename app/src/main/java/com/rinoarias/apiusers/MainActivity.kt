package com.rinoarias.apiusers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.Nullable
import com.android.volley.*
import com.android.volley.toolbox.*
import org.json.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtResult = findViewById<EditText>(R.id.txtResult)

        val queue = Volley.newRequestQueue(this)
        val url = "https://gorest.co.in/public/v1/users"

        val btnGetUsers = findViewById<Button>(R.id.btnGetUsers)
        btnGetUsers.setOnClickListener {
            getData(queue, url, txtResult)
        }
    }

    fun getData(queue: RequestQueue, url: String, control: EditText){
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                run {
                    val users = response.getJSONArray("data")
                    for (i in 0 until users.length()) {
                        var userObject = users.getJSONObject(i)
                        var id_user = userObject.getString("id")
                        var name_user = userObject.getString("name")
                        var email_user = userObject.getString("email")
                        var gender_user = userObject.getString("gender")
                        var status_user = userObject.getString("status")
                        control.append("ID: ${id_user}\nNombre: ${name_user}\nE-mail: ${email_user}"
                            + "\nGénero: ${gender_user}\nStatus: ${status_user}\n\n")
                    }
                }
            },
            { error -> run {
                Toast.makeText(applicationContext,
                    "Error al obtener los datos",
                    Toast.LENGTH_LONG).show()
            } }
        )
        queue.add(request)
    }

}