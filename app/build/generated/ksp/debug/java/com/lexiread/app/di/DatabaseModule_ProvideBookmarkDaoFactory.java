package com.lexiread.app.di;

import com.lexiread.app.data.local.dao.BookmarkDao;
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
public final class DatabaseModule_ProvideBookmarkDaoFactory implements Factory<BookmarkDao> {
  private final Provider<LexiReadDatabase> databaseProvider;

  public DatabaseModule_ProvideBookmarkDaoFactory(Provider<LexiReadDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public BookmarkDao get() {
    return provideBookmarkDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideBookmarkDaoFactory create(
      Provider<LexiReadDatabase> databaseProvider) {
    return new DatabaseModule_ProvideBookmarkDaoFactory(databaseProvider);
  }

  public static BookmarkDao provideBookmarkDao(LexiReadDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideBookmarkDao(database));
  }
}
