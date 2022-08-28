package com.elanyudho.movrefapplication.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityMainBinding
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.ui.detailmovie.DetailMovieActivity
import com.elanyudho.movrefapplication.ui.detailpeople.DetailPeopleActivity
import com.elanyudho.movrefapplication.ui.genre.listgenre.ListGenreActivity
import com.elanyudho.movrefapplication.ui.genre.listmoviebygenre.MovieGenreActivity
import com.elanyudho.movrefapplication.ui.main.adapter.MainMovieAdapter
import com.elanyudho.movrefapplication.ui.main.adapter.MainPeopleAdapter
import com.elanyudho.movrefapplication.ui.main.adapter.SuggestionAdapter
import com.elanyudho.movrefapplication.ui.moremovie.MoreMovieActivity
import com.elanyudho.movrefapplication.ui.morepeople.MorePeopleActivity
import com.elanyudho.movrefapplication.utils.extensions.gone
import com.elanyudho.movrefapplication.utils.extensions.visible
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>(),
    Observer<MainViewModel.MainUiState> {

    @Inject
    lateinit var mViewModel: MainViewModel

    private val movieAdapter: MainMovieAdapter by lazy { MainMovieAdapter() }
    private val peopleAdapter: MainPeopleAdapter by lazy { MainPeopleAdapter() }
    private val suggestionAdapter: SuggestionAdapter by lazy { SuggestionAdapter() }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = { ActivityMainBinding.inflate(layoutInflater) }

    override fun setupView() {
        //init call
        with(mViewModel) {
            uiState.observe(this@MainActivity, this@MainActivity)
            getGenre()
            getTrendingMovie("1")
            getPopularPeople("1")
            getPopularMovie("1")
        }

        setChipAction()
        setMovieAction()
        setSearchMovieAction()
        setPeopleAction()
        setSeeAllAction(MoreMovieActivity.POPULAR)

    }

    override fun onChanged(state: MainViewModel.MainUiState?) {
        when (state) {
            is MainViewModel.MainUiState.MovieDataLoaded -> {
                movieAdapter.submitList(state.movieList)
            }
            is MainViewModel.MainUiState.TrendingMovieDataLoaded -> {
                val movieBanner = ArrayList<SlideModel>()
                for (i in 0..4) {
                    movieBanner.add(
                        SlideModel(
                            state.movieList[i].movieBackDrop,
                            state.movieList[i].movieName
                        )
                    )
                }
                setBannerAction(movieBanner)

            }
            is MainViewModel.MainUiState.SearchMovieLoaded -> {
                suggestionAdapter.submitList(state.movieList)
                if (state.movieList.isEmpty()) {
                    binding.rvSuggestion.gone()
                } else {
                    binding.rvSuggestion.visible()
                }
            }
            is MainViewModel.MainUiState.GenreDataLoaded -> {
                if (state.genreList.size > 9) {
                    for (i in 1..9) {
                        addChipGenre(state.genreList[i])
                    }
                } else {
                    for (i in state.genreList) {
                        addChipGenre(i)
                    }
                }
            }
            is MainViewModel.MainUiState.PopularPeopleDataLoaded -> {
                peopleAdapter.submitList(state.peopleList)
            }
            is MainViewModel.MainUiState.Loading -> {

            }
            is MainViewModel.MainUiState.FailedLoadData -> {
                Toast.makeText(this, state.failure.code, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setMovieAction() {

        with(binding.rvFindMovie) {
            adapter = movieAdapter
            setHasFixedSize(true)
        }

        movieAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it.movieId.toString())
            )

            hideKeyboard(this)
        }
    }

    private fun setPeopleAction() {

        with(binding.rvPopularPeople) {
            adapter = peopleAdapter
            setHasFixedSize(true)
        }

        peopleAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailPeopleActivity::class.java
                ).putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, it.peopleId.toString())
            )

            hideKeyboard(this)
        }
    }

    private fun setSearchMovieAction() {

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
                    if (newText?.length == 0) {
                        mViewModel.getSearchMovie("Cpgncpgncpgn", "1")
                    } else {
                        mViewModel.getSearchMovie(newText.toString(), "1")
                    }
                    return true
                }

            })

        }

        suggestionAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it.movieId.toString())
            )

            hideKeyboard(this)
        }
    }

    private fun setChipAction() {
        with(binding) {

            chipGroup.setOnCheckedChangeListener { group, checkedId ->

                for (i in 0 until group.childCount) {
                    val chip = group.getChildAt(i)
                    chip.isClickable = chip.id != group.checkedChipId
                }

                when (checkedId) {
                    R.id.chip_popular -> {
                        mViewModel.getPopularMovie("1")
                        setSeeAllAction(MoreMovieActivity.POPULAR)
                    }
                    R.id.chip_top_rated -> {
                        mViewModel.getTopRatedMovie("1")
                        setSeeAllAction(MoreMovieActivity.TOPRATED)
                    }
                    R.id.chip_upcoming -> {
                        mViewModel.getUpcomingMovie("1")
                        setSeeAllAction(MoreMovieActivity.UPCOMING)
                    }
                }

            }
        }

    }

    private fun setBannerAction(list: List<SlideModel>) {
        with(binding) {
            ivBannerMovie.setImageList(list, ScaleTypes.CENTER_CROP)
        }
    }

    private fun setSeeAllAction(type: Int) {
        binding.tvSeeAll.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MoreMovieActivity::class.java
                ).putExtra(MoreMovieActivity.EXTRA_TYPE, type)
            )

            hideKeyboard(this)
        }

        binding.tvSeeAllTrending.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    MoreMovieActivity::class.java
                ).putExtra(MoreMovieActivity.EXTRA_TYPE, MoreMovieActivity.TRENDING)
            )

            hideKeyboard(this)
        }

        binding.tvSeeAllPeople.setOnClickListener {
            startActivity(Intent(this, MorePeopleActivity::class.java))

            hideKeyboard(this)
        }

        binding.tvSeeAllGenre.setOnClickListener {
            startActivity(Intent(this, ListGenreActivity::class.java))

            hideKeyboard(this)
        }
    }

    private fun addChipGenre(genre: Genre) {
        val chip = Chip(this)
        val drawable = ChipDrawable.createFromAttributes(this, null, 0, com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice)
        chip.setChipDrawable(drawable)
        chip.text = genre.genre
        chip.setTextColor(getColorStateList(R.color.chip_text))
        chip.chipBackgroundColor = getColorStateList(R.color.chip_background)
        chip.setOnClickListener {
            val intent = Intent(this, MovieGenreActivity::class.java)
            intent.putExtra(MovieGenreActivity.EXTRA_TYPE, genre)
            startActivity(intent)
        }
        binding.chipGroupGenre.addView(chip)
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

}