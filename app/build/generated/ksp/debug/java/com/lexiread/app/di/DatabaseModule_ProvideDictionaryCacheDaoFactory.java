package com.lexiread.app.di;

import com.lexiread.app.data.local.dao.DictionaryCacheDao;
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
public final class DatabaseModule_ProvideDictionaryCacheDaoFactory implements Factory<DictionaryCacheDao> {
  private final Provider<LexiReadDatabase> databaseProvider;

  public DatabaseModule_ProvideDictionaryCacheDaoFactory(
      Provider<LexiReadDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DictionaryCacheDao get() {
    return provideDictionaryCacheDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideDictionaryCacheDaoFactory create(
      Provider<LexiReadDatabase> databaseProvider) {
    return new DatabaseModule_ProvideDictionaryCacheDaoFactory(databaseProvider);
  }

  public static DictionaryCacheDao provideDictionaryCacheDao(LexiReadDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDictionaryCacheDao(database));
  }
}
