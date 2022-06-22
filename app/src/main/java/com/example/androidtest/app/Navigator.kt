package com.example.androidtest.app

import android.os.Bundle
import androidx.navigation.NavAction
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import com.example.androidtest.R

object Navigator {

    fun navigate(destination: Int, action: Int, data: Bundle?) {
        val navAction = NavAction(destination)
        val navOptions: NavOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.slide_out)
            .build()
        navAction.navOptions = navOptions
        val currentNode: NavDestination = AppData.navigationManager?.let {
            it.navController?.let { navController ->
                navController?.graph.let { navGraph ->
                    it.navController.currentDestination?.id?.let { id ->
                        navGraph.findNode(id)
                    }
                }
            }
        } as NavDestination
        if (currentNode != null) {
            currentNode.putAction(action, navAction)
            AppData.navigationManager?.let { it.navController.navigate(action,data) }
        }
    }

    fun getCurrentDestinationId(): Int? {
        return AppData.navigationManager?.let { it.navController.currentDestination?.let { currentDestination -> currentDestination.id} }
    }
}