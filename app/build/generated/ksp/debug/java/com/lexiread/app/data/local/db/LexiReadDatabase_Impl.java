package com.lexiread.app.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.lexiread.app.data.local.dao.BookDao;
import com.lexiread.app.data.local.dao.BookDao_Impl;
import com.lexiread.app.data.local.dao.BookmarkDao;
import com.lexiread.app.data.local.dao.BookmarkDao_Impl;
import com.lexiread.app.data.local.dao.DictionaryCacheDao;
import com.lexiread.app.data.local.dao.DictionaryCacheDao_Impl;
import com.lexiread.app.data.local.dao.HighlightDao;
import com.lexiread.app.data.local.dao.HighlightDao_Impl;
import com.lexiread.app.data.local.dao.NoteDao;
import com.lexiread.app.data.local.dao.NoteDao_Impl;
import com.lexiread.app.data.local.dao.ReadingProgressDao;
import com.lexiread.app.data.local.dao.ReadingProgressDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LexiReadDatabase_Impl extends LexiReadDatabase {
  private volatile BookDao _bookDao;

  private volatile HighlightDao _highlightDao;

  private volatile NoteDao _noteDao;

  private volatile BookmarkDao _bookmarkDao;

  private volatile ReadingProgressDao _readingProgressDao;

  private volatile DictionaryCacheDao _dictionaryCacheDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `books` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `author` TEXT NOT NULL, `format` TEXT NOT NULL, `file_path` TEXT NOT NULL, `cover_image_path` TEXT, `file_size` INTEGER NOT NULL, `total_pages` INTEGER NOT NULL, `language` TEXT, `publisher` TEXT, `isbn` TEXT, `description` TEXT, `is_favorite` INTEGER NOT NULL, `firebase_id` TEXT, `added_at` INTEGER NOT NULL, `last_opened_at` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `highlights` (`id` TEXT NOT NULL, `book_id` TEXT NOT NULL, `text` TEXT NOT NULL, `color` TEXT NOT NULL, `page_number` INTEGER NOT NULL, `char_start` INTEGER NOT NULL, `char_end` INTEGER NOT NULL, `chapter` TEXT, `firebase_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`book_id`) REFERENCES `books`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_highlights_book_id` ON `highlights` (`book_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` TEXT NOT NULL, `book_id` TEXT NOT NULL, `highlight_id` TEXT, `content` TEXT NOT NULL, `page_number` INTEGER NOT NULL, `chapter` TEXT, `firebase_id` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`book_id`) REFERENCES `books`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notes_book_id` ON `notes` (`book_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (`id` TEXT NOT NULL, `book_id` TEXT NOT NULL, `page_number` INTEGER NOT NULL, `title` TEXT NOT NULL, `chapter` TEXT, `firebase_id` TEXT, `created_at` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`book_id`) REFERENCES `books`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_bookmarks_book_id` ON `bookmarks` (`book_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reading_progress` (`id` TEXT NOT NULL, `book_id` TEXT NOT NULL, `current_page` INTEGER NOT NULL, `total_pages` INTEGER NOT NULL, `percentage` REAL NOT NULL, `last_read_at` INTEGER NOT NULL, `total_reading_time_ms` INTEGER NOT NULL, `firebase_id` TEXT, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`book_id`) REFERENCES `books`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_reading_progress_book_id` ON `reading_progress` (`book_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dictionary_cache` (`word` TEXT NOT NULL, `definition` TEXT NOT NULL, `part_of_speech` TEXT, `phonetic` TEXT, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`word`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b4d2679e7929127acae9543a1d722af1')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `books`");
        db.execSQL("DROP TABLE IF EXISTS `highlights`");
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `bookmarks`");
        db.execSQL("DROP TABLE IF EXISTS `reading_progress`");
        db.execSQL("DROP TABLE IF EXISTS `dictionary_cache`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsBooks = new HashMap<String, TableInfo.Column>(16);
        _columnsBooks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("format", new TableInfo.Column("format", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("file_path", new TableInfo.Column("file_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("cover_image_path", new TableInfo.Column("cover_image_path", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("file_size", new TableInfo.Column("file_size", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("total_pages", new TableInfo.Column("total_pages", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("language", new TableInfo.Column("language", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("publisher", new TableInfo.Column("publisher", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("isbn", new TableInfo.Column("isbn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("is_favorite", new TableInfo.Column("is_favorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("firebase_id", new TableInfo.Column("firebase_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("added_at", new TableInfo.Column("added_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBooks.put("last_opened_at", new TableInfo.Column("last_opened_at", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBooks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBooks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBooks = new TableInfo("books", _columnsBooks, _foreignKeysBooks, _indicesBooks);
        final TableInfo _existingBooks = TableInfo.read(db, "books");
        if (!_infoBooks.equals(_existingBooks)) {
          return new RoomOpenHelper.ValidationResult(false, "books(com.lexiread.app.data.local.entity.BookEntity).\n"
                  + " Expected:\n" + _infoBooks + "\n"
                  + " Found:\n" + _existingBooks);
        }
        final HashMap<String, TableInfo.Column> _columnsHighlights = new HashMap<String, TableInfo.Column>(11);
        _columnsHighlights.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("book_id", new TableInfo.Column("book_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("page_number", new TableInfo.Column("page_number", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("char_start", new TableInfo.Column("char_start", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("char_end", new TableInfo.Column("char_end", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("chapter", new TableInfo.Column("chapter", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("firebase_id", new TableInfo.Column("firebase_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHighlights.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHighlights = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysHighlights.add(new TableInfo.ForeignKey("books", "CASCADE", "NO ACTION", Arrays.asList("book_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesHighlights = new HashSet<TableInfo.Index>(1);
        _indicesHighlights.add(new TableInfo.Index("index_highlights_book_id", false, Arrays.asList("book_id"), Arrays.asList("ASC")));
        final TableInfo _infoHighlights = new TableInfo("highlights", _columnsHighlights, _foreignKeysHighlights, _indicesHighlights);
        final TableInfo _existingHighlights = TableInfo.read(db, "highlights");
        if (!_infoHighlights.equals(_existingHighlights)) {
          return new RoomOpenHelper.ValidationResult(false, "highlights(com.lexiread.app.data.local.entity.HighlightEntity).\n"
                  + " Expected:\n" + _infoHighlights + "\n"
                  + " Found:\n" + _existingHighlights);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(9);
        _columnsNotes.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("book_id", new TableInfo.Column("book_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("highlight_id", new TableInfo.Column("highlight_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("page_number", new TableInfo.Column("page_number", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("chapter", new TableInfo.Column("chapter", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("firebase_id", new TableInfo.Column("firebase_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNotes.add(new TableInfo.ForeignKey("books", "CASCADE", "NO ACTION", Arrays.asList("book_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(1);
        _indicesNotes.add(new TableInfo.Index("index_notes_book_id", false, Arrays.asList("book_id"), Arrays.asList("ASC")));
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.lexiread.app.data.local.entity.NoteEntity).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsBookmarks = new HashMap<String, TableInfo.Column>(7);
        _columnsBookmarks.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("book_id", new TableInfo.Column("book_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("page_number", new TableInfo.Column("page_number", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("chapter", new TableInfo.Column("chapter", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("firebase_id", new TableInfo.Column("firebase_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBookmarks.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBookmarks = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysBookmarks.add(new TableInfo.ForeignKey("books", "CASCADE", "NO ACTION", Arrays.asList("book_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesBookmarks = new HashSet<TableInfo.Index>(1);
        _indicesBookmarks.add(new TableInfo.Index("index_bookmarks_book_id", false, Arrays.asList("book_id"), Arrays.asList("ASC")));
        final TableInfo _infoBookmarks = new TableInfo("bookmarks", _columnsBookmarks, _foreignKeysBookmarks, _indicesBookmarks);
        final TableInfo _existingBookmarks = TableInfo.read(db, "bookmarks");
        if (!_infoBookmarks.equals(_existingBookmarks)) {
          return new RoomOpenHelper.ValidationResult(false, "bookmarks(com.lexiread.app.data.local.entity.BookmarkEntity).\n"
                  + " Expected:\n" + _infoBookmarks + "\n"
                  + " Found:\n" + _existingBookmarks);
        }
        final HashMap<String, TableInfo.Column> _columnsReadingProgress = new HashMap<String, TableInfo.Column>(9);
        _columnsReadingProgress.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("book_id", new TableInfo.Column("book_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("current_page", new TableInfo.Column("current_page", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("total_pages", new TableInfo.Column("total_pages", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("percentage", new TableInfo.Column("percentage", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("last_read_at", new TableInfo.Column("last_read_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("total_reading_time_ms", new TableInfo.Column("total_reading_time_ms", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("firebase_id", new TableInfo.Column("firebase_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReadingProgress.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReadingProgress = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysReadingProgress.add(new TableInfo.ForeignKey("books", "CASCADE", "NO ACTION", Arrays.asList("book_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesReadingProgress = new HashSet<TableInfo.Index>(1);
        _indicesReadingProgress.add(new TableInfo.Index("index_reading_progress_book_id", true, Arrays.asList("book_id"), Arrays.asList("ASC")));
        final TableInfo _infoReadingProgress = new TableInfo("reading_progress", _columnsReadingProgress, _foreignKeysReadingProgress, _indicesReadingProgress);
        final TableInfo _existingReadingProgress = TableInfo.read(db, "reading_progress");
        if (!_infoReadingProgress.equals(_existingReadingProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "reading_progress(com.lexiread.app.data.local.entity.ReadingProgressEntity).\n"
                  + " Expected:\n" + _infoReadingProgress + "\n"
                  + " Found:\n" + _existingReadingProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsDictionaryCache = new HashMap<String, TableInfo.Column>(5);
        _columnsDictionaryCache.put("word", new TableInfo.Column("word", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDictionaryCache.put("definition", new TableInfo.Column("definition", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDictionaryCache.put("part_of_speech", new TableInfo.Column("part_of_speech", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDictionaryCache.put("phonetic", new TableInfo.Column("phonetic", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDictionaryCache.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDictionaryCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDictionaryCache = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDictionaryCache = new TableInfo("dictionary_cache", _columnsDictionaryCache, _foreignKeysDictionaryCache, _indicesDictionaryCache);
        final TableInfo _existingDictionaryCache = TableInfo.read(db, "dictionary_cache");
        if (!_infoDictionaryCache.equals(_existingDictionaryCache)) {
          return new RoomOpenHelper.ValidationResult(false, "dictionary_cache(com.lexiread.app.data.local.entity.DictionaryCacheEntity).\n"
                  + " Expected:\n" + _infoDictionaryCache + "\n"
                  + " Found:\n" + _existingDictionaryCache);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b4d2679e7929127acae9543a1d722af1", "a7af6c5a10eb0e93d3228dd1ae4b4bf2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "books","highlights","notes","bookmarks","reading_progress","dictionary_cache");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `books`");
      _db.execSQL("DELETE FROM `highlights`");
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `bookmarks`");
      _db.execSQL("DELETE FROM `reading_progress`");
      _db.execSQL("DELETE FROM `dictionary_cache`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BookDao.class, BookDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(HighlightDao.class, HighlightDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BookmarkDao.class, BookmarkDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReadingProgressDao.class, ReadingProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DictionaryCacheDao.class, DictionaryCacheDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public BookDao bookDao() {
    if (_bookDao != null) {
      return _bookDao;
    } else {
      synchronized(this) {
        if(_bookDao == null) {
          _bookDao = new BookDao_Impl(this);
        }
        return _bookDao;
      }
    }
  }

  @Override
  public HighlightDao highlightDao() {
    if (_highlightDao != null) {
      return _highlightDao;
    } else {
      synchronized(this) {
        if(_highlightDao == null) {
          _highlightDao = new HighlightDao_Impl(this);
        }
        return _highlightDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public BookmarkDao bookmarkDao() {
    if (_bookmarkDao != null) {
      return _bookmarkDao;
    } else {
      synchronized(this) {
        if(_bookmarkDao == null) {
          _bookmarkDao = new BookmarkDao_Impl(this);
        }
        return _bookmarkDao;
      }
    }
  }

  @Override
  public ReadingProgressDao readingProgressDao() {
    if (_readingProgressDao != null) {
      return _readingProgressDao;
    } else {
      synchronized(this) {
        if(_readingProgressDao == null) {
          _readingProgressDao = new ReadingProgressDao_Impl(this);
        }
        return _readingProgressDao;
      }
    }
  }

  @Override
  public DictionaryCacheDao dictionaryCacheDao() {
    if (_dictionaryCacheDao != null) {
      return _dictionaryCacheDao;
    } else {
      synchronized(this) {
        if(_dictionaryCacheDao == null) {
          _dictionaryCacheDao = new DictionaryCacheDao_Impl(this);
        }
        return _dictionaryCacheDao;
      }
    }
  }
}
