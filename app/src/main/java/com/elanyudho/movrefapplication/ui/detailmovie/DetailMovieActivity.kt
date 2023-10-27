package com.elanyudho.movrefapplication.ui.detailmovie

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.core.domain.model.DetailMovie
import com.elanyudho.core.extension.glide
import com.elanyudho.core.extension.gone
import com.elanyudho.core.extension.roundOffDecimal
import com.elanyudho.core.extension.visible
import com.elanyudho.movrefapplication.databinding.ActivityDetailMovieBinding
import com.elanyudho.movrefapplication.ui.detailmovie.adapter.CastAdapter
import com.elanyudho.movrefapplication.ui.detailmovie.adapter.RecommendationAdapter
import com.elanyudho.movrefapplication.ui.detailmovie.adapter.ReviewMovieAdapter
import com.elanyudho.movrefapplication.ui.detailpeople.DetailPeopleActivity
import com.elanyudho.movrefapplication.utils.pagination.RecyclerviewPaginator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailMovieActivity : BaseActivityBinding<ActivityDetailMovieBinding>(),
    Observer<DetailMovieViewModel.DetailUiState>{

    @Inject
    lateinit var mViewModel: DetailMovieViewModel

    private val castAdapter: CastAdapter by lazy { CastAdapter() }

    private val recommendationAdapter: RecommendationAdapter by lazy { RecommendationAdapter() }

    private val reviewMovieAdapter: ReviewMovieAdapter by lazy { ReviewMovieAdapter() }

    private var paginator: RecyclerviewPaginator? = null

    private var movieId: String = ""

    private var isFirstGet = true

    private var videoMovie: String = ""

    override val bindingInflater: (LayoutInflater) -> ActivityDetailMovieBinding
        get() = { ActivityDetailMovieBinding.inflate(layoutInflater) }

    override fun setupView() {
        getDataIntent()
        initViewModel()
        setHeader()
        setAction()
        setVideoMovie()
        setReviewMoviePagination(movieId)
    }

    override fun onChanged(state: DetailMovieViewModel.DetailUiState?) {
        when(state) {
            is DetailMovieViewModel.DetailUiState.DetailMovieDataLoaded -> {
                setViewDetail(state.detail)
            }
            is DetailMovieViewModel.DetailUiState.CreditsMovieDataLoaded -> {
                castAdapter.submitList(state.credits)
            }
            is DetailMovieViewModel.DetailUiState.RecommendationMovieDataLoaded -> {
                if (state.listMovie.isEmpty()) {
                    showEmptyDataRecommendation()
                } else {
                    recommendationAdapter.submitList(state.listMovie)
                }
            }
            is DetailMovieViewModel.DetailUiState.Loading -> {

            }
            is DetailMovieViewModel.DetailUiState.InitialLoading -> {
                startInitialLoading()
            }
            is DetailMovieViewModel.DetailUiState.PagingLoading -> {
                startPagingLoading()
            }
            is DetailMovieViewModel.DetailUiState.ReviewMovieDataLoaded -> {
                stopLoading()
                if (state.reviews.isEmpty() && isFirstGet) {
                    showEmptyDataReview()
                } else {
                    reviewMovieAdapter.appendList(state.reviews)
                }
            }
            is DetailMovieViewModel.DetailUiState.VideoMovieDataLoaded -> {
                if (state.videos.isEmpty()) {
                    showEmptyDataVideo()
                } else {
                    videoMovie = state.videos[0].url
                }
            }
            is DetailMovieViewModel.DetailUiState.FailedLoadData -> {
                binding.errorViewDetail.view.visible()
                binding.scrollView.gone()

                Toast.makeText(this, state.failure.throwable.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun getDataIntent() {
        movieId = intent.getStringExtra(EXTRA_MOVIE_ID) ?: ""
    }

    private fun initViewModel() {
        mViewModel.uiState.observe(this@DetailMovieActivity, this)
        with(mViewModel) {
            getDetailMovie(movieId)
            getCreditsMovie(movieId)
            getRecommendationMovie(movieId, "1")
            getReviewMovie(movieId, 1L)
            getVideoMovie(movieId)
        }
    }

    private fun setHeader() {
        binding.headerDetailMovie.btnBack.setOnClickListener { onBackPressed() }
        binding.headerDetailMovie.tvTitleHeader.text = getString(com.elanyudho.movrefapplication.R.string.detail_movie)
    }

    private fun setAction() {
        setMovieCastAction()
        setRecommendationAction()
        setReviewMovieAction()
    }

    private fun setMovieCastAction() {

        with(binding.rvCast) {
            adapter = castAdapter
            setHasFixedSize(true)
        }

        castAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailPeopleActivity::class.java
                ).putExtra(DetailPeopleActivity.EXTRA_PEOPLE_ID, it.peopleId.toString())
            )
        }
    }

    private fun setRecommendationAction() {

        with(binding.rvRecommendation) {
            adapter = recommendationAdapter
            setHasFixedSize(true)
        }

        recommendationAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).putExtra(EXTRA_MOVIE_ID, it.movieId.toString())
            )

        }
    }

    private fun setReviewMovieAction() {
        with(binding.rvReview) {
            adapter = reviewMovieAdapter
            setHasFixedSize(true)
        }
    }

    private fun setReviewMoviePagination(id: String) {
        paginator = RecyclerviewPaginator(binding.rvReview.layoutManager as LinearLayoutManager)
        paginator?.setOnLoadMoreListener { page ->
            isFirstGet = false
            mViewModel.getReviewMovie(id, page)
        }
        paginator?.let { binding.rvReview.addOnScrollListener(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewDetail(detail: DetailMovie) {
        with(binding) {
            tvGenreMovie.text = detail.movieGenre[0].name
            tvDurationMovie.text = detail.movieDuration.toString() + "m"
            tvRateMovie.text = detail.movieRate?.roundOffDecimal().toString() + "/10"
            tvReleaseDate.text = detail.movieDate
            tvOverview.text = detail.movieOverview
            tvTitleMovie.text = detail.movieName
            ivPosterMovie.glide(this@DetailMovieActivity, detail.moviePoster?: "")
        }
    }

    private fun setVideoMovie() {
        lifecycle.addObserver(binding.vtMovie)
        binding.vtMovie.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId = videoMovie, 0f)
            }
        })
    }

    private fun startInitialLoading() {
        binding.progressBarMovie.visible()
    }

    private fun stopLoading() {
        binding.progressBarMovie.gone()
    }

    private fun startPagingLoading() {
        binding.progressBarMovie.visible()
    }

    private fun showEmptyDataRecommendation() {
        binding.materialTextView7.visible()
        binding.rvRecommendation.gone()
    }

    private fun showEmptyDataVideo() {
        binding.vtMovie.gone()
        binding.materialTextView13.visible()
    }

    private fun showEmptyDataReview() {
        binding.rvReview.gone()
        binding.materialTextView11.visible()
    }


    companion object {
        const val EXTRA_MOVIE_ID = "id movie"
    }

}