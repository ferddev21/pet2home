package com.capstone.pet2home.ui.postdetail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.pet2home.BuildConfig
import com.capstone.pet2home.R
import com.capstone.pet2home.api.response.DataItem
import com.capstone.pet2home.api.response.DataItemPet
import com.capstone.pet2home.databinding.ActivityPostDetailBinding
import com.capstone.pet2home.helper.withDateFormat
import com.capstone.pet2home.preference.UserPreference
import com.capstone.pet2home.ui.ViewModelFactory
import com.capstone.pet2home.ui.postedit.PostEditActivity
import es.dmoral.toasty.Toasty


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var postDetailViewModel: PostDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        buttonHandler()
    }

    private fun buttonHandler() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnFavorite.setOnClickListener {

        }

        binding.btnShere.setOnClickListener {
            shareIntent()
        }
    }

    private fun shareIntent() {
        val shareIntent = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT, "we are sharing")
            this.type = "text/plain"
        }
    }

    private fun setupViewModel() {
        postDetailViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore),this)
        )[PostDetailViewModel::class.java]

        postDetailViewModel.getUser().observe(this) {
            val getPetId = intent.getStringExtra(PostEditActivity.EXTRA_ID_POST)
            if(getPetId != null) {
                postDetailViewModel.getPetApi(getPetId, it.token)
            }
        }

        postDetailViewModel.dataPost.observe(this){
            if(it != null) setDataPost(it)
        }

        postDetailViewModel.dataUser.observe(this){
            if(it != null) setDataUser(it)
        }

        postDetailViewModel.showLoading.observe(this) {showLoading(it)}
    }

    private fun setDataUser(data: DataItem) {
        binding.apply {
            Glide.with(ivAvatarPost).load(URL_IMAGE + data.avatar).circleCrop().into(ivAvatarPost)
            tvUsernamePost.text = data.role
            tvFullnamePost.text = data.username
        }
    }

    private fun setDataPost(data: DataItemPet) {
        binding.apply {
            Glide.with(imgItemPhotoPost).load(URL_IMAGE + data.pic).into(imgItemPhotoPost)

            tvCreateDatePost.text = data.createdAt.withDateFormat()
            tvTitlePost.text = data.title
            tvAge.text = data.age
            tvLocationPost.text = data.location
            tvDescriptionPost.text = data.description

            if(intent.getStringExtra(EXTRA_DISTANCE).isNullOrEmpty()){
                tvLocationDistance.text = getString(R.string.text_post_location)
            }else{
                tvLocationDistance.text = "${intent.getStringExtra(EXTRA_DISTANCE)} Km"
            }

            if(data.breed == "Cat"){
                ivBreedAvatar.setBackgroundResource(R.drawable.ic_cat)
            }else{
                ivBreedAvatar.setBackgroundResource(R.drawable.ic_dog)
            }


            btnContactWhatsapp.apply {
                isGone = data.whatsapp.isEmpty()

                setOnClickListener {
                    Toasty.info(this@PostDetailActivity,data.whatsapp,Toast.LENGTH_SHORT,true).show()
                }
            }

            btnContactInstagram.apply {
                isGone = data.insta.isEmpty()

                setOnClickListener {
                    Toasty.info(this@PostDetailActivity,data.insta,Toast.LENGTH_SHORT,true).show()
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.scrollView2.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.scrollView2.visibility = View.VISIBLE
        }
    }

    companion object {
        const val EXTRA_DISTANCE = "extra_distance"
        const val TAG = "PostDetailActivity"
        const val EXTRA_ID_POST = "extra_id_post"
        const val URL_IMAGE = BuildConfig.BASE_URL + "public/upload/"
    }
}