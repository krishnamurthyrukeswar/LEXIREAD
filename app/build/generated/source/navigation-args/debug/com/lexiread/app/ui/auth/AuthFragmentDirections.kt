package com.lexiread.app.ui.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.lexiread.app.R

public class AuthFragmentDirections private constructor() {
  public companion object {
    public fun actionAuthToHome(): NavDirections = ActionOnlyNavDirections(R.id.action_auth_to_home)
  }
}
