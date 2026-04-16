package com.example.eventplannerapp.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.eventplannerapp.R
import com.example.eventplannerapp.db
import com.example.eventplannerapp.model.Event
import java.util.Calendar
import android.app.DatePickerDialog
import android.app.TimePickerDialog
class EventListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_event_list, container, false)
        val listView = view.findViewById<ListView>(R.id.eventListView)

        loadEvents(listView)

        return view
    }

    private fun loadEvents(listView: ListView) {

        val events = db.eventDao().getAllEvents()

        val displayList = events.map {
            "${it.title} - ${it.datetime}"
        }

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            displayList
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->

            val event = events[position]

            AlertDialog.Builder(requireContext())
                .setTitle("Event Details")
                .setMessage(
                    "Title: ${event.title}\n\n" +
                            "Date & Time: ${event.datetime}\n\n" +
                            "Location: ${event.location}\n\n" +
                            "Category: ${event.category}"
                )

                //  EDIT BUTTON
                .setPositiveButton("Edit") { _, _ ->

                    val dialogView = layoutInflater.inflate(R.layout.fragment_add_event, null)

                    val titleInput = dialogView.findViewById<EditText>(R.id.titleInput)
                    val categoryInput = dialogView.findViewById<EditText>(R.id.categoryInput)
                    val locationInput = dialogView.findViewById<EditText>(R.id.locationInput)
                    val dateInput = dialogView.findViewById<EditText>(R.id.dateTimeInput)
                    val selectedCalendar = Calendar.getInstance()
                    val saveBtn = dialogView.findViewById<Button>(R.id.saveBtn)
                    saveBtn.visibility = View.GONE

                    dateInput.setOnClickListener {

                        val calendar = Calendar.getInstance()

                        val datePickerDialog = DatePickerDialog(
                            requireContext(),
                            { _, y, m, d ->

                                TimePickerDialog(requireContext(), { _, hour, minute ->

                                    selectedCalendar.set(y, m, d, hour, minute)

                                    val formatted = String.format(
                                        "%04d-%02d-%02d %02d:%02d",
                                        y, m + 1, d, hour, minute
                                    )

                                    dateInput.setText(formatted)

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

//  Prevent past dates
                        datePickerDialog.datePicker.minDate = System.currentTimeMillis()

                        datePickerDialog.show()
                    }
                    // Pre-fill data
                    titleInput.setText(event.title)
                    categoryInput.setText(event.category)
                    locationInput.setText(event.location)
                    dateInput.setText(event.datetime)

                    AlertDialog.Builder(requireContext())
                        .setTitle("Edit Event")
                        .setView(dialogView)

                        .setPositiveButton("Update") { _, _ ->

                            val updatedEvent = event.copy(
                                title = titleInput.text.toString(),
                                category = categoryInput.text.toString(),
                                location = locationInput.text.toString(),
                                datetime = dateInput.text.toString()
                            )

                            db.eventDao().update(updatedEvent)

                            Toast.makeText(context, "Event Updated!", Toast.LENGTH_SHORT).show()
                            loadEvents(listView)
                        }

                        .setNegativeButton("Cancel", null)
                        .show()
                }

                // DELETE BUTTON
                .setNegativeButton("Delete") { _, _ ->

                    AlertDialog.Builder(requireContext())
                        .setTitle("Delete Event")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes") { _, _ ->

                            db.eventDao().delete(event)

                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                            loadEvents(listView)
                        }
                        .setNegativeButton("No", null)
                        .show()
                }

                //  CLOSE BUTTON
                .setNeutralButton("Close", null)

                .show()
        }
    }
}