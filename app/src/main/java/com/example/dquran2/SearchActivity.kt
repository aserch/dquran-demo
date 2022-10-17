package com.example.dquran2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.data.Quran
import com.example.dquran2.data.QuranDatabase
import com.example.dquran2.data.playlist.MyPlayerService
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist

class SearchActivity : AppCompatActivity(R.layout.activity_search) {

    private lateinit var database: QuranDatabase
    private lateinit var searchHere: LinearLayout

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = QuranDatabase.getInstance(this)
        val search = findViewById<TextInputEditText>(R.id.input_search)
        val back = findViewById<ImageButton>(R.id.back)
        searchHere = findViewById(R.id.search_here)
        recyclerView = findViewById(R.id.recycler_view_search)


        search.setOnEditorActionListener { textView, id, event ->
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                val query = "%${textView.text}%"

                lifecycleScope.launch {
                    searchHere.isVisible = false
                    val quranList = database.quranDao().searchAyah(query)
                    setAdapter(quranList)
                }
            }
            false
        }

        back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setAdapter(quranList: List<Quran>) {

        val adapter = SearchAdapter(quranList)
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val playerClient = PlayerClient.newInstance(this, MyPlayerService::class.java)

        playerClient.addOnPlayingMusicItemChangeListener { musicItem, position, playProgress ->
            recyclerView.smoothScrollToPosition(position)
            Toast.makeText(this, "ayat ${position + 1}", Toast.LENGTH_SHORT).show()
            if (position == quranList.lastIndex) {
                playerClient.playMode = PlayMode.SINGLE_ONCE
            }
        }


        adapter.playClickListener = {

            val gambarUrl =
                "https://i1.sndcdn.com/artworks-000143990330-ap256c-t500x500.jpg"

            val surah = String.format("%03d", it.surahNumber)
            val ayah = String.format("%03d", it.ayahNumber)

            val audioUrl = "https://www.everyayah.com/data/Hani_Rifai_192kbps/$surah$ayah.mp3"

            val audio = MusicItem.Builder()
                .setTitle("${it.SurahName} : ${it.ayahNumber}")
                .setArtist("Hani ar-Rifai")
                .setAlbum("Al Quran Recitation")
                .autoDuration()
                .setUri(audioUrl)
                .setIconUri(gambarUrl)
                .build()

            val playlist = Playlist.Builder()
                .append(audio)
                .build()

            playerClient.connect { isConnected ->
                Log.d("hola", isConnected.toString())
                playerClient.setPlaylist(playlist, true)
                playerClient.playMode = PlayMode.SINGLE_ONCE
            }

        }
        adapter.shareItemClickListener = {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val shareItem = "${it.ayahText} \n ${it.translation}"
            intent.putExtra(Intent.EXTRA_TEXT, shareItem)
            val chooser = Intent.createChooser(intent, "Share dengan")
            startActivity(chooser)
        }

        adapter.copyItemClickListener = {
            val copyItem = "${it.ayahText} \n ${it.translation}"
            val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("copy ayat", copyItem)
            clipboard.setPrimaryClip(clipData)

            Toast.makeText(this, "ayat telah disalin", Toast.LENGTH_SHORT).show()
        }

    }


}