package com.wizeline.cryptoconverter.mvvm;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

/**
 * Created by Nicole Terc on 7/20/17.
 */

public abstract class ViewModel extends BaseObservable {

    public ViewModel(@Nullable State savedInstanceState) {
    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public State getInstanceState() {
        return new State(this);
    }

    public static class State implements Parcelable {
        public State(ViewModel viewModel) {
        }

        protected State(Parcel in) {
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            @Override
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            @Override
            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }
}
