package com.example.aidltest

import android.app.Service
import android.content.Intent
import android.os.IBinder

class EventService : Service() {

    private val eventManager = HashMap<String, ICallBack>()

    public val SUCCESS = 1

    private val mBinder = object : IEventBus.Stub() {
        override fun register(cmd: String?, callback: ICallBack?) {
            if (cmd != null && callback != null) {
                eventManager[cmd] = callback
            }
        }

        override fun unRegister(cmd: String?) {
            if (cmd != null) {
                eventManager.remove(cmd)
            }
        }

        override fun post(event: Event?) {
            if (event != null){
                eventManager[event.cmd]?.onReceived(SUCCESS,event)
            }
        }

    }

    override fun onBind(p0: Intent?): IBinder? {
        return mBinder
    }
}