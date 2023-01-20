package com.setik.pupuk.ui_admin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.setik.pupuk.ui_admin.data.Pupuk

class PupukModel: ViewModel() {
    private val dbpupuk = FirebaseDatabase.getInstance().getReference("pupuk")

    private val _result = MutableLiveData<Exception?>()
    val result : LiveData<Exception?> get() = _result

    private val _pupuk = MutableLiveData<Pupuk>()
    val pupuk : LiveData<Pupuk> get() = _pupuk

    fun addPupuk(pupuk: Pupuk){
        pupuk.id = dbpupuk.push().key

        dbpupuk.child(pupuk.id!!).setValue(pupuk).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    private val childEventListener = object: ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val pupuk = snapshot.getValue(Pupuk::class.java)
            pupuk?.id = snapshot.key
            _pupuk.value = pupuk!!

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val pupuk = snapshot.getValue(Pupuk::class.java)
            pupuk?.id = snapshot.key
            _pupuk.value = pupuk!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val pupuk = snapshot.getValue(Pupuk::class.java)
            pupuk?.id = snapshot.key
            pupuk?.isDeleted = true
            _pupuk.value = pupuk!!

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }
    }
    fun getRealtimeUp(){
        dbpupuk.addChildEventListener(childEventListener)
    }
    fun updatePupuk(pupuk: Pupuk){
        dbpupuk.child(pupuk.id!!).setValue(pupuk).addOnCompleteListener {
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    fun  deletePupuk(pupuk: Pupuk){
        dbpupuk.child(pupuk.id!!).setValue(null).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        dbpupuk.removeEventListener(childEventListener)
    }

}