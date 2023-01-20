package com.setik.pupuk.ui_admin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.setik.pupuk.ui_admin.data.Kios

class KiosModel: ViewModel() {
    private val dbkios = FirebaseDatabase.getInstance().getReference("kios")

    private val _result = MutableLiveData<Exception?>()
    val result : LiveData<Exception?> get() = _result

    private val _kios = MutableLiveData<Kios>()
    val kios : LiveData<Kios> get() = _kios

    fun addKios (kios: Kios){
        kios.id = dbkios.push().key

        dbkios.child(kios.id!!).setValue(kios).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val kios = snapshot.getValue(Kios::class.java)
            kios?.id = snapshot.key
            _kios.value = kios!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val kios = snapshot.getValue(Kios::class.java)
            kios?.id = snapshot.key
            _kios.value = kios!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val kios = snapshot.getValue(Kios::class.java)
            kios?.id = snapshot.key
            kios?.isDeleted = true
            _kios.value = kios!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    }
    fun getRealtimeUp() {
        dbkios.addChildEventListener(childEventListener)
    }
    fun updateKios(kios: Kios){
        dbkios.child(kios.id!!).setValue(kios).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    fun deleteKios(kios: Kios){
        dbkios.child(kios.id!!).removeValue().addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        dbkios.removeEventListener(childEventListener)
    }
}