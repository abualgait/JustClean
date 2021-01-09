/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.network

import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import io.reactivex.Observable


class ApiRepository(private val api: ApiInterface) {

    fun getPosts(): Observable<List<PostsResponseItem>> =
        api.getPosts()

    fun getCommentsByPostId(postId: Int): Observable<List<CommentsResponseItem>> =
        api.getCommentsByPostId(postId)


}
