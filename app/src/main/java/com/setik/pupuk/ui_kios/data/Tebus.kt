package com.setik.pupuk.ui_kios.data

import com.google.firebase.database.Exclude

class Tebus(
    @get: Exclude
    var id: String? = null,
    var id_kios: String? = null,
    var id_pupuk: String? = null,
    var nama_kios: String? = null,
    var nama_pupuk: String? = null,
    var jumlah: String? = null,
    var tanggal: String? = null,
    @get: Exclude
    var isDeleted : Boolean = false
){
    override fun equals(other: Any?): Boolean {
        return if (other is Tebus){
            other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode()?:0
        result = 31 * result + (id_kios?.hashCode()?:0)
        result = 31 * result + (id_pupuk?.hashCode()?:0)
        result = 31 * result + (nama_pupuk?.hashCode()?:0)
        result = 31 * result + (jumlah?.hashCode()?:0)
        result = 31 * result + (tanggal?.hashCode()?:0)
        return result
    }
}