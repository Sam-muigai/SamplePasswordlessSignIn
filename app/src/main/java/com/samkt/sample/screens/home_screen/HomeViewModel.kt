package com.samkt.sample.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samkt.sample.data.FireStoreRepository
import com.samkt.sample.data.model.Posts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.samkt.sample.util.Result

class HomeViewModel : ViewModel() {
    private val fbFirestore = FireStoreRepository()
    private var _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            _homeScreenState.update {
                it.copy(
                    loading = true,
                )
            }
            fbFirestore.getPosts().collectLatest { result ->
                when (result) {
                    is Result.Success-> {
                        result.data?.collectLatest { posts ->
                            _homeScreenState.update {
                                it.copy(
                                    posts = posts ?: emptyList(),
                                    loading = false,
                                )
                            }
                        }
                    }

                    is Result.Error -> {
                        _homeScreenState.update {
                            it.copy(
                                error = result.message,
                            )
                        }
                    }
                }
            }
        }
    }
}

data class HomeScreenState(
    val loading: Boolean = false,
    val posts: List<Posts> = emptyList(),
    val error: String? = null,
)
