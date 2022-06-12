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
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityDetailPeopleBinding
import com.elanyudho.movrefapplication.domain.model.DetailPeople
import com.elanyudho.movrefapplication.ui.detailmovie.DetailMovieActivity
import com.elanyudho.movrefapplication.ui.detailpeople.adapter.KnownForAdapter
import com.elanyudho.movrefapplication.ui.detailpeople.adapter.PhotosAdapter
import com.elanyudho.movrefapplication.utils.extensions.glide
import com.elanyudho.movrefapplication.utils.extensions.gone
import com.elanyudho.movrefapplication.utils.extensions.invisible
import com.elanyudho.movrefapplication.utils.extensions.visible
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
        peopleId = intent.getStringExtra(EXTRA_PEOPLE_ID) ?: ""

        //init call
        with(mViewModel) {
            uiState.observe(this@DetailPeopleActivity, this@DetailPeopleActivity)
            getDetailPeople(peopleId)
            getCreditsPeople(peopleId)
            getImagePeople(peopleId)
        }

        setHeader()
        setKnownForAction()
        setPhotoAction()
    }

    override fun onChanged(state: DetailPeopleViewModel.DetailUiState?) {
        when(state) {
            is DetailPeopleViewModel.DetailUiState.DetailPeopleDataLoaded -> {
                setViewDetail(state.detail)
            }
            is DetailPeopleViewModel.DetailUiState.CreditsPeopleDataLoaded -> {
                if (state.credits.isEmpty()) {
                    binding.materialTextView13.visible()
                    binding.rvKnownFor.gone()
                } else {
                    knownForAdapter.submitList(state.credits)
                    binding.materialTextView9.gone()
                    binding.rvKnownFor.visible()
                }
            }
            is DetailPeopleViewModel.DetailUiState.ImagePeopleLoaded -> {
                if (state.listMovie.isEmpty()) {
                    binding.materialTextView9.visible()
                    binding.rvPhotos.invisible()
                } else {
                    photosAdapter.submitList(state.listMovie)
                    binding.materialTextView9.gone()
                    binding.rvPhotos.visible()
                }
            }
            is DetailPeopleViewModel.DetailUiState.Loading -> {

            }
            is DetailPeopleViewModel.DetailUiState.FailedLoadData -> {
                Toast.makeText(this, state.failure.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setHeader() {
        binding.headerDetailPeople.btnBack.setOnClickListener { onBackPressed() }
        binding.headerDetailPeople.tvTitleHeader.text = getString(R.string.detail_people)
    }

    private fun setKnownForAction() {

        with(binding.rvKnownFor) {
            adapter = knownForAdapter
            setHasFixedSize(true)
        }

        knownForAdapter.setOnClickData {
            startActivity(
                Intent(
                    this,
                    DetailMovieActivity::class.java
                ).putExtra(DetailMovieActivity.EXTRA_MOVIE_ID, it.movieId.toString())
            )

            hideKeyboard(this)
        }
    }

    private fun setPhotoAction() {

        with(binding.rvPhotos) {
            adapter = photosAdapter
            setHasFixedSize(true)
        }

        photosAdapter.setOnClickData {
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

    companion object {
        const val EXTRA_PEOPLE_ID = "id people"
    }

}