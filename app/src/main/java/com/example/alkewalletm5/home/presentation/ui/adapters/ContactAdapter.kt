import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.alkeapi.data.network.response.AccountDataResponse
import com.example.alkewalletm5.R
import com.example.alkewalletm5.data.local.model.User
import com.example.alkewalletm5.databinding.ContactItemBinding

class ContactAdapter(
    context: Context,
    private val accounts: List<AccountDataResponse>,
    private val users: List<User>
) : ArrayAdapter<AccountDataResponse>(context, 0, accounts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ContactItemBinding
        val view: View

        if (convertView == null) {
            binding = ContactItemBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ContactItemBinding
            view = convertView
        }

        val account = getItem(position)
        val user = users.find { it.id == account?.userId }

        binding.apply {
            // Establecer la imagen (ejemplo con imagen predeterminada)
            imgProfileContact.setImageResource(R.drawable.default_profile_image)

            // Configurar nombre y email del usuario
            if (user != null && user.first_name != null) {
                txtNameContact.text = "${user.first_name} ${user.last_name}"
                txtEmailContact.text = user.email ?: "Unknown Email"
            } else {
                txtNameContact.text = "Unknown User"
                txtEmailContact.text = "Unknown Email"
            }
        }

        return view
    }
}
