package com.starwars.starwarstournament.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.starwars.starwarstournament.di.DaggerStarWarsComponent
import com.starwars.starwarstournament.di.StarWarsComponent
import com.starwars.starwarstournament.di.ViewModelFactory
import com.starwars.starwarstournament.helper.GlobalState
import com.starwars.starwarstournament.helper.State
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var starWarsComponent: StarWarsComponent? = null

    abstract fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    abstract fun afterViewCreated()
    abstract fun getGlobalStateData(): LiveData<GlobalState>
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Fragment", "Oncreate called")
        super.onCreate(savedInstanceState)
        starWarsComponent?.inject(this) ?: initInjector()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment", "Oncreateview called")
        return doViewBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterViewCreated()
        getGlobalStateData().observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> dismissProgress()
                State.ERROR -> handleError(it.message ?: "Something Went Wrong")
                State.LOADING -> showProgress()
            }
        }
    }

    abstract fun dismissProgress()

    abstract fun showProgress()

    private fun handleError(message: String) {
        dismissProgress()
        showDialog(message)
    }

    private fun showDialog(message: String) {

    }

    private fun initInjector() {
        starWarsComponent = DaggerStarWarsComponent.builder().activityContext(requireContext())
            .build()
        starWarsComponent?.inject(this)
    }
}