package com.github.jupittar.thescreen.screen.moviedetails.cast;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.jupittar.commlib.recyclerview.CommonViewHolder;
import com.github.jupittar.commlib.recyclerview.adapter.CommonViewAdapter;
import com.github.jupittar.thescreen.AppComponent;
import com.github.jupittar.thescreen.R;
import com.github.jupittar.thescreen.data.remote.response.CastBean;
import com.github.jupittar.thescreen.screen.base.LazyFragment;
import com.github.jupittar.thescreen.util.Constants;
import com.github.jupittar.thescreen.util.TypefaceUtils;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class MovieCastFragment
        extends LazyFragment
        implements MovieCastContract.View {

    @BindView(R.id.pb_loading) ContentLoadingProgressBar mLoadingProgressBar;
    @BindView(R.id.rv_cast) RecyclerView mCastRv;

    @Inject MovieCastContract.Presenter<MovieCastContract.View> mPresenter;
    private CastAdapter mCastAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_cast, container, false);
    }

    @Override
    protected void injectDependencies(Context context, AppComponent appComponent) {
        appComponent.plus(new MovieCastModule(this)).inject(this);
    }

    @Override
    public void onFirstAppear() {
        setUpCastRv();
        mPresenter.showCast();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detach();
        mPresenter = null;
        super.onDestroyView();
    }

    private void setUpCastRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mCastRv.setLayoutManager(layoutManager);
        mCastRv.setHasFixedSize(true);
        mCastAdapter = new CastAdapter(getContext(), R.layout.item_cast);
        mCastRv.setAdapter(mCastAdapter);
    }

    //region Implementation of MovieCastContract.View
    @Override
    public void showLoading() {
        mLoadingProgressBar.show();
    }

    @Override
    public void hideLoading() {
        mLoadingProgressBar.hide();
    }

    @Override
    public void showErrorMessage(Throwable throwable) {
        Toasty.error(getContext(), throwable.getMessage()).show();
    }

    @Override
    public void showCast(List<CastBean> cast) {
        mCastAdapter.addAll(cast);
    }
    //endregion

    //region Adapters
    private class CastAdapter extends CommonViewAdapter<CastBean> {

        public CastAdapter(Context context, @LayoutRes int layoutId) {
            super(context, layoutId);
        }

        @Override
        public void convertView(CommonViewHolder holder, CastBean item) {
            CircleImageView castIv = holder.getView(R.id.iv_cast);
            TextView castNameTv = holder.getView(R.id.tv_cast_name);
            TextView roleNameTv = holder.getView(R.id.tv_role_name);
            Typeface typeface = TypefaceUtils.getTypeface(TypefaceUtils.FONT_EXO2_REGULAR, mContext);
            castNameTv.setTypeface(typeface);
            roleNameTv.setTypeface(typeface);
            String profileUrl = String.format(Locale.ENGLISH, "%s%s%s",
                    Constants.IMAGE_BASE_URL, Constants.IMAGE_SIZE_H632, item.getProfilePath());
            Glide.with(mContext)
                    .load(profileUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(castIv);
            castNameTv.setText(item.getName());
            roleNameTv.setText(String.format(Locale.ENGLISH, "as  %s", item.getCharacter()));
        }
    }
    //endregion

}
