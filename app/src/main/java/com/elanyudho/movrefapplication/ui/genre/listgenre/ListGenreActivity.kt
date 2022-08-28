package com.elanyudho.movrefapplication.ui.genre.listgenre

import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import com.elanyudho.core.abstraction.BaseActivityBinding
import com.elanyudho.movrefapplication.R
import com.elanyudho.movrefapplication.databinding.ActivityListGenreBinding
import com.elanyudho.movrefapplication.domain.model.Genre
import com.elanyudho.movrefapplication.ui.genre.listmoviebygenre.MovieGenreActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListGenreActivity : BaseActivityBinding<ActivityListGenreBinding>(),
    Observer<ListGenreViewModel.ListGenreUiState> {

    @Inject
    lateinit var mViewModel: ListGenreViewModel

    override val bindingInflater: (LayoutInflater) -> ActivityListGenreBinding
        get() = { ActivityListGenreBinding.inflate(layoutInflater) }

    override fun setupView() {
        mViewModel.uiState.observe(this@ListGenreActivity, this@ListGenreActivity)
        mViewModel.getGenre()

        setHeader()
    }

    override fun onChanged(state: ListGenreViewModel.ListGenreUiState?) {
        when (state) {
            is ListGenreViewModel.ListGenreUiState.GenreDataLoaded -> {
                for (i in state.genreList) {
                    addChipGenre(i)
                }
            }
            is ListGenreViewModel.ListGenreUiState.Loading -> {

            }
            is ListGenreViewModel.ListGenreUiState.FailedLoadData -> {
                Toast.makeText(this, state.failure.code, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun addChipGenre(genre: Genre) {
        val chip = Chip(this)
        val drawable = ChipDrawable.createFromAttributes(
            this,
            null,
            0,
            com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice
        )
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

    private fun setHeader() {
        binding.headerGenre.btnBack.setOnClickListener { onBackPressed() }
        binding.headerGenre.tvTitleHeader.text = getString(R.string.list_genre)
    }

}