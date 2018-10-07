package pri.com.barcodereader.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pri.com.barcodereader.model.BarcodeData;

/**
 * Created by Priyanka on 10/3/18.
 */

@Dao
public interface BarcodeDao {

    @Query("SELECT * FROM barcode")
    List<BarcodeData> getAllbarcode();

    @Query("SELECT * FROM barcode where id LIKE  :id")
    BarcodeData findbarcodeById(int id);

    @Query("SELECT COUNT(*) from barcode")
    int countbarcode();

    @Insert
    void insertbarcode(BarcodeData... barcodeData);

    @Delete
    void deletebarcode(BarcodeData BarcodeData);

}
