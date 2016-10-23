package com.yellowsoft.yellow.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.yellowsoft.yellow.ui.screen_contracts.PostScreen;
import com.yellowsoft.yellow.ui.presenters.PostsPresenter;
import com.yellowsoft.yellow.R;
import com.yellowsoft.yellow.dagger.DaggerInjector;
import com.yellowsoft.yellow.events.ErrorEvent;
import com.yellowsoft.yellow.events.NewPostsEvent;
import com.yellowsoft.yellow.ui.adapters.PostsListAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

public class PostsActivity extends AppCompatActivity implements PostScreen {

    @Inject
    PostsPresenter postsPresenter;

    @InjectView(R.id.posts_recycler_view)
    RecyclerView postsRecyclerView;

    @InjectView(R.id.error_view)
    TextView errorView;

    PostsListAdapter postsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        DaggerInjector.get().inject(this);
        ButterKnife.inject(this);

        initRecyclerView();
        postsPresenter.loadPostsFromAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void initRecyclerView() {
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(postsRecyclerView.getContext()));
        postsListAdapter = new PostsListAdapter(this);
        postsRecyclerView.setAdapter(postsListAdapter);
        Log.d("initRecyclerView", "done");
    }

    public void onEventMainThread(NewPostsEvent newPostsEvent) {
        hideError();
        postsListAdapter.addPosts(newPostsEvent.getPosts());
        Log.d("onEventMainThread", "done");
    }

    public void onEventMainThread(ErrorEvent errorEvent) {
        Log.d("onEventMainThread", "error");
        showError();
    }

    private void hideError() {

    }

    private void showError() {

    }
}
