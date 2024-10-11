package com.example.dicodingeventapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent
import com.example.dicodingeventapp.data.local.room.FavoriteEventDao
import com.example.dicodingeventapp.data.remote.response.Event
import com.example.dicodingeventapp.data.remote.response.ListEventsItem
import com.example.dicodingeventapp.data.remote.retrofit.ApiService

class EventRepository private constructor(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao
){
    fun loadFinishedEvents(): LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFinished()
            val events = response.listEvents
            val eventList = events.map { events ->
                ListEventsItem(
                    events.summary,
                    events.mediaCover,
                    events.registrants,
                    events.imageLogo,
                    events.link,
                    events.description,
                    events.ownerName,
                    events.cityName,
                    events.quota,
                    events.name,
                    events.id,
                    events.beginTime,
                    events.endTime,
                    events.category
                )
            }
            emit(Result.Success(eventList))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun loadUpcomingEvents() : LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUpcoming()
            val events = response.listEvents
            val eventList = events.map { events ->
                ListEventsItem(
                    events.summary,
                    events.mediaCover,
                    events.registrants,
                    events.imageLogo,
                    events.link,
                    events.description,
                    events.ownerName,
                    events.cityName,
                    events.quota,
                    events.name,
                    events.id,
                    events.beginTime,
                    events.endTime,
                    events.category
                )
            }
            emit(Result.Success(eventList))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUpcoming5() : LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUpcoming5()
            val events = response.listEvents
            val eventList = events.map { events ->
                ListEventsItem(
                    events.summary,
                    events.mediaCover,
                    events.registrants,
                    events.imageLogo,
                    events.link,
                    events.description,
                    events.ownerName,
                    events.cityName,
                    events.quota,
                    events.name,
                    events.id,
                    events.beginTime,
                    events.endTime,
                    events.category
                )
            }
            emit(Result.Success(eventList))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFinished5() : LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFinished5()
            val events = response.listEvents
            val eventList = events.map { events ->
                ListEventsItem(
                    events.summary,
                    events.mediaCover,
                    events.registrants,
                    events.imageLogo,
                    events.link,
                    events.description,
                    events.ownerName,
                    events.cityName,
                    events.quota,
                    events.name,
                    events.id,
                    events.beginTime,
                    events.endTime,
                    events.category
                )
            }
            emit(Result.Success(eventList))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun searchEvent(q: String) : LiveData<Result<List<ListEventsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.searchEvents(q)
            val events = response.listEvents
            val eventList = events.map { events ->
                ListEventsItem(
                    events.summary,
                    events.mediaCover,
                    events.registrants,
                    events.imageLogo,
                    events.link,
                    events.description,
                    events.ownerName,
                    events.cityName,
                    events.quota,
                    events.name,
                    events.id,
                    events.beginTime,
                    events.endTime,
                    events.category
                )
            }
            emit(Result.Success(eventList))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun detailEvent(id: String): LiveData<Result<Event>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getEvent(id)
            val event = response.event
            emit(Result.Success(event))
        } catch (e: Exception) {
            Log.d("EventRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: EventRepository? = null
        fun getInstance(
            apiService: ApiService,
            eventDao: FavoriteEventDao,
        ): EventRepository =
            instance ?: synchronized(this) {
                instance ?: EventRepository(apiService, eventDao)
            }.also { instance = it }
    }
}