package com.lexiread.app.di;

import com.lexiread.app.data.local.dao.HighlightDao;
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
public final class DatabaseModule_ProvideHighlightDaoFactory implements Factory<HighlightDao> {
  private final Provider<LexiReadDatabase> databaseProvider;

  public DatabaseModule_ProvideHighlightDaoFactory(Provider<LexiReadDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public HighlightDao get() {
    return provideHighlightDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideHighlightDaoFactory create(
      Provider<LexiReadDatabase> databaseProvider) {
    return new DatabaseModule_ProvideHighlightDaoFactory(databaseProvider);
  }

  public static HighlightDao provideHighlightDao(LexiReadDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHighlightDao(database));
  }
}
