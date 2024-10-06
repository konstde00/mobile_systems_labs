package com.konstde00.milkmobileapp.viewmodel

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.konstde00.milkmobileapp.data.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsViewModel : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> get() = _contacts

    fun loadContacts(contentResolver: ContentResolver) {

        viewModelScope.launch {
            val contactList = withContext(Dispatchers.IO) {
                val contactsMap = mutableMapOf<Long, Contact>()

                val cursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    arrayOf(
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    null, null, null
                )

                cursor?.use {
                    val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
                    val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    while (it.moveToNext()) {

                        val id = it.getLong(idIndex)
                        val name = it.getString(nameIndex) ?: ""
                        val number = it.getString(numberIndex) ?: ""

                        if (name.startsWith("Ми")) {
                            val contact = contactsMap.getOrPut(id) { Contact(name, number, "") }
                            contact.number = number
                        }
                    }
                }

                val addressCursor = contentResolver.query(
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                    arrayOf(
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID,
                        ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
                    ),
                    null, null, null
                )

                addressCursor?.use {
                    val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID)
                    val addressIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)

                    while (it.moveToNext()) {
                        val id = it.getLong(idIndex)
                        val address = it.getString(addressIndex) ?: ""

                        contactsMap[id]?.address = address
                    }
                }

                contactsMap.values.toList()
            }
            _contacts.value = contactList
        }
    }
}
