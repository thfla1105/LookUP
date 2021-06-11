package ImageSelect
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import java.R

class PostsAdapter internal constructor(
        private val context: Context,
        private val postItems: List<PostItem>
) :
        RecyclerView.Adapter<PostsAdapter.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PostItem>? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postImageView: RoundedImageView = itemView.findViewById(R.id.imagePost)
//        private val container: LinearLayout = itemView.findViewById(R.id.linear_layout_container)

        fun setPostImage(postItem: PostItem, isActivated: Boolean = false) {
            postImageView.setImageResource(postItem.image)
            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PostItem> =
                object : ItemDetailsLookup.ItemDetails<PostItem>() {
                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): PostItem? = postItems[position]
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

                val itemView = inflater.inflate(R.layout.itemselect_post, parent, false)
                return ListViewHolder(itemView)


    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        tracker?.let {
            holder.setPostImage(postItems[position], it.isSelected(postItems[position]))
        }

    }

    override fun getItemCount(): Int {
        return postItems.size
    }

    fun getItem(position: Int) = postItems[position]
    fun getPosition(imageID: Int) = postItems.indexOfFirst { it.imageID == imageID }
}