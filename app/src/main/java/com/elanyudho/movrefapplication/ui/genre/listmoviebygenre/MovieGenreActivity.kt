package com.elanyudho.movrefapplication.ui.genre.listmoviebygenre

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.core.domain.model.Genre
import com.elanyudho.movrefapplication.databinding.ActivityMovieGenreBinding
import com.elanyudho.movrefapplication.ui.detailmovie.DetailMovieActivity
import com.elanyudho.movrefapplication.ui.genre.listmoviebygenre.adapter.MovieGenreAdapter
import com.elanyudho.core.extension.gone
import com.elanyudho.core.extension.visible
import com.elanyudho.movrefapplication.utils.pagination.RecyclerviewPaginator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieGenreActivity : BaseActivityBinding<ActivityMovieGenreBinding>(), Observer<MovieGenreViewModel.MovieGenreUiState> {

    @Inject
    lateinit var mViewModel: MovieGenreViewModel

    private val movieGenreAdapter: MovieGenreAdapter by lazy { MovieGenreAdapter() }

    private var paginator: RecyclerviewPaginator? = null

    private lateinit var genre: Genre

    private var isFirstGet = true

    override val bindingInflater: (LayoutInflater) -> ActivityMovieGenreBinding
        get() = { ActivityMovieGenreBinding.inflate(layoutInflater) }

    override fun setupView() {
        getDataIntent()
        initViewModel()
        setHeader()
        setAction()
        setMoviePagination()
    }

    override fun onChanged(state: MovieGenreViewModel.MovieGenreUiState?) {
        when(state) {
            is MovieGenreViewModel.MovieGenreUiState.MovieDataLoaded-> {
                stopLoading()
                if (state.movieList.isEmpty() && isFirstGet) {
                    showEmptyDataRecommendation()
                } else {
                    movieGenreAdapter.appendList(state.movieList)
                }
            }
            is MovieGenreViewModel.MovieGenreUiState.InitialLoading -> {
                startInitialLoading()
            }
            is MovieGenreViewModel.MovieGenreUiState.PagingLoading -> {
                startPagingLoading()
            }
            is MovieGenreViewModel.MovieGenreUiState.FailedLoadData -> {
                stopLoading()
                binding.errorViewDetail.view.visible()
                binding.rvMovie.gone()
                Toast.makeText(this, state.failure.throwable.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun getDataIntent() {
        genre = intent.getParcelableExtra<Genre>(EXTRA_TYPE) as Genre
    }

    private fun initViewModel() {
        mViewModel.uiState.observe(this, this)
        mViewModel.getMovieGenre(1, genre.id.toString())
    }

    private fun setAction() {
        setMovieAction()
    }
    private fun setMovieAction() {

        with(binding.rvMovie) {
            adapter = movieGenreAdapter
            setHasFixedSize(true)
        }

        movieGenreAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it.movieId.toString())
            )
        }
    }

    private fun setMoviePagination() {
        paginator = RecyclerviewPaginator(binding.rvMovie.layoutManager as StaggeredGridLayoutManager)
        paginator?.setOnLoadMoreListener { page ->
            isFirstGet = false
            mViewModel.getMovieGenre(page, genre.id.toString())
        }
        paginator?.let { binding.rvMovie.addOnScrollListener(it) }
    }

    private fun startInitialLoading() {
        binding.progressBarMovie.visible()
        binding.rvMovie.gone()
    }

    private fun stopLoading() {
        binding.progressBarMovie.gone()
        binding.rvMovie.visible()
    }

    private fun startPagingLoading() {
        binding.progressBarMovie.visible()
        binding.rvMovie.visible()
    }

    private fun showEmptyDataRecommendation() {
        binding.rvMovie.gone()
    }

    private fun setHeader() {
        binding.headerMovieGenre.btnBack.setOnClickListener { onBackPressed() }
        binding.headerMovieGenre.tvTitleHeader.text = genre.genre
    }

    companion object {
        const val EXTRA_TYPE = "movie_genre"
    }

}