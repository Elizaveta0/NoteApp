package space.ex.myapplication

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.coroutines.launch
import space.ex.myapplication.database.NotesDatabase
import space.ex.myapplication.entities.Notes
import java.util.*

class CreateNoteFragment : BaseFragment() {
    var currentDate:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CreateNoteFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        tvDataTime.text = currentDate

        imgDone.setOnClickListener {
            saveNote()
            replaceFragment(HomeFragment.newInstance(), true)
        }

        imgBack.setOnClickListener {
            replaceFragment(HomeFragment.newInstance(), true)
        }
    }

    private fun saveNote(){
        if(etNoteTitle.text.isNullOrEmpty() ){
            Toast.makeText(context, "Note Title is Required", Toast.LENGTH_SHORT).show()
        }
        if(etNoteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Note Sub Title is Required", Toast.LENGTH_SHORT).show()
        }
        if(etNoteDescription.text.isNullOrEmpty()){
            Toast.makeText(context, "Note Description is Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = etNoteTitle.text.toString()
            notes.subTitle = etNoteSubTitle.text.toString()
            notes.noteText = etNoteDescription.text.toString()
            notes.dateTime = currentDate
            context?.let {
//                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                etNoteDescription.setText("")
                etNoteSubTitle.setText("")
                etNoteTitle.setText("")
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, istransition:Boolean){
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if(istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
            fragmentTransition.commit()
        }
        fragmentTransition.replace(R.id.frame_loyout, fragment).addToBackStack(fragment.javaClass.simpleName)
    }
}