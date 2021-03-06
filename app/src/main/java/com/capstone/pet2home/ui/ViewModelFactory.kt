package com.capstone.pet2home.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.pet2home.api.ApiConfig
import com.capstone.pet2home.preference.UserPreference
import com.capstone.pet2home.ui.home.HomeViewModel
import com.capstone.pet2home.ui.lens.LensViewModel
import com.capstone.pet2home.ui.login.LoginViewModel
import com.capstone.pet2home.ui.postadd.PostAddViewModel
import com.capstone.pet2home.ui.postdetail.PostDetailViewModel
import com.capstone.pet2home.ui.postedit.PostEditViewModel
import com.capstone.pet2home.ui.profile.ProfileViewModel
import com.capstone.pet2home.ui.register.RegisterViewModel
import com.capstone.pet2home.ui.search.SearchViewModel
import com.capstone.pet2home.ui.settings.SettingsViewModel
import com.capstone.pet2home.ui.settings.changepassword.ChangePasswordViewModel
import com.capstone.pet2home.ui.settings.editprofile.EditProfileViewModel
import com.capstone.pet2home.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory(private val pref: UserPreference, private val context: Context): ViewModelProvider.NewInstanceFactory() {

    private val apiService = ApiConfig.getApiService()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PostAddViewModel::class.java) -> {
                PostAddViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PostEditViewModel::class.java) -> {
                PostEditViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PostDetailViewModel::class.java) -> {
                PostDetailViewModel(pref) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LensViewModel::class.java) -> {
                LensViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}