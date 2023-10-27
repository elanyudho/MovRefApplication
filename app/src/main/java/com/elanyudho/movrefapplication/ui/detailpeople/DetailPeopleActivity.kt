package com.elanyudho.movrefapplication.ui.detailpeople

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
import com.elanyudho.core.domain.model.DetailPeople
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityDetailPeopleBinding
import com.elanyudho.movrefapplication.ui.detailmovie.DetailMovieActivity
import com.elanyudho.movrefapplication.ui.detailpeople.adapter.KnownForAdapter
import com.elanyudho.movrefapplication.ui.detailpeople.adapter.PhotosAdapter
import com.elanyudho.core.extension.glide
import com.elanyudho.core.extension.gone
import com.elanyudho.core.extension.invisible
import com.elanyudho.core.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailPeopleActivity : BaseActivityBinding<ActivityDetailPeopleBinding>(),
    Observer<DetailPeopleViewModel.DetailUiState> {

    @Inject
    lateinit var mViewModel: DetailPeopleViewModel

    private val knownForAdapter: KnownForAdapter by lazy { KnownForAdapter() }

    private val photosAdapter: PhotosAdapter by lazy { PhotosAdapter() }

    private var peopleId: String = ""

    override val bindingInflater: (LayoutInflater) -> ActivityDetailPeopleBinding
        get() = { ActivityDetailPeopleBinding.inflate(layoutInflater) }

    override fun setupView() {
        getDataIntent()
        initViewModel()
        setHeader()
        setAction()
    }

    override fun onChanged(state: DetailPeopleViewModel.DetailUiState?) {
        when(state) {
            is DetailPeopleViewModel.DetailUiState.DetailPeopleDataLoaded -> {
                setViewDetail(state.detail)
            }
            is DetailPeopleViewModel.DetailUiState.CreditsPeopleDataLoaded -> {
                if (state.credits.isEmpty()) {
                    showEmptyCreditPeopleView()
                } else {
                    knownForAdapter.submitList(state.credits)
                }
            }
            is DetailPeopleViewModel.DetailUiState.ImagePeopleLoaded -> {
                if (state.listMovie.isEmpty()) {
                    showEmptyImagePeopleView()
                } else {
                    photosAdapter.submitList(state.listMovie)
                }
            }
            is DetailPeopleViewModel.DetailUiState.Loading -> {

            }
            is DetailPeopleViewModel.DetailUiState.FailedLoadData -> {
                binding.errorViewDetail.view.visible()
                binding.scrollView.gone()

                Toast.makeText(this, state.failure.throwable.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun getDataIntent() {
        peopleId = intent.getStringExtra(EXTRA_PEOPLE_ID) ?: ""
    }

    private fun setHeader() {
        binding.headerDetailPeople.btnBack.setOnClickListener { onBackPressed() }
        binding.headerDetailPeople.tvTitleHeader.text = getString(R.string.detail_people)
    }

    private fun initViewModel() {
        mViewModel.uiState.observe(this@DetailPeopleActivity, this@DetailPeopleActivity)
        with(mViewModel) {
            getDetailPeople(peopleId)
            getCreditsPeople(peopleId)
            getImagePeople(peopleId)
        }
    }

    private fun setAction() {
        setKnownForAction()
        setPhotoAction()
    }

    private fun setKnownForAction() {

        with(binding.rvKnownFor) {
            adapter = knownForAdapter
            setHasFixedSize(true)
        }

        knownForAdapter.setOnClickData {
            startActivity(Intent(this, DetailMovieActivity::class.java).putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it.movieId.toString()))
            hideKeyboard(this)
        }
    }

    private fun setPhotoAction() {
        with(binding.rvPhotos) {
            adapter = photosAdapter
            setHasFixedSize(true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewDetail(detail: DetailPeople) {
        with(binding) {
            if (detail.peopleBirthday.isNullOrEmpty()) {
                tvBirthday.invisible()
                materialTextView11.visible()
            } else {
                tvBirthday.visible()
                tvBirthday.text = detail.peopleBirthday
                materialTextView11.gone()
            }
            if (detail.peopleBiography.isNullOrEmpty()) {
                tvOverview.invisible()
                materialTextView12.visible()
            } else {
                tvOverview.visible()
                tvOverview.text = detail.peopleBiography
                materialTextView12.gone()
            }
            tvOverview.text = detail.peopleBiography
            tvPeopleName.text = detail.peopleName
            ivImagePeople.glide(this@DetailPeopleActivity, detail.peopleImage?: "")
        }
    }

    private fun showEmptyCreditPeopleView() {
        binding.materialTextView13.visible()
        binding.rvKnownFor.gone()
    }

    private fun showEmptyImagePeopleView() {
        binding.materialTextView9.visible()
        binding.rvPhotos.invisible()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val EXTRA_PEOPLE_ID = "id people"
    }

}