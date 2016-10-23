package com.yellowsoft.yellow.ui.presenters;

import android.util.Log;

import com.yellowsoft.yellow.events.ErrorEvent;
import com.yellowsoft.yellow.events.NewPostsEvent;
import com.yellowsoft.yellow.model.PostsAPI;
import com.yellowsoft.yellow.model.pojo.Post;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhankar on 10/11/2016.
 */
public class PostsPresenter {
    PostsAPI postsAPI;

    @Inject
    public PostsPresenter(PostsAPI postsAPI) {
        this.postsAPI = postsAPI;
    }

    public void loadPostsFromAPI() {
        postsAPI.getPostsObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Post>>() {
                    @Override
                    public void onNext(List<Post> newPosts) {
                        EventBus.getDefault().post(new NewPostsEvent(newPosts));
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onerror", e.toString());
                        EventBus.getDefault().post(new ErrorEvent());
                    }
                });
    }
}
