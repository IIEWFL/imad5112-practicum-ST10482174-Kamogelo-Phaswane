package vcmsa.ci.musicplaylistmanagerapp


//Imports necessary packages
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {

    private lateinit var songTitle: ArrayList<String>
    private lateinit var artistname: ArrayList<String>
    private lateinit var ratingStr: ArrayList<Int>
    private lateinit var comments: ArrayList<String>
    private lateinit var displayTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        // Gets song data from the intent
        songTitle = intent.getStringArrayListExtra("song") ?: arrayListOf()
        artistname = intent.getStringArrayListExtra("artist") ?: arrayListOf()
        ratingStr = intent.getIntegerArrayListExtra("rating") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()
        displayTextView = findViewById(R.id.textViewList)

        //Initializes the buttons
        val displayAllButton: Button = findViewById(R.id.btnAllSongs)
        val displayRatingButton: Button = findViewById(R.id.buttonShowRating)
        val backButton: Button = findViewById(R.id.buttonBack)

        //Set Button Click Listener
        displayAllButton.setOnClickListener {
            display()
        }

        displayRatingButton.setOnClickListener {
            displayRating()
        }

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to display all song data

    private fun display() {
        val stringBuilder = StringBuilder()
        if (songTitle.isNotEmpty()) {
            for (i in songTitle.indices) {
                stringBuilder.append("Song: ${songTitle[i]}\n")
                stringBuilder.append("Artist: ${artistname[i]}\n")
                stringBuilder.append("Rating: ${ratingStr[i]}\n")
                stringBuilder.append("Comments: ${comments[i]}\n\n")
            }
            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "Song list is empty."
        }
    }

    //function to display all ratings
    private fun displayRating() {
        val stringBuilder = StringBuilder()
        var found = false
        for (i in songTitle.indices) {
            if (ratingStr[i] >= 1 && ratingStr[i] <= 5) {
                stringBuilder.append("Song: ${songTitle[i]} (Rating: ${ratingStr[i]})\n")
                found = true
            }
        }
        if (found) {
            displayTextView.text = stringBuilder.toString()
        } else {
            displayTextView.text = "No songs with ratings found."
        }
    }
}