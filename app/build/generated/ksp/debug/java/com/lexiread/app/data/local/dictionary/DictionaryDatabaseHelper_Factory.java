package com.lexiread.app.data.local.dictionary;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class DictionaryDatabaseHelper_Factory implements Factory<DictionaryDatabaseHelper> {
  private final Provider<Context> contextProvider;

  public DictionaryDatabaseHelper_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DictionaryDatabaseHelper get() {
    return newInstance(contextProvider.get());
  }

  public static DictionaryDatabaseHelper_Factory create(Provider<Context> contextProvider) {
    return new DictionaryDatabaseHelper_Factory(contextProvider);
  }

  public static DictionaryDatabaseHelper newInstance(Context context) {
    return new DictionaryDatabaseHelper(context);
  }
}
