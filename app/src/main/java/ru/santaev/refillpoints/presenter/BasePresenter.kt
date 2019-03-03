package ru.santaev.refillpoints.presenter

abstract class BasePresenter<View>(
    protected var view: View?
) {

    open fun onDestroy() {
        view = null
    }
}