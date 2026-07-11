package com.lexiread.app.ui.dictionary;

import com.lexiread.app.domain.repository.DictionaryRepository;
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
public final class DictionaryViewModel_Factory implements Factory<DictionaryViewModel> {
  private final Provider<DictionaryRepository> dictionaryRepositoryProvider;

  public DictionaryViewModel_Factory(Provider<DictionaryRepository> dictionaryRepositoryProvider) {
    this.dictionaryRepositoryProvider = dictionaryRepositoryProvider;
  }

  @Override
  public DictionaryViewModel get() {
    return newInstance(dictionaryRepositoryProvider.get());
  }

  public static DictionaryViewModel_Factory create(
      Provider<DictionaryRepository> dictionaryRepositoryProvider) {
    return new DictionaryViewModel_Factory(dictionaryRepositoryProvider);
  }

  public static DictionaryViewModel newInstance(DictionaryRepository dictionaryRepository) {
    return new DictionaryViewModel(dictionaryRepository);
  }
}
