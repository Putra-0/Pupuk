package com.setik.pupuk.ui_admin.data

import com.google.firebase.database.Exclude

class Kios(

    @get: Exclude
    var id: String? = null,
    var nama: String? = null,
    var email: String? = null,
    var password: String? = null,
    var alamat: String? = null,
    var no_telp: String? = null,
    @get: Exclude
    var isDeleted : Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Kios){
            other.id == id
        }else false
    }
    override fun hashCode(): Int {
        var result = id?.hashCode()?:0
        result = 31 * result + (nama?.hashCode()?:0)
        result = 31 * result + (email?.hashCode()?:0)
        result = 31 * result + (password?.hashCode()?:0)
        result = 31 * result + (alamat?.hashCode()?:0)
        result = 31 * result + (no_telp?.hashCode()?:0)
        return result
    }
}