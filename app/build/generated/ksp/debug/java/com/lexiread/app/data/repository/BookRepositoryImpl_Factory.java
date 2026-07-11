package com.lexiread.app.data.repository;

import com.lexiread.app.data.local.dao.BookDao;
import com.lexiread.app.data.local.file.FileManager;
import com.lexiread.app.data.remote.api.GoogleBooksApi;
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
public final class BookRepositoryImpl_Factory implements Factory<BookRepositoryImpl> {
  private final Provider<BookDao> bookDaoProvider;

  private final Provider<GoogleBooksApi> googleBooksApiProvider;

  private final Provider<FileManager> fileManagerProvider;

  public BookRepositoryImpl_Factory(Provider<BookDao> bookDaoProvider,
      Provider<GoogleBooksApi> googleBooksApiProvider, Provider<FileManager> fileManagerProvider) {
    this.bookDaoProvider = bookDaoProvider;
    this.googleBooksApiProvider = googleBooksApiProvider;
    this.fileManagerProvider = fileManagerProvider;
  }

  @Override
  public BookRepositoryImpl get() {
    return newInstance(bookDaoProvider.get(), googleBooksApiProvider.get(), fileManagerProvider.get());
  }

  public static BookRepositoryImpl_Factory create(Provider<BookDao> bookDaoProvider,
      Provider<GoogleBooksApi> googleBooksApiProvider, Provider<FileManager> fileManagerProvider) {
    return new BookRepositoryImpl_Factory(bookDaoProvider, googleBooksApiProvider, fileManagerProvider);
  }

  public static BookRepositoryImpl newInstance(BookDao bookDao, GoogleBooksApi googleBooksApi,
      FileManager fileManager) {
    return new BookRepositoryImpl(bookDao, googleBooksApi, fileManager);
  }
}
