package com.elanyudho.movrefapplication.ui.genre.listmoviebygenre

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityMovieGenreBinding
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.ui.detailmovie.DetailMovieActivity
import com.elanyudho.movrefapplication.ui.genre.listmoviebygenre.adapter.MovieGenreAdapter
import com.elanyudho.movrefapplication.utils.extensions.gone
import com.elanyudho.movrefapplication.utils.extensions.visible
import com.elanyudho.movrefapplication.utils.pagination.RecyclerviewPaginator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieGenreActivity : BaseActivityBinding<ActivityMovieGenreBinding>(), Observer<MovieGenreViewModel.MovieGenreUiState> {

    @Inject
    lateinit var mViewModel: MovieGenreViewModel

    private val movieGenreAdapter: MovieGenreAdapter by lazy { MovieGenreAdapter() }

    private var paginator: RecyclerviewPaginator? = null

    override val bindingInflater: (LayoutInflater) -> ActivityMovieGenreBinding
        get() = { ActivityMovieGenreBinding.inflate(layoutInflater) }

    override fun setupView() {
        val genre = intent.getParcelableExtra<Genre>(EXTRA_TYPE)

        setHeader(genre?.genre.toString())
        mViewModel.uiState.observe(this, this)
        mViewModel.getMovieGenre(1, genre?.id.toString())

        setMovieAction()
        setMoviePagination(genre?.id.toString())
    }

    override fun onChanged(state: MovieGenreViewModel.MovieGenreUiState?) {
        when(state) {
            is MovieGenreViewModel.MovieGenreUiState.MovieDataLoaded-> {
                stopLoading()
                movieGenreAdapter.appendList(state.movieList)
            }
            is MovieGenreViewModel.MovieGenreUiState.InitialLoading -> {
                startInitialLoading()
            }
            is MovieGenreViewModel.MovieGenreUiState.PagingLoading -> {
                startPagingLoading()
            }
            is MovieGenreViewModel.MovieGenreUiState.FailedLoadData -> {
                stopLoading()
                Toast.makeText(this, state.failure.code, Toast.LENGTH_SHORT)
                    .show()
            }
        }
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

    private fun setMoviePagination(genreId: String) {
        paginator = RecyclerviewPaginator(binding.rvMovie.layoutManager as StaggeredGridLayoutManager)
        paginator?.setOnLoadMoreListener { page ->
            mViewModel.getMovieGenre(page, genreId)
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

    private fun setHeader(text: String) {
        binding.headerMovieGenre.btnBack.setOnClickListener { onBackPressed() }
        binding.headerMovieGenre.tvTitleHeader.text = text
    }

    companion object {
        const val EXTRA_TYPE = "movie_genre"

    }

}