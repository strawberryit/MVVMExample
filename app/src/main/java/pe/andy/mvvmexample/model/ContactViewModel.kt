package pe.andy.mvvmexample.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pe.andy.mvvmexample.repository.ContactRepository

class ContactViewModel(application: Application): AndroidViewModel(application) {

    val TAG: String = javaClass.simpleName

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>> {
        Log.d("MVVMExample", "getAll called")
        return contacts
    }

    fun insert(contact: Contact)
            = repository.insert(contact)

    fun delete(contact: Contact)
            = repository.delete(contact)
}