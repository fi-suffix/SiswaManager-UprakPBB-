package com.example.aplikasidatassiswa.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.aplikasidatassiswa.data.Siswa
import com.example.aplikasidatassiswa.data.SiswaDbHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SiswaViewModel(app: Application) : AndroidViewModel(app) {

    private val db = SiswaDbHelper(app)

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _selectedKelas = MutableStateFlow("Semua")
    val selectedKelas: StateFlow<String> = _selectedKelas.asStateFlow()

    private val _kelasOptions = MutableStateFlow(listOf("Semua"))
    val kelasOptions: StateFlow<List<String>> = _kelasOptions.asStateFlow()

    private val _siswa = MutableStateFlow<List<Siswa>>(emptyList())
    val siswa: StateFlow<List<Siswa>> = _siswa.asStateFlow()

    private val _recentSiswa = MutableStateFlow<List<Siswa>>(emptyList())
    val recentSiswa: StateFlow<List<Siswa>> = _recentSiswa.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    private val _maleCount = MutableStateFlow(0)
    val maleCount: StateFlow<Int> = _maleCount.asStateFlow()

    private val _femaleCount = MutableStateFlow(0)
    val femaleCount: StateFlow<Int> = _femaleCount.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _siswa.value = db.getAll(_query.value, _selectedKelas.value)
        _recentSiswa.value = db.getRecent(3)
        _totalCount.value = db.count()
        _maleCount.value = db.countByGender("Laki-laki")
        _femaleCount.value = db.countByGender("Perempuan")
        _kelasOptions.value = listOf("Semua") + db.getKelasList()
    }

    fun setQuery(value: String) {
        _query.value = value
        refresh()
    }

    fun setKelas(value: String) {
        _selectedKelas.value = value
        refresh()
    }

    fun getById(id: Long): Siswa? = db.getById(id)

    fun isNisExists(nis: String, excludeId: Long = -1L): Boolean = db.isNisExists(nis, excludeId)

    fun save(siswa: Siswa): Long {
        val result = if (siswa.id == 0L) {
            db.insert(siswa)
        } else {
            db.update(siswa)
            siswa.id
        }
        refresh()
        return result
    }

    fun delete(id: Long) {
        db.delete(id)
        refresh()
    }
}
