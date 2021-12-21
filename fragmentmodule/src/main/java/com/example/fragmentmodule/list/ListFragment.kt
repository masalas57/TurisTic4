package com.example.fragmentmodule.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragmentmodule.databinding.FragmentListBinding
import com.example.fragmentmodule.model.Cartagena
import com.google.gson.Gson
import com.example.fragmentmodule.model.CartagenaItem

class   ListFragment : Fragment() {

    private lateinit var listBinding: FragmentListBinding
    private lateinit var listViewModel: ListViewModel
    private lateinit var cartagenaAdapter: CartagenaAdapter
    private var listCartagena: ArrayList<CartagenaItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        listBinding = FragmentListBinding.inflate(inflater, container, false)
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]

        return listBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel.getCartagenaFromServer()

        listViewModel.onCartagenaLoaded.observe(viewLifecycleOwner, { result ->
            onCartagenaLoadedSubscribe(result)
        })
        /*listCartagena = loadMockCartagenaFromJson()*/
        cartagenaAdapter = CartagenaAdapter(listCartagena, onItemClicked = { onCartagenaClicked(it) } )

        listBinding.cartagenaRecyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = cartagenaAdapter
            setHasFixedSize(false)
        }
    }

    private fun onCartagenaLoadedSubscribe(result: ArrayList<CartagenaItem>?) {
        result?.let { listCartagena ->
            cartagenaAdapter.appendItems(listCartagena)
        }
    }
    private fun onCartagenaClicked(cartagena: CartagenaItem) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(poicartagena = cartagena))
    }

    private fun loadMockCartagenaFromJson(): ArrayList<CartagenaItem> {
        var cartagenaString: String = context?.assets?.open("poicartagena.json")?.bufferedReader().use { it!!.readText() } //TODO reparar
        val gson = Gson()
        val cartagenaList = gson.fromJson(cartagenaString, Cartagena::class.java)
        return cartagenaList
    }
}