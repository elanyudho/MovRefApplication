package com.elanyudho.movrefapplication.ui.detailmovie

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityDetailMovieBinding
import com.elanyudho.movrefapplication.domain.model.DetailMovie
import com.elanyudho.movrefapplication.ui.detailmovie.adapter.CastAdapter
import com.elanyudho.movrefapplication.ui.detailmovie.adapter.RecommendationAdapter
import com.elanyudho.movrefapplication.ui.detailpeople.DetailPeopleActivity
import com.elanyudho.movrefapplication.utils.extensions.glide
import com.elanyudho.movrefapplication.utils.extensions.gone
import com.elanyudho.movrefapplication.utils.extensions.invisible
import com.elanyudho.movrefapplication.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailMovieActivity : BaseActivityBinding<ActivityDetailMovieBinding>(),
    Observer<DetailMovieViewModel.DetailUiState> {

    @Inject
    lateinit var mViewModel: DetailMovieViewModel

    private val castAdapter: CastAdapter by lazy { CastAdapter() }

    private val recommendationAdapter: RecommendationAdapter by lazy { RecommendationAdapter() }

    private var movieId: String = ""

    override val bindingInflater: (LayoutInflater) -> ActivityDetailMovieBinding
        get() = { ActivityDetailMovieBinding.inflate(layoutInflater) }

    override fun setupView() {

        movieId = intent.getStringExtra(EXTRA_MOVIE_ID) ?: ""

        //init call
        with(mViewModel) {
            uiState.observe(this@DetailMovieActivity, this@DetailMovieActivity)
            getDetailMovie(movieId)
            getCreditsMovie(movieId)
            getRecommendationMovie(movieId, "1")
        }

        setHeader()
        setMovieCastAction()
        setRecommendationAction()
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
                    binding.materialTextView7.visible()
                    binding.rvRecommendation.invisible()
                } else {
                    binding.materialTextView7.gone()
                    binding.rvRecommendation.visible()
                }
                recommendationAdapter.submitList(state.listMovie)
            }
            is DetailMovieViewModel.DetailUiState.Loading -> {

            }
            is DetailMovieViewModel.DetailUiState.FailedLoadData -> {
                Toast.makeText(this, getString(R.string.error_unknown_error), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setHeader() {
        binding.headerDetailMovie.btnBack.setOnClickListener { onBackPressed() }
        binding.headerDetailMovie.tvTitleHeader.text = getString(R.string.detail_movie)
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

    @SuppressLint("SetTextI18n")
    private fun setViewDetail(detail: DetailMovie) {
        with(binding) {
            tvGenreMovie.text = detail.movieGenre[0].name
            tvDurationMovie.text = detail.movieDuration.toString() + "m"
            tvRateMovie.text = detail.movieRate.toString() + "/10"
            tvReleaseDate.text = detail.movieDate
            tvOverview.text = detail.movieOverview
            tvTitleMovie.text = detail.movieName
            ivPosterMovie.glide(this@DetailMovieActivity, detail.moviePoster?: "")
        }
    }

    companion object {
        const val EXTRA_MOVIE_ID = "id movie"
    }

}