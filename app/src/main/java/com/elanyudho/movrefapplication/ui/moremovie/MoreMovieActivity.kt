package com.elanyudho.movrefapplication.ui.moremovie

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityMoreMovieBinding
import com.elanyudho.movrefapplication.ui.detailmovie.DetailMovieActivity
import com.elanyudho.movrefapplication.ui.moremovie.adapter.MoreMovieAdapter
import com.elanyudho.core.extension.gone
import com.elanyudho.core.extension.visible
import com.elanyudho.movrefapplication.utils.pagination.RecyclerviewPaginator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoreMovieActivity : BaseActivityBinding<ActivityMoreMovieBinding>(),
    Observer<MoreMovieViewModel.MoreUiState> {

    @Inject
    lateinit var mViewModel: MoreMovieViewModel

    private val moreMovieAdapter: MoreMovieAdapter by lazy { MoreMovieAdapter() }

    private var paginator: RecyclerviewPaginator? = null

    private var isFirstGet = true

    private var type = -1

    override val bindingInflater: (LayoutInflater) -> ActivityMoreMovieBinding
        get() = { ActivityMoreMovieBinding.inflate(layoutInflater) }

    override fun setupView() {
        getDataIntent()
        initViewModel()
        setAction()
        setMoviePagination(type)

    }

    override fun onChanged(state: MoreMovieViewModel.MoreUiState?) {
        when(state) {
            is MoreMovieViewModel.MoreUiState.MovieDataLoaded -> {
                if (state.movieList.isEmpty() && isFirstGet) {
                    showEmptyMovie()
                } else {
                    stopLoading()
                    moreMovieAdapter.appendList(state.movieList)
                }
            }
            is MoreMovieViewModel.MoreUiState.InitialLoading -> {
                startInitialLoading()
            }
            is MoreMovieViewModel.MoreUiState.PagingLoading -> {
                startPagingLoading()
            }
            is MoreMovieViewModel.MoreUiState.FailedLoadData -> {
                stopLoading()
                binding.errorViewDetail.view.visible()
                binding.rvMovie.gone()
                Toast.makeText(this, state.failure.throwable.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}

        }
    }

    private fun getDataIntent() {
        type = intent.getIntExtra(EXTRA_TYPE, -1)

    }
    private fun initViewModel() {
        mViewModel.uiState.observe(this, this)

        when(type) {
            POPULAR -> {
                setHeader(getString(R.string.popular))
                mViewModel.getPopularMovie(1)
            }
            TOPRATED -> {
                setHeader(getString(R.string.top_rated))
                mViewModel.getTopRatedMovie(1)
            }
            UPCOMING -> {
                setHeader(getString(R.string.upcoming))
                mViewModel.getUpcomingMovie(1)
            }
            TRENDING -> {
                setHeader(getString(R.string.trending))
                mViewModel.getTrendingMovie(1)
            }
        }
    }

    private fun setAction() {
        setMovieAction()
    }

    private fun setHeader(title: String) {
        binding.headerMoreMovie.btnBack.setOnClickListener { onBackPressed() }
        binding.headerMoreMovie.tvTitleHeader.text = title
    }

    private fun setMovieAction() {

        with(binding.rvMovie) {
            adapter = moreMovieAdapter
            setHasFixedSize(true)
        }

        moreMovieAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it.movieId.toString())
            )
        }
    }

    private fun setMoviePagination(type: Int) {
        paginator = RecyclerviewPaginator(binding.rvMovie.layoutManager as StaggeredGridLayoutManager)
        paginator?.setOnLoadMoreListener { page ->
            isFirstGet = false
            when(type) {
                POPULAR -> {
                    mViewModel.getPopularMovie(page)
                }
                TOPRATED -> {
                    mViewModel.getTopRatedMovie(page)
                }
                UPCOMING -> {
                    mViewModel.getUpcomingMovie(page)
                }
                TRENDING -> {
                    mViewModel.getTrendingMovie(page)
                }
            }
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

    private fun showEmptyMovie() {
        binding.progressBarMovie.gone()
        binding.rvMovie.gone()
    }

    companion object {
        const val EXTRA_TYPE = "movie_type"
        const val POPULAR = 0
        const val TOPRATED = 1
        const val UPCOMING = 2
        const val TRENDING = 3
    }

}