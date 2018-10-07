package pri.com.barcodereader.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import pri.com.barcodereader.R
import pri.com.barcodereader.databinding.ItemBarCodeBinding
import pri.com.barcodereader.model.BarcodeData



/**
 * Created by Priyanka on 10/6/18.
 */

class BarcodeListAdapter(var context: Context) : RecyclerView.Adapter<BarcodeListAdapter.ViewHolder>() {

    var itemList:ArrayList<BarcodeData>

    init {
        this.itemList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemBarCodeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_bar_code, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = itemList.get(position)
        holder.setData(data)
    }

    fun setData(itemList: ArrayList<BarcodeData>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
    fun addData(item: BarcodeData) {
        itemList.add(item)
        notifyItemInserted(itemList.size-1)
    }

    inner class ViewHolder(var binding: ItemBarCodeBinding) : RecyclerView.ViewHolder(binding.root), OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            val context = itemView.context
            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
            builder.setTitle("Scanned on: "+binding.tvScannedDate.text)

            // Display a message on alert dialog
            builder.setMessage(binding.tvScannedValue.text)
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
            try {
                val textView = dialog.window.decorView.findViewById(android.R.id.message) as TextView
                textView.setTextIsSelectable(true)
            } catch (e: Exception) {
                // Oups!
            }

        }

        fun setData(data: BarcodeData) {
            binding.data = data
        }

    }
}