package com.lexiread.app.di;

import com.lexiread.app.data.local.dao.NoteDao;
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
public final class DatabaseModule_ProvideNoteDaoFactory implements Factory<NoteDao> {
  private final Provider<LexiReadDatabase> databaseProvider;

  public DatabaseModule_ProvideNoteDaoFactory(Provider<LexiReadDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public NoteDao get() {
    return provideNoteDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideNoteDaoFactory create(
      Provider<LexiReadDatabase> databaseProvider) {
    return new DatabaseModule_ProvideNoteDaoFactory(databaseProvider);
  }

  public static NoteDao provideNoteDao(LexiReadDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNoteDao(database));
  }
}
