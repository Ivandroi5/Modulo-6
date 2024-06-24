package com.example.alkewalletm5.home.presentation.ui.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alkewalletm5.data.local.entities.Profile
import com.example.alkewalletm5.databinding.InfoProfileItemBinding
private val TAG = ProfileAdapter::class.java.simpleName

/**
 * Clase adaptadora para pintar los datos del perfil en el home
 * @param profiles lista de perfiles
 * @param onItemClickListener lambda que recibe el perfil seleccionado
 */
class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    lateinit var onItemClickListener: (Profile) -> Unit

    var profiles = mutableListOf<Profile>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     *  Crea el viewholder
     *  @param bindingItem el binding que infla el layout que pinta el recyclerview
     *  @return profileViewHolder el viewholder que pinta el recyclerview
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileAdapter.ProfileViewHolder {

        val bindingItem =
            InfoProfileItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)

        return ProfileViewHolder(bindingItem)
    }

    /**
     * @param position la posición del perfil en la lista
     */
    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        val profile: Profile = profiles[position]
        holder.bind(profile)
    }

    /**
     *  @return la cantidad de perfiles en la lista
     */
    override fun getItemCount(): Int {
        return profiles.size
    }

    /**
     * Clase viewholder que pinta el perfil en el recyclerview con el fragmento
     *
     */
    inner class ProfileViewHolder(private var bindingItem: InfoProfileItemBinding) :
        RecyclerView.ViewHolder(bindingItem.root) {

        /**
         * Pinta los datos del xml con los datos del perfil
         * @param profile el perfil que se va a pintar
         */

        fun bind(profile: Profile) {

            with(profile) {
                Log.d(TAG, "bind: $name")
                bindingItem.imageProfileHome.setImageResource(imageProfile)
                bindingItem.textWelcomeHome.text = "Hola,"
                bindingItem.balanceHome.text = "$ " + balance

            }
            bindingItem.root.setOnClickListener {
                if (::onItemClickListener.isInitialized)
                    onItemClickListener(profile)

                else
                    Log.e(TAG, "onItemClickList is not initialized")

            }

        }
    }

}