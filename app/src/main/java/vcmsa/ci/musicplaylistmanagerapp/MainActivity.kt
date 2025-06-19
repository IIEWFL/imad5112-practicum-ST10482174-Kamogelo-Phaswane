package vcmsa.ci.musicplaylistmanagerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val songTitle = mutableListOf<String>()
    private val artistname = mutableListOf<String>()
    private val ratingStr = mutableListOf<Int>()
    private val comments = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val addButton: Button = findViewById(R.id.btnAddToPlaylist)
        val secondscreenButton: Button = findViewById(R.id.btnSecondScreen)
        val exitButton: Button = findViewById(R.id.btnExit)

        addButton.setOnClickListener {
            showAddItemDialog()
        }

        secondscreenButton.setOnClickListener {
            if (songTitle.isNotEmpty()) {
                val intent = Intent(this, MainActivity2::class.java)
                intent.putStringArrayListExtra("song", ArrayList(songTitle))
                intent.putStringArrayListExtra("artist", ArrayList(artistname)) // Fix the key here
                intent.putIntegerArrayListExtra("rating", ArrayList(ratingStr))
                intent.putStringArrayListExtra("comments", ArrayList(comments))
                startActivity(intent)
            } else {
                Snackbar.make(secondscreenButton, "Song Title is empty. Add song first.", Snackbar.LENGTH_SHORT).show()
            }
        }

        exitButton.setOnClickListener {
            finishAffinity()
            exitProcess(0)
        }
    }

    private fun showAddItemDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Song")

        val view = layoutInflater.inflate(R.layout.dialog_add_item, null)null)
        val songnameEditText: EditText = view.findViewById(R.id.editTextSongTitle)
        val artistnameEditText: EditText = view.findViewById(R.id.editTextArtistName)
        val ratingEditText: EditText = view.findViewById(R.id.editTextRating)
        val commentsEditText: EditText = view.findViewById(R.id.editTextUserComments)

        builder.setView(view)

        builder.setPositiveButton("Add") { dialog, _ ->
            val song = songnameEditText.text.toString().trim()
            val artist = artistnameEditText.text.toString().trim()
            val ratingStr = ratingEditText.text.toString().trim()
            val comments = commentsEditText.text.toString().trim()

            if (song.isEmpty() || artist.isEmpty() || ratingStr.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "Item name, category, and quantity cannot be empty.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            val rating = ratingStr.toIntOrNull()
            if (rating == null || rating < 1 || rating > 5) { // Fix the rating condition
                Snackbar.make(findViewById(android.R.id.content), "Invalid rating. Please enter a number between 1 and 5.", Snackbar.LENGTH_SHORT).show()
                return@setPositiveButton
            }

            songTitle.add(song)
            this.artistname.add(artist)
            this.ratingStr.add(rating)
            this.comments.add(comments)

            Snackbar.make(findViewById(android.R.id.content), "$song added to the playlist.", Snackbar.LENGTH_SHORT).show() // Fix the snackbar message
            dialog.dismiss()

            val seconscreenButton: Button = findViewById(R.id.btnSecondScreen)
            seconscreenButton.setOnClickListener {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}