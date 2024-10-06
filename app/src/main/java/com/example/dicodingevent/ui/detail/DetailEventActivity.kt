package com.example.dicodingevent.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.dicodingevent.MainActivity
import com.example.dicodingevent.R
import com.example.dicodingevent.data.api.response.DetailEventResponse
import com.example.dicodingevent.data.api.response.Event
import com.example.dicodingevent.data.api.retrofit.ApiConfig
import com.example.dicodingevent.databinding.DetailEventBinding
import com.example.dicodingevent.settings.SettingsPreferences
import com.example.dicodingevent.settings.SettingsViewModel
import com.example.dicodingevent.settings.ViewModelFactory
import com.example.dicodingevent.settings.dataStore
import retrofit2.Call
import retrofit2.Response

class DetailEventActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: DetailEventBinding

    private lateinit var id:String
    private lateinit var linkUrl:String
    private lateinit var htmlDecription:String
    private lateinit var dataResponse: Event

    companion object{
        const val TAG:String = "DetailEventActivity"
        var favorite:Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get data from intent
        id = intent.getStringExtra("id").toString()

        binding = DetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hide actionBar
        val actionBar: ActionBar? = getSupportActionBar()
        actionBar?.hide()

        // settings view model
        val pref = SettingsPreferences.getInstance(application.dataStore)
        val loveModel = ViewModelProvider(this, ViewModelFactory(pref)).get(SettingsViewModel::class.java)

//        loveModel.getLoveSettings().observe(this){fav  ->
//            binding.loveButton.setImageResource(if (fav) R.drawable.love else R.drawable.not_love)
//            Log.i("LOVE_FIELD",fav.toString())
//            favorite = fav
//
//        }


        Log.i("id_event", id)

        // instance model
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailEventModel::class.java)

        mainViewModel.detailEvent.observe(this, Observer {

            if (it != null){

                updateUi(it)


                // Check and update favorite status when details are loaded
                loveModel.getLoveSettings().observe(this) { isFavorite ->
                    favorite = isFavorite
                    updateLoveButton()
                }

                // Set the click listener for the love button
                binding.loveButton.setOnClickListener {
                    favorite = !favorite // Toggle favorite
                    loveModel.saveLoveSettings(favorite)
                    updateLoveButton() // Update button appearance
                }

                Log.i(TAG, "Data : ${it}")


            }
        })

        mainViewModel.isLoading.observe(this,Observer{
            showLoading(it)
        })

        // fetch event detail
        mainViewModel.fetchDetailEvent(id)

        binding.linkEvent.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(linkUrl))
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onSupportNavigateUp()
        return true
    }

    private fun updateUi(eventDetails:Event)
    {
        binding.decription.text = HtmlCompat.fromHtml(eventDetails.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        Glide.with(binding.pictureEvent.context).load(eventDetails.imageLogo).into(binding.pictureEvent)
        binding.judulEvent.text = eventDetails.name
        binding.colaborator.text = eventDetails.ownerName
        binding.dateStart.text = eventDetails.beginTime
        binding.dateEnd.text = eventDetails.endTime
        binding.kuota.text = (eventDetails.quota - eventDetails.registrants).toString()
        binding.placement.text = eventDetails.cityName
    }

    private fun updateLoveButton()
    {
        binding.loveButton.setImageResource(if (favorite) R.drawable.love else R.drawable.not_love)
    }

    private fun showLoading(isLoading:Boolean){
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}