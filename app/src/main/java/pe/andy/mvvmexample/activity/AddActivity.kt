package pe.andy.mvvmexample.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import pe.andy.mvvmexample.R
import pe.andy.mvvmexample.databinding.ActivityAddBinding
import pe.andy.mvvmexample.model.Contact
import pe.andy.mvvmexample.model.ContactViewModel

class AddActivity: AppCompatActivity() {

    private lateinit var activityAddBinding: ActivityAddBinding
    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    companion object {
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_add
        )

        contactViewModel = ViewModelProviders.of(this)
            .get(ContactViewModel::class.java)


            if (intent != null
                && intent.hasExtra(EXTRA_CONTACT_ID)
                && intent.hasExtra(EXTRA_CONTACT_NAME)
                && intent.hasExtra(EXTRA_CONTACT_NUMBER)
            ) {
                activityAddBinding.apply {
                    addEdittextName.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
                    addEdittextNumber.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
                    id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
                }
            }

        activityAddBinding.apply {
            addContactButton.setOnClickListener {
                val name = addEdittextName.text.toString().trim()
                val number = addEdittextNumber.text.toString().trim()

                if (name.isEmpty() || number.isEmpty()) {
                    Toast.makeText(this@AddActivity, "Please input fields.", Toast.LENGTH_LONG)
                        .show()
                } else {
                    val initial = name[0].toUpperCase()
                    val contact = Contact(
                        id = id,
                        name = name,
                        number = number,
                        initial = initial
                    )
                    contactViewModel.insert(contact)
                    finish()
                }
            }
        }
    }
}