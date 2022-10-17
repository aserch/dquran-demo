package com.example.dquran2.ui.ayah

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.TabViewModel
import com.example.dquran2.data.Quran
import com.example.dquran2.data.QuranDatabase
import com.example.dquran2.data.playlist.MyPlayerService
import com.example.dquran2.ui.surah.SurahViewModel
import com.example.dquran2.ui.FootnotesFragment
import com.example.dquran2.ui.SelectPref
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist

class AyatFragment : Fragment(R.layout.fragment_ayat) {


    private val viewModel: TabViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private val ayatViewModel : SurahViewModel by activityViewModels()
    private lateinit var qari : String
    private lateinit var qProfil : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.recycler_view_ayat)
        val database = QuranDatabase.getInstance(requireContext())
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.material_toolbar)


        val juzNumber = arguments?.getInt(JUZ_NUMBER, 1) ?: 1
        val surahNumber = arguments?.getInt(SURAH_NUMBER, 1) ?: 1
        val pageNumber = arguments?.getInt(PAGE_NUMBER, 1) ?: 1
        val surahName = arguments?.getString("surah_name")







        lifecycleScope.launch {


//            search.addTextChangedListener(object : TextWatcher{
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    search.setOnEditorActionListener { v, actionId, event ->
//
//                        false
//                        true
//                    }
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    TODO("Not yet implemented")
//                }
//
//            })


            when (viewModel.getIndex()) {
                0 -> {
                    toolbar.title = "Surat $surahName"
                    val ayahSurahList = database.quranDao().getAyahFromSurah(surahNumber)
                    setAdapter(ayahSurahList)
                }

                1 -> {
                    toolbar.title = "Juz $juzNumber"
                    val ayahJuzList = database.quranDao().getAyahFromJuz(juzNumber)
                    setAdapter(ayahJuzList)
                }

                2 -> {
                    toolbar.title = "Halaman $pageNumber"
                    val ayahPageList = database.quranDao().getAyahFromPage(pageNumber)
                    setAdapter(ayahPageList)
                }
            }


        }
    }

    fun setAdapter(quranList: List<Quran>) {

        qari = ""
        qProfil = ""

        ayatViewModel.getayahList().observe(viewLifecycleOwner){ it ->
            val adapter = AyatAdapter(quranList,it)
            recyclerView.adapter = adapter
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.layoutManager = layoutManager

            when(SelectPref(requireContext()).selectQ){
                "Hani ar Rifai" -> {
                    qari = "Hani_Rifai_192kbps"
                    qProfil = ""
                }
                "Mishary Rasheed Alafasy" ->{
                    qari = "Alafasy_128kbps"
                    qProfil = ""
                }
                "Sahih Ibrahim" -> {
                    qari = "Sahih_Intnl_Ibrahim_Walk_192kbps"
                    qProfil = ""
                }
            }

            val playerClient = PlayerClient.newInstance(requireContext(), MyPlayerService::class.java)

            playerClient.addOnPlayingMusicItemChangeListener { musicItem, position, playProgress ->
                recyclerView.smoothScrollToPosition(position)
                Toast.makeText(requireContext(), "ayat ${position + 1}", Toast.LENGTH_SHORT).show()
                if (position == quranList.lastIndex) {
                    playerClient.playMode = PlayMode.SINGLE_ONCE
                }
            }

            adapter.footnotesClickListener = {
                val bundle = bundleOf(
                    FootnotesFragment.TAG_FOOTNOTES to it.footnotes,
                    FootnotesFragment.TAG_SURAH_NAME to it.SurahName,
                    FootnotesFragment.TAG_AYAH_NUMBER to it.ayahNumber,
                )

                when(it.footnotes == null){
                    true -> {
                        val footnotes = FootnotesFragment().apply {  }
                        footnotes.isHidden
                    }
                    else -> findNavController().navigate(R.id.action_ayatFragment_to_footnotesFragment,bundle)
                }


//                arguments = bundleOf(
//                    FootnotesFragment.TAG_FOOTNOTES to it.footnotes,
//                    FootnotesFragment.TAG_SURAH_NAME to it.SurahName,
//                    FootnotesFragment.TAG_AYAH_NUMBER to it.ayahNumber,
//                )
//                findNavController().navigate(R.id.action_ayatFragment_to_footnotesFragment,arguments)
//                val footnotesBottomSheets = FootnotesFragment().apply {
//
//
//                }
//
//                footnotesBottomSheets.show(parentFragmentManager, "footnotes")
            }

            adapter.playlistClickListener = {

                val playlist = createPlaylist(it)
                playerClient.connect { isConnected ->
                    Log.d("hola", isConnected.toString())
                    playerClient.setPlaylist(playlist, true)
                    playerClient.playMode = PlayMode.PLAYLIST_LOOP

                }


            }
            adapter.playClickListener = {

                val gambarUrl =
                    "https://i1.sndcdn.com/artworks-000143990330-ap256c-t500x500.jpg"

                val surah = String.format("%03d", it.surahNumber)
                val ayah = String.format("%03d", it.ayahNumber)


                val audioUrl = "https://www.everyayah.com/data/$qari/$surah$ayah.mp3"

                val audio = MusicItem.Builder()
                    .setTitle("${it.SurahName} : ${it.ayahNumber}")
                    .setArtist(SelectPref(requireContext()).selectQ.toString())
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
                val clipboard =
                    requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("copy ayat", copyItem)
                clipboard.setPrimaryClip(clipData)

                Toast.makeText(requireContext(), "ayat telah disalin", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun createPlaylist(it: List<Quran>): Playlist {

        val listQuranPlay = mutableListOf<MusicItem>()
        it.forEach { ayat ->
            val formatAyatNumber = String.format("%03d", ayat.ayahNumber)
            val formatSuratNumber = String.format("%03d", ayat.surahNumber)

            val playlistItem = MusicItem.Builder()
                .setTitle(ayat.SurahName!!)
                .setUri("https://www.everyayah.com/data/$qari/$formatSuratNumber$formatAyatNumber.mp3")
                .setArtist(SelectPref(requireContext()).selectQ.toString())
                .setAlbum("Quran Recitation")
                .setIconUri("https://i1.sndcdn.com/artworks-000143990330-ap256c-t500x500.jpg")
                .autoDuration()
                .build()

            listQuranPlay.add(playlistItem)
        }


        return Playlist.Builder()
            .appendAll(listQuranPlay)
            .build()
    }


    companion object {
        const val SURAH_NUMBER = "surah_number"
        const val JUZ_NUMBER = "juz_number"
        const val PAGE_NUMBER = "page_number"

    }
}