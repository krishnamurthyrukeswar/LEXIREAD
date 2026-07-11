package com.lexiread.app.ui.reader;

import androidx.lifecycle.SavedStateHandle;
import com.lexiread.app.data.local.dao.BookmarkDao;
import com.lexiread.app.data.local.dao.HighlightDao;
import com.lexiread.app.data.local.dao.NoteDao;
import com.lexiread.app.data.local.datastore.UserPreferences;
import com.lexiread.app.domain.repository.BookRepository;
import com.lexiread.app.domain.repository.ReadingProgressRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ReaderViewModel_Factory implements Factory<ReaderViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<BookRepository> bookRepositoryProvider;

  private final Provider<ReadingProgressRepository> progressRepositoryProvider;

  private final Provider<BookmarkDao> bookmarkDaoProvider;

  private final Provider<HighlightDao> highlightDaoProvider;

  private final Provider<NoteDao> noteDaoProvider;

  private final Provider<UserPreferences> userPreferencesProvider;

  public ReaderViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<BookRepository> bookRepositoryProvider,
      Provider<ReadingProgressRepository> progressRepositoryProvider,
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<HighlightDao> highlightDaoProvider,
      Provider<NoteDao> noteDaoProvider, Provider<UserPreferences> userPreferencesProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.bookRepositoryProvider = bookRepositoryProvider;
    this.progressRepositoryProvider = progressRepositoryProvider;
    this.bookmarkDaoProvider = bookmarkDaoProvider;
    this.highlightDaoProvider = highlightDaoProvider;
    this.noteDaoProvider = noteDaoProvider;
    this.userPreferencesProvider = userPreferencesProvider;
  }

  @Override
  public ReaderViewModel get() {
    return newInstance(savedStateHandleProvider.get(), bookRepositoryProvider.get(), progressRepositoryProvider.get(), bookmarkDaoProvider.get(), highlightDaoProvider.get(), noteDaoProvider.get(), userPreferencesProvider.get());
  }

  public static ReaderViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<BookRepository> bookRepositoryProvider,
      Provider<ReadingProgressRepository> progressRepositoryProvider,
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<HighlightDao> highlightDaoProvider,
      Provider<NoteDao> noteDaoProvider, Provider<UserPreferences> userPreferencesProvider) {
    return new ReaderViewModel_Factory(savedStateHandleProvider, bookRepositoryProvider, progressRepositoryProvider, bookmarkDaoProvider, highlightDaoProvider, noteDaoProvider, userPreferencesProvider);
  }

  public static ReaderViewModel newInstance(SavedStateHandle savedStateHandle,
      BookRepository bookRepository, ReadingProgressRepository progressRepository,
      BookmarkDao bookmarkDao, HighlightDao highlightDao, NoteDao noteDao,
      UserPreferences userPreferences) {
    return new ReaderViewModel(savedStateHandle, bookRepository, progressRepository, bookmarkDao, highlightDao, noteDao, userPreferences);
  }
}
