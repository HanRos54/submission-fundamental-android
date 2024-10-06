package com.example.dicodingevent.ui.finished

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dicodingevent.R
import com.example.dicodingevent.adapter.EventAdapter
import com.example.dicodingevent.data.api.response.EventResponse
import com.example.dicodingevent.data.api.retrofit.ApiConfig
import com.example.dicodingevent.databinding.FragmentFinishedBinding
import retrofit2.Call
import retrofit2.Response

class FinishedFragment : Fragment() {
   private lateinit var finishedFragmentBinding:FragmentFinishedBinding
   private lateinit var eventAdapter:EventAdapter

   companion object{
       const val TAG = "FinishedFragment"
   }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        finishedFragmentBinding = FragmentFinishedBinding.inflate(inflater, container, false)

        //initialize recycleview
        val layoutManager = LinearLayoutManager(context)
        finishedFragmentBinding.finishedRecycle.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        finishedFragmentBinding.finishedRecycle.addItemDecoration(itemDecoration)

        // Instance viewModel
        val finishedModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FinishedViewModel::class.java)

        // ambil live data event
        finishedModel.event.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                finishedFragmentBinding.progressBar.visibility = View.GONE
                eventAdapter = EventAdapter(events)
                finishedFragmentBinding.finishedRecycle.adapter = eventAdapter
                Log.i(TAG, "Data: $events")
            }
        }

        // ambil live data loading
        finishedModel.loading.observe(this, Observer {
            finishedFragmentBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        // ambil live data fetch retrofit
        finishedModel.fetchEvents()

        return finishedFragmentBinding.root

    }
}