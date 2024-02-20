package com.kawamura.kawachi.nauapp

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class CalenderActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var etMemo: EditText
    private lateinit var btnAddEvent: Button
    private lateinit var tvEvent: TextView
    private lateinit var selectedDate: String
    private var events: MutableList<Event> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)



        calendarView = findViewById(R.id.calendarView)
        etMemo = findViewById(R.id.etMemo)
        btnAddEvent = findViewById(R.id.btnAddEvent)
        tvEvent = findViewById(R.id.tvEvent)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, month, dayOfMonth)
            selectedDate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(selectedCalendar.time)
        }

        btnAddEvent.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, month, dayOfMonth)
                selectedDate = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(selectedCalendar.time)

                val memo = etMemo.text.toString()

                if (memo.isNotEmpty()) {
                    val event = Event(selectedDate, memo)
                    events.add(event)
                    Toast.makeText(
                        this,
                        "予定を追加しました。\n日付: $selectedDate\n予定: $memo",
                        Toast.LENGTH_LONG
                    ).show()
                    updateEventText(selectedDate)
                } else {
                    Toast.makeText(this, "メモを入力してください。", Toast.LENGTH_SHORT).show()
                }
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun updateEventText(date: String) {
        val eventsForDate = events.filter { it.date == date }
        if (eventsForDate.isNotEmpty()) {
            val memoText = eventsForDate.joinToString(separator = "\n") { it.memo }
            tvEvent.text = memoText
        } else {
            tvEvent.text = "予定はありません"
        }
    }
}