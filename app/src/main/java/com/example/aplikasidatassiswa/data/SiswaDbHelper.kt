package com.example.aplikasidatassiswa.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SiswaDbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NIS TEXT NOT NULL UNIQUE,
                $COL_NAMA TEXT NOT NULL,
                $COL_KELAS TEXT NOT NULL,
                $COL_ALAMAT TEXT NOT NULL,
                $COL_GENDER TEXT NOT NULL DEFAULT 'Laki-laki'
            )
            """.trimIndent()
        )
        SEED_DATA.forEach { siswa ->
            val values = ContentValues().apply {
                put(COL_NIS, siswa.nis)
                put(COL_NAMA, siswa.nama)
                put(COL_KELAS, siswa.kelas)
                put(COL_ALAMAT, siswa.alamat)
                put(COL_GENDER, siswa.jenisKelamin)
            }
            db.insert(TABLE, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE")
        onCreate(db)
    }

    fun getAll(search: String = "", kelas: String = "Semua"): List<Siswa> {
        val conditions = mutableListOf<String>()
        val args = mutableListOf<String>()

        if (search.isNotBlank()) {
            conditions.add("($COL_NIS LIKE ? OR $COL_NAMA LIKE ? OR $COL_KELAS LIKE ? OR $COL_ALAMAT LIKE ?)")
            repeat(4) { args.add("%$search%") }
        }
        if (kelas != "Semua") {
            conditions.add("$COL_KELAS = ?")
            args.add(kelas)
        }

        val whereClause = if (conditions.isEmpty()) "" else "WHERE ${conditions.joinToString(" AND ")}"
        val query = "SELECT * FROM $TABLE $whereClause ORDER BY $COL_NAMA ASC"
        return readableDatabase.rawQuery(query, args.toTypedArray()).use { it.toSiswaList() }
    }

    fun getRecent(limit: Int = 3): List<Siswa> =
        readableDatabase.rawQuery(
            "SELECT * FROM $TABLE ORDER BY $COL_ID DESC LIMIT ?",
            arrayOf(limit.toString())
        ).use { it.toSiswaList() }

    fun getById(id: Long): Siswa? =
        readableDatabase.rawQuery(
            "SELECT * FROM $TABLE WHERE $COL_ID = ?",
            arrayOf(id.toString())
        ).use { c -> if (c.moveToFirst()) c.toSiswa() else null }

    fun insert(siswa: Siswa): Long =
        writableDatabase.insert(TABLE, null, siswa.toValues())

    fun update(siswa: Siswa): Int =
        writableDatabase.update(TABLE, siswa.toValues(), "$COL_ID = ?", arrayOf(siswa.id.toString()))

    fun delete(id: Long): Int =
        writableDatabase.delete(TABLE, "$COL_ID = ?", arrayOf(id.toString()))

    fun count(): Int =
        readableDatabase.rawQuery("SELECT COUNT(*) FROM $TABLE", null).use {
            if (it.moveToFirst()) it.getInt(0) else 0
        }

    fun countByGender(jenisKelamin: String): Int =
        readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM $TABLE WHERE $COL_GENDER = ?",
            arrayOf(jenisKelamin)
        ).use { if (it.moveToFirst()) it.getInt(0) else 0 }

    fun getKelasList(): List<String> =
        readableDatabase.rawQuery(
            "SELECT DISTINCT $COL_KELAS FROM $TABLE ORDER BY $COL_KELAS ASC",
            null
        ).use { c ->
            val result = mutableListOf<String>()
            while (c.moveToNext()) result.add(c.getString(c.getColumnIndexOrThrow(COL_KELAS)))
            result
        }

    fun isNisExists(nis: String, excludeId: Long = -1L): Boolean =
        readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM $TABLE WHERE $COL_NIS = ? AND $COL_ID != ?",
            arrayOf(nis.trim(), excludeId.toString())
        ).use { it.moveToFirst() && it.getInt(0) > 0 }

    private fun Siswa.toValues() = ContentValues().apply {
        put(COL_NIS, nis.trim())
        put(COL_NAMA, nama.trim())
        put(COL_KELAS, kelas.trim())
        put(COL_ALAMAT, alamat.trim())
        put(COL_GENDER, jenisKelamin)
    }

    private fun Cursor.toSiswa(): Siswa = Siswa(
        id = getLong(getColumnIndexOrThrow(COL_ID)),
        nis = getString(getColumnIndexOrThrow(COL_NIS)),
        nama = getString(getColumnIndexOrThrow(COL_NAMA)),
        kelas = getString(getColumnIndexOrThrow(COL_KELAS)),
        alamat = getString(getColumnIndexOrThrow(COL_ALAMAT)),
        jenisKelamin = getString(getColumnIndexOrThrow(COL_GENDER))
    )

    private fun Cursor.toSiswaList(): List<Siswa> {
        val result = mutableListOf<Siswa>()
        while (moveToNext()) result.add(toSiswa())
        return result
    }

    companion object {
        private const val DB_NAME = "datassiswa.db"
        private const val DB_VERSION = 2

        private const val TABLE = "siswa"
        private const val COL_ID = "id"
        private const val COL_NIS = "nis"
        private const val COL_NAMA = "nama"
        private const val COL_KELAS = "kelas"
        private const val COL_ALAMAT = "alamat"
        private const val COL_GENDER = "jenis_kelamin"

        private val SEED_DATA = listOf(
            Siswa(
                nis = "2026001",
                nama = "Andi Pratama",
                kelas = "XI RPL 1",
                alamat = "Cibinong, Bogor",
                jenisKelamin = "Laki-laki"
            ),
            Siswa(
                nis = "2026002",
                nama = "Nabila Putri",
                kelas = "XI RPL 2",
                alamat = "Citeureup, Bogor",
                jenisKelamin = "Perempuan"
            ),
            Siswa(
                nis = "2026003",
                nama = "Rizky Ramadhan",
                kelas = "XI RPL 1",
                alamat = "Gunung Putri, Bogor",
                jenisKelamin = "Laki-laki"
            ),
            Siswa(
                nis = "2026004",
                nama = "Siti Aisyah",
                kelas = "XI RPL 2",
                alamat = "Cibinong, Bogor",
                jenisKelamin = "Perempuan"
            ),
            Siswa(
                nis = "2026005",
                nama = "Dimas Pratama",
                kelas = "XI RPL 1",
                alamat = "Bogor",
                jenisKelamin = "Laki-laki"
            )
        )
    }
}
