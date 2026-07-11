package com.lexiread.app.ui.reader

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ReaderFragmentArgs(
  public val bookId: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("bookId", this.bookId)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("bookId", this.bookId)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ReaderFragmentArgs {
      bundle.setClassLoader(ReaderFragmentArgs::class.java.classLoader)
      val __bookId : String?
      if (bundle.containsKey("bookId")) {
        __bookId = bundle.getString("bookId")
        if (__bookId == null) {
          throw IllegalArgumentException("Argument \"bookId\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"bookId\" is missing and does not have an android:defaultValue")
      }
      return ReaderFragmentArgs(__bookId)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): ReaderFragmentArgs {
      val __bookId : String?
      if (savedStateHandle.contains("bookId")) {
        __bookId = savedStateHandle["bookId"]
        if (__bookId == null) {
          throw IllegalArgumentException("Argument \"bookId\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"bookId\" is missing and does not have an android:defaultValue")
      }
      return ReaderFragmentArgs(__bookId)
    }
  }
}
