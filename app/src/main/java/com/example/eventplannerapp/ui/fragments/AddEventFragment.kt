package com.example.eventplannerapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.eventplannerapp.R
import com.example.eventplannerapp.db
import com.example.eventplannerapp.model.Event
import java.util.Calendar

class AddEventFragment : Fragment() {
    lateinit var selectedCalendar: Calendar
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_add_event, container, false)

        val title = view.findViewById<EditText>(R.id.titleInput)
        val category = view.findViewById<EditText>(R.id.categoryInput)
        val location = view.findViewById<EditText>(R.id.locationInput)
        val datetime = view.findViewById<EditText>(R.id.dateTimeInput)
        val saveBtn = view.findViewById<Button>(R.id.saveBtn)

        //  DATE + TIME PICKER
        datetime.setOnClickListener {



            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->

                TimePickerDialog(requireContext(), { _, hour, minute ->

                    selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(y, m, d, hour, minute)
                    selectedCalendar.set(Calendar.SECOND, 0)
                    selectedCalendar.set(Calendar.MILLISECOND, 0)

                    val formatted = String.format(
                        "%04d-%02d-%02d %02d:%02d",
                        y, m + 1, d, hour, minute
                    )

                    datetime.setText(formatted)

                },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()

            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = System.currentTimeMillis()

            datePicker.show()
        }

        saveBtn.setOnClickListener {

            val titleText = title.text.toString()
            val dateText = datetime.text.toString()
            val now = Calendar.getInstance()
            if (titleText.isEmpty() && dateText.isEmpty())
            {
                Toast.makeText(context, "Title and date is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (titleText.isEmpty()){
                Toast.makeText(context, "Title is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if (dateText.isEmpty()){
                Toast.makeText(context, "date and time is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            now.set(Calendar.SECOND, 0)
            now.set(Calendar.MILLISECOND, 0)

            if (selectedCalendar.timeInMillis < now.timeInMillis) {
                Toast.makeText(context, "Selected date/time has already passed", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }





            val event = Event(
                title = titleText,
                category = category.text.toString(),
                location = location.text.toString(),
                datetime = dateText
            )

            db.eventDao().insert(event)

            Toast.makeText(context, "Event Saved!", Toast.LENGTH_SHORT).show()

            title.text.clear()
            category.text.clear()
            location.text.clear()
            datetime.text.clear()
        }

        return view
    }
}