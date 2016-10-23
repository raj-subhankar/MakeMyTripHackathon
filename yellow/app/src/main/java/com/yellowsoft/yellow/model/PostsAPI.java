package com.yellowsoft.yellow.model;


import com.yellowsoft.yellow.model.pojo.AuthResult;
import com.yellowsoft.yellow.model.pojo.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by subhankar on 10/11/2016.
 */
public class PostsAPI {

    public interface PostService {
        @GET("/posts/all")
        Observable<List<Post>> getPostsList();
    }

    private Observable<List<Post>> postsObservable = new Retrofit.Builder()
            .baseUrl("http://52.38.153.65:3000")
            .build().create(PostService.class).getPostsList().cache();

    public Observable<List<Post>> getPostsObservable() {
        return postsObservable;
    }
}
