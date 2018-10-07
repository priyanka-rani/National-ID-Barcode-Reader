package pri.com.barcodereader.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Priyanka on 10/3/18.
 */
@Entity(tableName = "barcode")
class BarcodeData(@PrimaryKey(autoGenerate = true) var id: Int = 0, @ColumnInfo(name = "loginUserId") var barCodeValue: String? = null, @ColumnInfo(name = "userId") var dateTime: String? = null) : Serializable

