package com.nougust3.weather.Presentation.Common.Rx;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulersProvider {

    @Inject
    public RxSchedulersProvider() {
    }

    public CompletableTransformer getIoToMainTransformerCompletableCompletable() {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable objectObservable) {
                return objectObservable
                        .subscribeOn(RxSchedulersProvider.this.getIOScheduler())
                        .observeOn(RxSchedulersProvider.this.getMainThreadScheduler());
            }
        };
    }

    public <T> SingleTransformer<T, T> getIoToMainTransformerSingle()  {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> objectObservable) {
                return objectObservable
                        .subscribeOn(RxSchedulersProvider.this.getIOScheduler())
                        .observeOn(RxSchedulersProvider.this.getMainThreadScheduler());
            }
        };
    }

    public CompletableTransformer getComputationToMainTransformerCompletable() {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable objectObservable) {
                return objectObservable
                        .subscribeOn(RxSchedulersProvider.this.getComputationScheduler())
                        .observeOn(RxSchedulersProvider.this.getMainThreadScheduler());
            }
        };
    }

    public <T> SingleTransformer<T, T> getComputationToMainTransformerSingle()  {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> objectObservable) {
                return objectObservable
                        .subscribeOn(RxSchedulersProvider.this.getComputationScheduler())
                        .observeOn(RxSchedulersProvider.this.getMainThreadScheduler());
            }
        };
    }

    public Scheduler getMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler getIOScheduler() {
        return Schedulers.io();
    }

    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }

}