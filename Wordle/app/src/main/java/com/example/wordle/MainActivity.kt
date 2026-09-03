package com.example.wordle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var chances = 3
    var wordToGuess = FourLetterWordList.getRandomFourLetterWord().uppercase()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val validTries = findViewById<TextView>(R.id.ChancesView)

        val guessBox = findViewById<EditText>(R.id.GuessBox)
        val submitButton = findViewById<Button>(R.id.submit)

        val guess1 = findViewById<TextView>(R.id.Guess1)
        val guess2 = findViewById<TextView>(R.id.Guess2)
        val guess3 = findViewById<TextView>(R.id.Guess3)

        val result1 = findViewById<TextView>(R.id.Result1)
        val result2 = findViewById<TextView>(R.id.Result2)
        val result3 = findViewById<TextView>(R.id.Result3)
        val ResetButton = findViewById<Button>(R.id.Reset)

        fun updateChances(){
            validTries.text = chances.toString()
        }

        fun getWord(){

        }

        updateChances()


        submitButton.setOnClickListener {

            val guess = guessBox.text.toString().uppercase()

            if (guess.length != 4) {
                Toast.makeText(this, "Not a 4 letter word", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            when (chances) {
                3 -> {
                    result1.text = checkGuess(guess)
                }

                2 -> {
                    result2.text = checkGuess(guess)
                }

                1 -> {
                    result3.text = checkGuess(guess)
                }
            }

            if (guess == wordToGuess) {
                Toast.makeText(this, "You guessed the word!", Toast.LENGTH_LONG).show()

                submitButton.isEnabled = false
                ResetButton.isEnabled = true
            } else {
                chances--
                updateChances()
                if (chances == 0) {
                    Toast.makeText(this, "You lost! The word was $wordToGuess", Toast.LENGTH_LONG).show()

                    submitButton.isEnabled = false
                    ResetButton.isEnabled = true

                }
            }

            guessBox.text.clear()
        }
        ResetButton.setOnClickListener {
            Toast.makeText(this,"Resetting Game...",Toast.LENGTH_SHORT).show()
            chances = 3
            updateChances()
            guessBox.text.clear()
            result1.text = ""
            result2.text = ""
            result3.text = ""
            submitButton.isEnabled = true
            ResetButton.isEnabled = false
            wordToGuess = FourLetterWordList.getRandomFourLetterWord().uppercase()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }
    }


    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
}