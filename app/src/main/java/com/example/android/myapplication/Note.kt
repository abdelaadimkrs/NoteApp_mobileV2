package com.example.android.myapplication

class Note{

    var noteID:Int?=null
    var noteTitle:String?=null
    var noteDescription:String?=null

    constructor(noteID: Int?, noteTitle: String?, noteDescription: String?) {
        this.noteID = noteID
        this.noteTitle = noteTitle
        this.noteDescription = noteDescription
    }
}