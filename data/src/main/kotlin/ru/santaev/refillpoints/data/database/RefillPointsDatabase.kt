package ru.santaev.refillpoints.data.database

import io.reactivex.Completable
import io.reactivex.Flowable

internal class RefillPointsDatabase: IRefillPointsDatabase {

    override fun getRefillPoints(): Flowable<List<IRefillPointsDatabase.RefillPointDto>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(entities: List<IRefillPointsDatabase.RefillPointDto>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(refillPointDto: IRefillPointsDatabase.RefillPointDto): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}