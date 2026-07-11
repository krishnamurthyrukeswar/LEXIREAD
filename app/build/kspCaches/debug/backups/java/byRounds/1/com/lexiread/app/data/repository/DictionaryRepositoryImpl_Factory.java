package com.lexiread.app.data.repository;

import com.lexiread.app.data.local.dao.DictionaryCacheDao;
import com.lexiread.app.data.local.dictionary.DictionaryDatabaseHelper;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DictionaryRepositoryImpl_Factory implements Factory<DictionaryRepositoryImpl> {
  private final Provider<DictionaryDatabaseHelper> dictionaryHelperProvider;

  private final Provider<DictionaryCacheDao> cacheDaoProvider;

  public DictionaryRepositoryImpl_Factory(
      Provider<DictionaryDatabaseHelper> dictionaryHelperProvider,
      Provider<DictionaryCacheDao> cacheDaoProvider) {
    this.dictionaryHelperProvider = dictionaryHelperProvider;
    this.cacheDaoProvider = cacheDaoProvider;
  }

  @Override
  public DictionaryRepositoryImpl get() {
    return newInstance(dictionaryHelperProvider.get(), cacheDaoProvider.get());
  }

  public static DictionaryRepositoryImpl_Factory create(
      Provider<DictionaryDatabaseHelper> dictionaryHelperProvider,
      Provider<DictionaryCacheDao> cacheDaoProvider) {
    return new DictionaryRepositoryImpl_Factory(dictionaryHelperProvider, cacheDaoProvider);
  }

  public static DictionaryRepositoryImpl newInstance(DictionaryDatabaseHelper dictionaryHelper,
      DictionaryCacheDao cacheDao) {
    return new DictionaryRepositoryImpl(dictionaryHelper, cacheDao);
  }
}
