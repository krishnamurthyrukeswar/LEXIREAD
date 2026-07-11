package com.lexiread.app.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.lexiread.app.data.local.datastore.UserPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class DataStoreModule_ProvideUserPreferencesFactory implements Factory<UserPreferences> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public DataStoreModule_ProvideUserPreferencesFactory(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public UserPreferences get() {
    return provideUserPreferences(dataStoreProvider.get());
  }

  public static DataStoreModule_ProvideUserPreferencesFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new DataStoreModule_ProvideUserPreferencesFactory(dataStoreProvider);
  }

  public static UserPreferences provideUserPreferences(DataStore<Preferences> dataStore) {
    return Preconditions.checkNotNullFromProvides(DataStoreModule.INSTANCE.provideUserPreferences(dataStore));
  }
}
