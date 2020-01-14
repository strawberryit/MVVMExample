package pe.andy.mvvmexample.repository

import android.app.Application
import androidx.lifecycle.LiveData
import pe.andy.mvvmexample.dao.ContactDao
import pe.andy.mvvmexample.database.ContactDatabase
import pe.andy.mvvmexample.model.Contact
import kotlin.concurrent.thread

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll()
            = contacts

    fun insert(contact: Contact) {
        try {
            thread(start = true) {
                contactDao.insert(contact)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun delete(contact: Contact) {
        try {
            thread(start = true) {
                contactDao.delete(contact)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}