package com.lexiread.app.ui.splash

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.lexiread.app.R

public class SplashFragmentDirections private constructor() {
  public companion object {
    public fun actionSplashToAuth(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splash_to_auth)

    public fun actionSplashToHome(): NavDirections =
        ActionOnlyNavDirections(R.id.action_splash_to_home)
  }
}
