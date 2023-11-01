package com.android.tsmc.data.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.tsmc.R
import com.android.tsmc.data.models.StudentCreateRequest
import com.android.tsmc.data.models.StudentEditRequest
import com.android.tsmc.data.models.User
import com.android.tsmc.utils.AppPreferences
import com.android.tsmc.viewnodels.UserViewModel

class StudentAdapter(val c: Context, val studentList: ArrayList<User>, val userViewMode: UserViewModel) :
    RecyclerView.Adapter<StudentAdapter.userViewHolder>() {

//    lateinit var studentList: ArrayList<StudentCreateRequest>

    /* fun addStudents(list: ArrayList<StudentCreateRequest>)
     {
         studentList.addAll(list)
     }*/

    inner class userViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView
        var mail: TextView
        var mMenu: ImageView

        init {
            name = v.findViewById<TextView>(R.id.stName)
            mail = v.findViewById<TextView>(R.id.stMail)
            mMenu = v.findViewById<ImageView>(R.id.mMenu)
            mMenu.setOnClickListener { popupMenus(it) }

        }

        private fun popupMenus(v: View) {
            val position = studentList[adapterPosition]
            val popupMenu = PopupMenu(c, v)
            popupMenu.inflate(R.menu.show_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editText -> {
                        val v = LayoutInflater.from(c).inflate(R.layout.add_students, null)
                        val name = v.findViewById<EditText>(R.id.userName)
                        val UseMail = v.findViewById<EditText>(R.id.userMail)
                        val Userpass = v.findViewById<EditText>(R.id.userPassword)
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.username = name.text.toString()
                                position.email = UseMail.text.toString()
                                position.password = Userpass.text.toString()
                                val studentEditRequest = StudentEditRequest(UseMail.text.toString(),position.role,name.text.toString())
                                userViewMode.editStudentInformation(AppPreferences.token,position._id,studentEditRequest)
                                notifyDataSetChanged()
                                Toast.makeText(c, "User Information is Edited", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }

                    R.id.delete -> {
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.baseline_warning_24)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes") { dialog, _ ->
                                studentList.removeAt(adapterPosition)
                                userViewMode.deleteStudentById(AppPreferences.token,position._id)
                                notifyDataSetChanged()
                                Toast.makeText(c, "Deleted this Information", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true

                    }

                    else -> true
                }

            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_view, parent, false)
        return userViewHolder(v)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val newList = studentList[position]
        holder.name.text = newList.username
        holder.mail.text = newList.email
    }


}