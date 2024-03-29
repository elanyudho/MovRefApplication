package com.elanyudho.movrefapplication.ui.morepeople

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.core.extension.gone
import com.elanyudho.core.extension.visible
import com.elanyudho.movrefapplication.databinding.ActivityMorePeopleBinding
import com.elanyudho.movrefapplication.ui.detailpeople.DetailPeopleActivity
import com.elanyudho.movrefapplication.ui.morepeople.adapter.PopularPeopleAdapter
import com.elanyudho.movrefapplication.ui.morepeople.adapter.SuggestionAdapter
import com.elanyudho.movrefapplication.utils.pagination.RecyclerviewPaginator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MorePeopleActivity : BaseActivityBinding<ActivityMorePeopleBinding>(),
    Observer<MorePeopleViewModel.MoreUiState> {

    @Inject
    lateinit var mViewModel: MorePeopleViewModel

    private val popularPeopleAdapter: PopularPeopleAdapter by lazy { PopularPeopleAdapter() }
    private val suggestionAdapter: SuggestionAdapter by lazy { SuggestionAdapter() }

    private var paginator: RecyclerviewPaginator? = null

    private var isFirstGet = true

    override val bindingInflater: (LayoutInflater) -> ActivityMorePeopleBinding
        get() = { ActivityMorePeopleBinding.inflate(layoutInflater) }

    override fun setupView() {
        initViewModel()
        setAction()
        setPopularPeoplePagination()
    }

    override fun onChanged(state: MorePeopleViewModel.MoreUiState?) {
        when(state) {
            is MorePeopleViewModel.MoreUiState.PeopleDataLoaded -> {
                if (state.peopleList.isEmpty() && isFirstGet) {
                    showEmptyPeople()
                } else {
                    stopLoading()
                    popularPeopleAdapter.appendList(state.peopleList)
                }
            }
            is MorePeopleViewModel.MoreUiState.SearchPeopleDataLoaded -> {
                if (state.peopleList.isEmpty()) {
                    binding.rvSuggestion.gone()
                } else {
                    binding.rvSuggestion.visible()
                    suggestionAdapter.submitList(state.peopleList)
                }
            }
            is MorePeopleViewModel.MoreUiState.Loading -> {

            }
            is MorePeopleViewModel.MoreUiState.InitialLoading -> {
                startInitialLoading()
            }
            is MorePeopleViewModel.MoreUiState.PagingLoading -> {
                startPagingLoading()
            }
            is MorePeopleViewModel.MoreUiState.FailedLoadData -> {
                stopLoading()
                binding.errorViewDetail.view.visible()
                binding.rvPopularPeople.gone()
                Toast.makeText(this, state.failure.throwable.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}

        }
    }

    override fun onResume() {
        super.onResume()
        clearFocusSearchBar()
    }

    private fun initViewModel() {
        mViewModel.uiState.observe(this, this)
        mViewModel.getPopularPeople(1)
    }

    private fun setAction() {
        setBackAction()
        setPopularPeopleAction()
        setSearchPeopleAction()
    }

    private fun setPopularPeopleAction() {
        with(binding.rvPopularPeople) {
            adapter = popularPeopleAdapter
            setHasFixedSize(true)
        }

        popularPeopleAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailPeopleActivity::class.java
                ).putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, it.peopleId.toString())
            )

            hideKeyboard(this)
        }
    }

    private fun setSearchPeopleAction() {

        with(binding.rvSuggestion) {
            adapter = suggestionAdapter
            setHasFixedSize(true)
        }

        with(binding) {
            searchBar.setOnQueryChangeListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true || newText.toString() == "") {
                        mViewModel.getSearchPeople(newText.toString(), "1")
                    } else {
                        binding.rvSuggestion.gone()
                        suggestionAdapter.clear()
                    }
                    return true
                }

            })

        }

        suggestionAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailPeopleActivity::class.java
                ).putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, it.peopleId.toString())
            )

            hideKeyboard(this)
        }
    }

    private fun setPopularPeoplePagination() {
        paginator =
            RecyclerviewPaginator(binding.rvPopularPeople.layoutManager as LinearLayoutManager)
        paginator?.setOnLoadMoreListener { page ->
            isFirstGet = false
            mViewModel.getPopularPeople(page)
        }
        paginator?.let { binding.rvPopularPeople.addOnScrollListener(it) }
    }

    private fun setBackAction() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
            hideKeyboard(this)
        }
    }

    private fun startInitialLoading() {
        binding.progressBarPeople.visible()
        binding.rvPopularPeople.gone()
    }

    private fun stopLoading() {
        binding.progressBarPeople.gone()
        binding.rvPopularPeople.visible()
    }

    private fun startPagingLoading() {
        binding.progressBarPeople.visible()
        binding.rvPopularPeople.visible()
    }

    private fun showEmptyPeople() {
        binding.progressBarPeople.gone()
        binding.rvPopularPeople.gone()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearFocusSearchBar() {
        binding.searchBar.clearFocus()
        binding.rvSuggestion.gone()
        suggestionAdapter.clear()
    }
}