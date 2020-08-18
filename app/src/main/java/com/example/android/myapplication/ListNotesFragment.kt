package com.example.android.myapplication

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_list_notes.*
import kotlinx.android.synthetic.main.noteticket.view.*

class ListNotesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val listNotes = ArrayList<Note>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
            querySearch("%")
        var myAdapter =MyNoteAdapter(this!!.activity!!,listNotes)
        lv_notes.adapter=myAdapter
    }

    fun querySearch(noteTitle:String)
    {
        var dbManager=DbManager(this!!.activity!!)
        val projections = arrayOf("ID","Title","Description")
        val selectionArgs = arrayOf(noteTitle)
        val cursor = dbManager.query(projections,"Title like ?",selectionArgs,"Title")

        if(cursor.moveToFirst())
        {
            listNotes.clear()
            do {
                val id = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))
                listNotes.add(Note(id,title,description))
            }while (cursor.moveToNext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.addnoteBu->{
                view!!.findNavController().navigate(R.id.action_listNotesFragment_to_addNoteFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyNoteAdapter: BaseAdapter {
        var listNotesAdapter = ArrayList<Note>()
        var context:Context?=null

        constructor(context: Context,listNoteAdapter:ArrayList<Note>):super(){
            this.context
            this.listNotesAdapter=listNotesAdapter
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.noteticket,null)
            val note = listNotesAdapter[p0]

            myView.tvTitle.text=note.noteTitle
            myView.tvDes.text=note.noteDescription

            myView.ivDelete.setOnClickListener{
                val dbManager=DbManager(this.context!!)
                val selectionAgrs = arrayOf(note.noteID.toString())
                dbManager.delete("ID=?",selectionAgrs)
                querySearch("%")
            }

            myView.ivEdit.setOnClickListener{
                goToUpdate(note)
            }
            return myView
        }

        override fun getItem(p0: Int): Any {
           return listNotesAdapter[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
           return listNotesAdapter.size
        }

        fun goToUpdate(note:Note)
        {
            var bundle= Bundle()
            bundle.putInt("ID",note.noteID!!)
            bundle.putString("Title",note.noteTitle)
            bundle.putString("Description",note.noteDescription)
            view!!.findNavController().navigate(R.id.action_listNotesFragment_to_addNoteFragment,bundle)
        }


    }

}
