package com.example.couplesgame

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gameView = GameView(this)
        val tracker = AnniversaryTracker(this)

        // Create a FrameLayout to hold both the game and the button
        val frameLayout = FrameLayout(this)
        frameLayout.addView(gameView) // Add GameView first so it's in the background

        // Create a button for selecting the anniversary date
        val setDateButton = Button(this)
        setDateButton.text = "Set Anniversary"

        //Set the button size to wrap content**
        setDateButton.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, // Width = Wrap Content
            FrameLayout.LayoutParams.WRAP_CONTENT  // Height = Wrap Content
        ).apply {
            gravity = Gravity.BOTTOM
        }

        // Set button click listener to open date picker
        setDateButton.setOnClickListener {
            showDatePicker(tracker, gameView)
        }

        // Add the button to the layout
        frameLayout.addView(setDateButton)

        // Set the FrameLayout as the root view
        setContentView(frameLayout)
    }

    private fun showDatePicker(tracker: AnniversaryTracker, gameView: GameView) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                // Save the new anniversary date
                tracker.setAnniversary(year, month, dayOfMonth)

                // Redraw the game screen with the updated date
                gameView.invalidate()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}