package com.evgeniy.royality.Data;

import android.content.Context;
import android.support.v4.util.Pair;

import com.evgeniy.royality.Net.Api;
import com.evgeniy.royality.Net.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    private Api api;
    private Context context;

    public Repository(Api api, Context context) {
        this.api = api;
        this.context = context;
    }

    public Observable<User> login(final String number) {
        return api.getUsers()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                })
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(User user) throws Exception {
                        return user.getPhone().equals(number);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Pair<String, String>> getBill(final String userName) {
        return api.getUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                })
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(User user) throws Exception {
                        return user.getCorName().equals(userName);
                    }
                })
                .map(new Function<User, Pair<String, String>>() {
                    @Override
                    public Pair<String, String> apply(User user) throws Exception {
                        return new Pair<>(user.getCor_bill_1(), user.getCor_bill_2());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
