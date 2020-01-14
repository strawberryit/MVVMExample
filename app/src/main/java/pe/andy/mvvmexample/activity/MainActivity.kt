package pe.andy.mvvmexample.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import pe.andy.mvvmexample.R
import pe.andy.mvvmexample.activity.AddActivity.Companion.EXTRA_CONTACT_ID
import pe.andy.mvvmexample.activity.AddActivity.Companion.EXTRA_CONTACT_NAME
import pe.andy.mvvmexample.activity.AddActivity.Companion.EXTRA_CONTACT_NUMBER
import pe.andy.mvvmexample.adapter.ContactAdapter
import pe.andy.mvvmexample.databinding.ActivityMainBinding
import pe.andy.mvvmexample.model.Contact
import pe.andy.mvvmexample.model.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        setSupportActionBar(activityMainBinding.toolbar)

        initFabButton()

        val adapter = initRecyclerView()
        initContactViewModel(adapter)
    }

    fun initFabButton() {
        activityMainBinding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun initRecyclerView(): ContactAdapter {
        val adapter = ContactAdapter(
            { contact ->
                val intent = Intent(this, AddActivity::class.java)
                intent.apply {
                    putExtra(EXTRA_CONTACT_ID, contact.id)
                    putExtra(EXTRA_CONTACT_NAME, contact.name)
                    putExtra(EXTRA_CONTACT_NUMBER, contact.number)
                }
                startActivity(intent)
            },
            { contact ->
                deleteDialog(contact)
            })

        activityMainBinding.contentMain.apply {
            mainRecyclerview.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(this@MainActivity)
                setHasFixedSize(true)
            }

            mainButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(intent)
            }
        }

        return adapter
    }

    fun initContactViewModel(adapter: ContactAdapter) {
        contactViewModel = ViewModelProviders.of(this)
            .get(ContactViewModel::class.java)

        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            // Update UI
            adapter.setContact(contacts)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteDialog(contact: Contact) {
        AlertDialog.Builder(this)
            .setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
            .show()
    }
}
