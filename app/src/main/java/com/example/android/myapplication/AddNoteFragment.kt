package com.example.android.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_note.*

class AddNoteFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }
        var id:Int?=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_note.setOnClickListener{
            addNote()
        }
        setHasOptionsMenu(true)
        //check if it edit

        id= arguments?.getInt("ID")
        if(id!=0){
            val title = arguments?.getString("Title")
            note_title.setText(title)
            val description= arguments?.getString("Description")
            note_des.setText(description)
        }

    }

    fun addNote() {
        val title = note_title.text.toString()
        val des = note_des.text.toString()
        val values = ContentValues()
        values.put("Title",title)
        values.put("Description",des)
        val DbManager = DbManager(this!!.activity!!)
        if(id!=0)
        {
            val selectionArgs= arrayOf(id.toString())
            val id= DbManager.update(values,"ID=?",selectionArgs)
            if(id>0) {
                Toast.makeText(this!!.activity!!,"Record is updated",Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this!!.activity!!,"Fail to update Record",Toast.LENGTH_LONG).show()
            }
        }else{
            val id= DbManager.insertNotes(values)
            if(id>0) {
                Toast.makeText(this!!.activity!!,"Record is Added",Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this!!.activity!!,"Fail to add Record",Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.backnoteBu->{
               view!!.findNavController().navigate(R.id.listNotesFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
