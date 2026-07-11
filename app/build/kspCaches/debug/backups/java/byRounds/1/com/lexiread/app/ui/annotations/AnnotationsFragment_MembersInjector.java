package com.lexiread.app.ui.annotations;

import com.lexiread.app.data.local.dao.BookmarkDao;
import com.lexiread.app.data.local.dao.HighlightDao;
import com.lexiread.app.data.local.dao.NoteDao;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AnnotationsFragment_MembersInjector implements MembersInjector<AnnotationsFragment> {
  private final Provider<BookmarkDao> bookmarkDaoProvider;

  private final Provider<HighlightDao> highlightDaoProvider;

  private final Provider<NoteDao> noteDaoProvider;

  public AnnotationsFragment_MembersInjector(Provider<BookmarkDao> bookmarkDaoProvider,
      Provider<HighlightDao> highlightDaoProvider, Provider<NoteDao> noteDaoProvider) {
    this.bookmarkDaoProvider = bookmarkDaoProvider;
    this.highlightDaoProvider = highlightDaoProvider;
    this.noteDaoProvider = noteDaoProvider;
  }

  public static MembersInjector<AnnotationsFragment> create(
      Provider<BookmarkDao> bookmarkDaoProvider, Provider<HighlightDao> highlightDaoProvider,
      Provider<NoteDao> noteDaoProvider) {
    return new AnnotationsFragment_MembersInjector(bookmarkDaoProvider, highlightDaoProvider, noteDaoProvider);
  }

  @Override
  public void injectMembers(AnnotationsFragment instance) {
    injectBookmarkDao(instance, bookmarkDaoProvider.get());
    injectHighlightDao(instance, highlightDaoProvider.get());
    injectNoteDao(instance, noteDaoProvider.get());
  }

  @InjectedFieldSignature("com.lexiread.app.ui.annotations.AnnotationsFragment.bookmarkDao")
  public static void injectBookmarkDao(AnnotationsFragment instance, BookmarkDao bookmarkDao) {
    instance.bookmarkDao = bookmarkDao;
  }

  @InjectedFieldSignature("com.lexiread.app.ui.annotations.AnnotationsFragment.highlightDao")
  public static void injectHighlightDao(AnnotationsFragment instance, HighlightDao highlightDao) {
    instance.highlightDao = highlightDao;
  }

  @InjectedFieldSignature("com.lexiread.app.ui.annotations.AnnotationsFragment.noteDao")
  public static void injectNoteDao(AnnotationsFragment instance, NoteDao noteDao) {
    instance.noteDao = noteDao;
  }
}
