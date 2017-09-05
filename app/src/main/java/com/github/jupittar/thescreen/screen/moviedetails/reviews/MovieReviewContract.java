package com.github.jupittar.thescreen.screen.moviedetails.reviews;

import com.github.jupittar.thescreen.data.entity.ReviewWrapper;
import com.github.jupittar.thescreen.data.remote.response.Review;
import com.github.jupittar.thescreen.screen.base.Contract;

import java.util.List;

import io.reactivex.Observable;


public interface MovieReviewContract {

    interface View extends Contract.View {

        void showReviews(List<Review> reviews);
    }

    interface Presenter<V extends View> extends Contract.Presenter<V> {

        void showReviews(long movieId);
    }

    interface interactor {
        Observable<ReviewWrapper> getReviews(long movieId);
    }

}
