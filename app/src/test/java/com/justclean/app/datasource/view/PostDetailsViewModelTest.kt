package com.justclean.app.datasource.view

import androidx.lifecycle.Observer
import com.justclean.app.datasource.BaseUnitTest
import com.justclean.app.shared.data.model.Resource
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import com.justclean.app.shared.network.ApiRepository
import com.justclean.app.ui.navhostactivity.postdetails.PostDetailsFragVm
import org.junit.Test
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito

class PostDetailsViewModelTest : BaseUnitTest() {

    val repository by inject<ApiRepository>()
    private val postDetailsViewModel: PostDetailsFragVm by inject()

    @Mock
    lateinit var uiData: Observer<Resource<List<CommentsResponseItem>>>


    @Test
    fun testGetPostDetails() {
        postDetailsViewModel.response.observeForever(uiData)
        val list = repository.getCommentsByPostId(1).blockingFirst()
        val first = list.first()
        postDetailsViewModel.getCommentsByPostId(first.id!!)
        Mockito.verify(uiData).onChanged(Resource.loading(null))


    }
}