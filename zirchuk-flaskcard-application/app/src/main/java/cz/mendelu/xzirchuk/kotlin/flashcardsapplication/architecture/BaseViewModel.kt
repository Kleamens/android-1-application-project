package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel:ViewModel(),CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext //alows to  use launch() that sends a process to the background
            = Dispatchers.Main + job

}