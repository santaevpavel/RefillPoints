package ru.santaev.refillpoints.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View>(
    var view: View?
) {
    private val disposables = CompositeDisposable()

    fun registerDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    open fun onDestroy() {
        view = null
        disposables.dispose()
    }
}