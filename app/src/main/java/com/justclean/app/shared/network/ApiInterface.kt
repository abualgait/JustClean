/*
 * Created by  Muhammad Sayed  on 1/9/21 9:37 AM
 * Copyright (c) JustClean. All rights reserved.
 */

package com.justclean.app.shared.network

import com.justclean.app.shared.data.model.posts.PostsResponseItem
import com.justclean.app.shared.data.model.posts.comments.CommentsResponseItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


@JvmSuppressWildcards
interface ApiInterface {

    @GET("posts")
    fun getPosts(
    ): Observable<List<PostsResponseItem>>

    @GET("posts/{post_id}/comments")
    fun getCommentsByPostId(
        @Path("post_id") post_id: Int
    ): Observable<List<CommentsResponseItem>>


}