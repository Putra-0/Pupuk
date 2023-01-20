package com.setik.pupuk.ui_admin.data

import com.google.firebase.database.Exclude

class Pupuk (
    @get: Exclude
    var id: String? = null,
    var nama_pupuk: String? = null,
    var stok: String? = null,
    var tgl_masuk: String? = null,

    @get: Exclude
    var isDeleted : Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return if (other is Pupuk){
         other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode()?:0
        result = 31 * result + (nama_pupuk?.hashCode()?:0)
        result = 31 * result + (stok?.hashCode()?:0)
        result = 31 * result + (tgl_masuk?.hashCode()?:0)
        return result
    }
}
