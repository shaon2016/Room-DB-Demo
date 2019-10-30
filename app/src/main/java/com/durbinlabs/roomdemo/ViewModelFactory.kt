package spartons.com.androidroomcoroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.durbinlabs.roomdemo.interfaces.ClientDao
import com.durbinlabs.roomdemo.viewmodels.ClientVM

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 4/6/19}
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val taskDao: ClientDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientVM::class.java))
            return ClientVM(taskDao) as T
        throw IllegalArgumentException("Unknown View Model class")
    }
}