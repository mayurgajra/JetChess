package com.mayurg.jetchess.framework.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayurg.jetchess.business.data.local.abstraction.JetChessLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Used to communicate between screens.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val jetChessLocalDataSource: JetChessLocalDataSource
) : ViewModel() {

    private val _currentScreen = MutableLiveData<Screens>(Screens.MainScreens.Users)
    val currentScreen: LiveData<Screens> = _currentScreen

    private val _userName = MutableLiveData<String>()
    val userName = _userName

    fun setCurrentScreen(screen: Screens) {
        _currentScreen.value = screen
    }

    init {
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            val user = jetChessLocalDataSource.getUserInfo()
            user?.let {
                _userName.value = it.fullName
            }
        }
    }

    fun clearUserInfo() {
        viewModelScope.launch {
            jetChessLocalDataSource.clearUserInfo()
        }
    }

}