package com.lexiread.app.ui.home

import android.os.Bundle
import androidx.navigation.NavDirections
import com.lexiread.app.R
import kotlin.Int
import kotlin.String

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeToReader(
    public val bookId: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_home_to_reader

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("bookId", this.bookId)
        return result
      }
  }

  public companion object {
    public fun actionHomeToReader(bookId: String): NavDirections = ActionHomeToReader(bookId)
  }
}
