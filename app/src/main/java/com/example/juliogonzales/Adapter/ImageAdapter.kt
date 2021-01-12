import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.juliogonzales.Activities.ImagesReportActivity
import com.example.juliogonzales.Activities.imageCallback
import com.example.juliogonzales.R
import com.example.juliogonzales.Utils.GlideApp
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.popup_delete_image.view.*
import org.jetbrains.anko.layoutInflater




class ImageAdapter(
    val context: Context,
    private val imageList: MutableList<String>,
    private val storageReference: StorageReference,
    id: String,
    private val booleanEditable: Boolean,
    private val callback: imageCallback
): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_image_report, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = imageList[position]
        val counter = position+1

        holder.counter.text = "$counter/${imageList.size} photos added"

        val url = storageReference.storage.getReferenceFromUrl(order)

        if(booleanEditable){
            holder.imgReport.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(context)
                dialogBuilder.setTitle("Delete it")

                val view = context.layoutInflater.inflate(R.layout.popup_delete_image,null)
                dialogBuilder.setView(view)

                val alertDialog = dialogBuilder.create()
                alertDialog.show()

                view.btnDelete.setOnClickListener {
                    url.delete().addOnSuccessListener {
                        Toast.makeText(context, "Deleted image", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                        callback.deleteSucces()
                    }
                }
                view.btnCancel.setOnClickListener {
                    alertDialog.dismiss()
                }
            }
        }

        Log.d("BuenoAdapter",order)
        // Create a reference to a file from a Google Cloud Storage URI
        GlideApp.with(context)
            .load(url)
            .into(holder.imgReport)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgReport = itemView.findViewById<ImageView>(R.id.imgFinalEvidence)
        val counter = itemView.findViewById<TextView>(R.id.txtCounter)
    }

}
