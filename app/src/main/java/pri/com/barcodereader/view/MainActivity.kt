package pri.com.barcodereader.view

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import pri.com.barcodereader.R
import pri.com.barcodereader.adapter.BarcodeListAdapter
import pri.com.barcodereader.databinding.ActivityMainBinding
import pri.com.barcodereader.db.AppDatabase
import pri.com.barcodereader.model.BarcodeData
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    private var mResult: String? = null
    private val KEY_STATE = "key_state"

    lateinit var adapter:BarcodeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btStartScan.setOnClickListener {
            val oDesiredFormats = Arrays.asList(*"PDF_417".split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            val integrator = IntentIntegrator(this@MainActivity)
            integrator.initiateScan(oDesiredFormats)
        }
        adapter = BarcodeListAdapter(this@MainActivity)
        adapter.setData(AppDatabase.getAppDatabase(this@MainActivity).barcodeDao().allbarcode as ArrayList<BarcodeData>)
        binding.rvSvcannedIdList.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.i("BarCode", "Cancelled scan")
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Log.i("BarCode", "Scanned")
                mResult = result.contents
                val c = Calendar.getInstance()
                val dateformat = SimpleDateFormat("dd-MMM-yyyy\nhh:mm:ss aa")
                val datetime = dateformat.format(c.time)
                val barcodeData = BarcodeData()
                barcodeData.id = adapter.itemCount+1
                barcodeData.dateTime = datetime
                barcodeData.barCodeValue = mResult
                AppDatabase.getAppDatabase(this@MainActivity).barcodeDao().insertbarcode(barcodeData)
                adapter.addData(barcodeData)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mResult != null) {
            outState.putString(KEY_STATE, mResult)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mResult = savedInstanceState.getString(KEY_STATE, null)
    }
}
