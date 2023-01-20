package com.setik.pupuk.ui_kios.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.setik.pupuk.ui_admin.data.Kios
import com.setik.pupuk.ui_kios.data.Tebus

class TebusKModel: ViewModel() {

    val dbtebus = FirebaseDatabase.getInstance().getReference("kios"+"/-NM6uYvi4YehovPI678V"+"/tebus")

    private val _result = MutableLiveData<Exception?>()
    val result : LiveData<Exception?> get() = _result

    private val _kios = MutableLiveData<Kios>()
    val kios : LiveData<Kios> get() = _kios

    private val _tebus = MutableLiveData<Tebus>()
    val tebus : LiveData<Tebus> get() = _tebus

    fun addTebus (tebus: Tebus){
        tebus.id = dbtebus.push().key

        dbtebus.child(tebus.id!!).setValue(tebus).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }

    private val childEventListener = object: ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val tebus = snapshot.getValue(Tebus::class.java)
            tebus?.id = snapshot.key
            _tebus.value = tebus!!
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val tebus = snapshot.getValue(Tebus::class.java)
            tebus?.id = snapshot.key
            _tebus.value = tebus!!
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val tebus = snapshot.getValue(Tebus::class.java)
            tebus?.id = snapshot.key
            tebus?.isDeleted = true
            _tebus.value = tebus!!
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            TODO("Not yet implemented")
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    fun getRealtimeUp(){
        dbtebus.addChildEventListener(childEventListener)
    }
    fun updateTebus(tebus: Tebus){
        dbtebus.child(tebus.id!!).setValue(tebus).addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    fun deleteTebus(tebus: Tebus){
        dbtebus.child(tebus.id!!).removeValue().addOnCompleteListener{
            if (it.isSuccessful){
                _result.value = null
            }else{
                _result.value = it.exception
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        dbtebus.removeEventListener(childEventListener)
    }

}