package com.zzuh.filot_shoppings_admin.data

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore.MediaColumns
import java.io.FileNotFoundException

class LocalImage {
    private var _uri: Uri? = null
    private var _contentResolver: ContentResolver? = null
    private var _extension: String? = null
    private var _path: String? = null
    private var _name: String? = null
    private var _isImage: Boolean = false

    val fileExtension get() = _extension
    val filePath get() = _path
    val fileName get() = _name
    val isImage get() = _isImage

    var contentResolver: ContentResolver? get() = _contentResolver
        set(value) {_contentResolver = value}
    var uri: Uri? get() = _uri
        set(value) {_uri = value}
    var isReady = false

    fun fetchImage() {
        if(_contentResolver == null || _uri == null) return
        val projection = arrayOf(MediaColumns.DATA)
        val cursor: Cursor? = _contentResolver!!.query(_uri!!, projection, null, null, null)
        cursor?.apply {
            val column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA)
            cursor.moveToFirst()
            _path = cursor.getString(column_index)
            _path?.apply {
                _extension = _path!!.substring(_path!!.lastIndexOf(".") + 1)
                _name = _path!!.substring(_path!!.lastIndexOf("/") + 1)
            }
        }
        try {
            _isImage = (_extension == "img" || _extension == "jpg" || _extension == "jpeg" || _extension == "gif" || _extension == "png")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            _isImage = false
        }
    }
    fun getIntent(): Intent{
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)

        return intent
    }
    fun clear(){
        _uri = null
        _contentResolver = null
        _extension = null
        _path = null
        _name = null
        _isImage = false
    }
}