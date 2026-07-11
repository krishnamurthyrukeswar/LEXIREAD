package com.lexiread.app.di;

import com.lexiread.app.data.local.dao.ReadingProgressDao;
import com.lexiread.app.data.local.db.LexiReadDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideReadingProgressDaoFactory implements Factory<ReadingProgressDao> {
  private final Provider<LexiReadDatabase> databaseProvider;

  public DatabaseModule_ProvideReadingProgressDaoFactory(
      Provider<LexiReadDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ReadingProgressDao get() {
    return provideReadingProgressDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideReadingProgressDaoFactory create(
      Provider<LexiReadDatabase> databaseProvider) {
    return new DatabaseModule_ProvideReadingProgressDaoFactory(databaseProvider);
  }

  public static ReadingProgressDao provideReadingProgressDao(LexiReadDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideReadingProgressDao(database));
  }
}
