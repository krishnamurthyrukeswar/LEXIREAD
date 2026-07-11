package com.lexiread.app.data.repository;

import com.lexiread.app.data.local.dao.ReadingProgressDao;
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
public final class ReadingProgressRepositoryImpl_Factory implements Factory<ReadingProgressRepositoryImpl> {
  private final Provider<ReadingProgressDao> progressDaoProvider;

  public ReadingProgressRepositoryImpl_Factory(Provider<ReadingProgressDao> progressDaoProvider) {
    this.progressDaoProvider = progressDaoProvider;
  }

  @Override
  public ReadingProgressRepositoryImpl get() {
    return newInstance(progressDaoProvider.get());
  }

  public static ReadingProgressRepositoryImpl_Factory create(
      Provider<ReadingProgressDao> progressDaoProvider) {
    return new ReadingProgressRepositoryImpl_Factory(progressDaoProvider);
  }

  public static ReadingProgressRepositoryImpl newInstance(ReadingProgressDao progressDao) {
    return new ReadingProgressRepositoryImpl(progressDao);
  }
}
