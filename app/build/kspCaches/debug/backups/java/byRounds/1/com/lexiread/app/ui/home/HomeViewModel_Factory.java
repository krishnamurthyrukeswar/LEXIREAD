package com.lexiread.app.ui.home;

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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<BookRepository> bookRepositoryProvider;

  private final Provider<ReadingProgressRepository> progressRepositoryProvider;

  public HomeViewModel_Factory(Provider<BookRepository> bookRepositoryProvider,
      Provider<ReadingProgressRepository> progressRepositoryProvider) {
    this.bookRepositoryProvider = bookRepositoryProvider;
    this.progressRepositoryProvider = progressRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(bookRepositoryProvider.get(), progressRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<BookRepository> bookRepositoryProvider,
      Provider<ReadingProgressRepository> progressRepositoryProvider) {
    return new HomeViewModel_Factory(bookRepositoryProvider, progressRepositoryProvider);
  }

  public static HomeViewModel newInstance(BookRepository bookRepository,
      ReadingProgressRepository progressRepository) {
    return new HomeViewModel(bookRepository, progressRepository);
  }
}
