package edu.ktu.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    var db : AppDatabase? = null
    var data:MutableList<User>? = null

    var list: RecyclerView? = null
    var adapter: UserAdapter? = null

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)
        data = mutableListOf()

        list = findViewById(R.id.users_list)
        list?.layoutManager = LinearLayoutManager(this)

        //getLocalUsers()
        getRemoteUsers()

        findViewById<Button>(R.id.add_btn).setOnClickListener {
            addUser(it)
        }
    }

    fun getLocalUsers()
    {
        disposable = db?.getUserDao()?.getAllUsers()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    if(!it.isNullOrEmpty())
                    {
                        data = it.toMutableList()
                    }
                    adapter = UserAdapter(data!!)
                    list?.adapter = adapter
                },{})
    }

    fun getRemoteUsers()
    {
        disposable = UserApiService.service.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(!it.isNullOrEmpty())
                        {
                            data = it.toMutableList()
                        }
                        adapter = UserAdapter(data!!)
                        list?.adapter = adapter
                    },
                    {
                        Toast.makeText(this, "Getting all users failed!", Toast.LENGTH_SHORT).show()
                    }
                )
    }

    fun addUser(v:View)
    {
        var nameField = findViewById<EditText>(R.id.name_field)
        //var surnameField = findViewById<EditText>(R.id.surname_field)

        /*var newUser = User(0,
            nameField.text.toString(),
            surnameField.text.toString()
        )*/

        val name = nameField.text.toString()

        nameField.setText("")
        //surnameField.setText("")

        /*disposable = db?.getUserDao()?.insert(newUser)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                data?.add(newUser)
                adapter?.notifyItemInserted(data!!.lastIndex)
            },{})*/

        disposable = UserApiService.service.getUser(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            data?.add(it)
                            adapter?.notifyItemInserted(data!!.lastIndex)
                        },
                        {
                            Toast.makeText(this, "Getting all users failed! {${it.message}}", Toast.LENGTH_SHORT).show()
                        }
                )
    }

    override fun onPause()
    {
        super.onPause()
        disposable?.dispose()
    }
}