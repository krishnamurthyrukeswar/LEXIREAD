package com.lexiread.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lexiread.app.data.local.entity.BookEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BookDao_Impl implements BookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BookEntity> __insertionAdapterOfBookEntity;

  private final EntityDeletionOrUpdateAdapter<BookEntity> __deletionAdapterOfBookEntity;

  private final EntityDeletionOrUpdateAdapter<BookEntity> __updateAdapterOfBookEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFavoriteStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastOpened;

  public BookDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookEntity = new EntityInsertionAdapter<BookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `books` (`id`,`title`,`author`,`format`,`file_path`,`cover_image_path`,`file_size`,`total_pages`,`language`,`publisher`,`isbn`,`description`,`is_favorite`,`firebase_id`,`added_at`,`last_opened_at`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getAuthor());
        statement.bindString(4, entity.getFormat());
        statement.bindString(5, entity.getFilePath());
        if (entity.getCoverImagePath() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCoverImagePath());
        }
        statement.bindLong(7, entity.getFileSize());
        statement.bindLong(8, entity.getTotalPages());
        if (entity.getLanguage() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getLanguage());
        }
        if (entity.getPublisher() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPublisher());
        }
        if (entity.getIsbn() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getIsbn());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getDescription());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(13, _tmp);
        if (entity.getFirebaseId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getFirebaseId());
        }
        statement.bindLong(15, entity.getAddedAt());
        if (entity.getLastOpenedAt() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getLastOpenedAt());
        }
      }
    };
    this.__deletionAdapterOfBookEntity = new EntityDeletionOrUpdateAdapter<BookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `books` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfBookEntity = new EntityDeletionOrUpdateAdapter<BookEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `books` SET `id` = ?,`title` = ?,`author` = ?,`format` = ?,`file_path` = ?,`cover_image_path` = ?,`file_size` = ?,`total_pages` = ?,`language` = ?,`publisher` = ?,`isbn` = ?,`description` = ?,`is_favorite` = ?,`firebase_id` = ?,`added_at` = ?,`last_opened_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getAuthor());
        statement.bindString(4, entity.getFormat());
        statement.bindString(5, entity.getFilePath());
        if (entity.getCoverImagePath() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCoverImagePath());
        }
        statement.bindLong(7, entity.getFileSize());
        statement.bindLong(8, entity.getTotalPages());
        if (entity.getLanguage() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getLanguage());
        }
        if (entity.getPublisher() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPublisher());
        }
        if (entity.getIsbn() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getIsbn());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getDescription());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(13, _tmp);
        if (entity.getFirebaseId() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getFirebaseId());
        }
        statement.bindLong(15, entity.getAddedAt());
        if (entity.getLastOpenedAt() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getLastOpenedAt());
        }
        statement.bindString(17, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateFavoriteStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE books SET is_favorite = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastOpened = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE books SET last_opened_at = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBook(final BookEntity book, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBookEntity.insert(book);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBook(final BookEntity book, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBookEntity.handle(book);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateBook(final BookEntity book, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBookEntity.handle(book);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFavoriteStatus(final String bookId, final boolean isFavorite,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFavoriteStatus.acquire();
        int _argIndex = 1;
        final int _tmp = isFavorite ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, bookId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateFavoriteStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastOpened(final String bookId, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastOpened.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, bookId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLastOpened.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BookEntity>> getAllBooks() {
    final String _sql = "SELECT * FROM books ORDER BY added_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfCoverImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "cover_image_path");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "added_at");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_opened_at");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpCoverImagePath;
            if (_cursor.isNull(_cursorIndexOfCoverImagePath)) {
              _tmpCoverImagePath = null;
            } else {
              _tmpCoverImagePath = _cursor.getString(_cursorIndexOfCoverImagePath);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpPublisher;
            if (_cursor.isNull(_cursorIndexOfPublisher)) {
              _tmpPublisher = null;
            } else {
              _tmpPublisher = _cursor.getString(_cursorIndexOfPublisher);
            }
            final String _tmpIsbn;
            if (_cursor.isNull(_cursorIndexOfIsbn)) {
              _tmpIsbn = null;
            } else {
              _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpFormat,_tmpFilePath,_tmpCoverImagePath,_tmpFileSize,_tmpTotalPages,_tmpLanguage,_tmpPublisher,_tmpIsbn,_tmpDescription,_tmpIsFavorite,_tmpFirebaseId,_tmpAddedAt,_tmpLastOpenedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getBookById(final String bookId,
      final Continuation<? super BookEntity> $completion) {
    final String _sql = "SELECT * FROM books WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BookEntity>() {
      @Override
      @Nullable
      public BookEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfCoverImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "cover_image_path");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "added_at");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_opened_at");
          final BookEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpCoverImagePath;
            if (_cursor.isNull(_cursorIndexOfCoverImagePath)) {
              _tmpCoverImagePath = null;
            } else {
              _tmpCoverImagePath = _cursor.getString(_cursorIndexOfCoverImagePath);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpPublisher;
            if (_cursor.isNull(_cursorIndexOfPublisher)) {
              _tmpPublisher = null;
            } else {
              _tmpPublisher = _cursor.getString(_cursorIndexOfPublisher);
            }
            final String _tmpIsbn;
            if (_cursor.isNull(_cursorIndexOfIsbn)) {
              _tmpIsbn = null;
            } else {
              _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            _result = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpFormat,_tmpFilePath,_tmpCoverImagePath,_tmpFileSize,_tmpTotalPages,_tmpLanguage,_tmpPublisher,_tmpIsbn,_tmpDescription,_tmpIsFavorite,_tmpFirebaseId,_tmpAddedAt,_tmpLastOpenedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<BookEntity> getBookByIdFlow(final String bookId) {
    final String _sql = "SELECT * FROM books WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<BookEntity>() {
      @Override
      @Nullable
      public BookEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfCoverImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "cover_image_path");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "added_at");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_opened_at");
          final BookEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpCoverImagePath;
            if (_cursor.isNull(_cursorIndexOfCoverImagePath)) {
              _tmpCoverImagePath = null;
            } else {
              _tmpCoverImagePath = _cursor.getString(_cursorIndexOfCoverImagePath);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpPublisher;
            if (_cursor.isNull(_cursorIndexOfPublisher)) {
              _tmpPublisher = null;
            } else {
              _tmpPublisher = _cursor.getString(_cursorIndexOfPublisher);
            }
            final String _tmpIsbn;
            if (_cursor.isNull(_cursorIndexOfIsbn)) {
              _tmpIsbn = null;
            } else {
              _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            _result = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpFormat,_tmpFilePath,_tmpCoverImagePath,_tmpFileSize,_tmpTotalPages,_tmpLanguage,_tmpPublisher,_tmpIsbn,_tmpDescription,_tmpIsFavorite,_tmpFirebaseId,_tmpAddedAt,_tmpLastOpenedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> getFavoriteBooks() {
    final String _sql = "SELECT * FROM books WHERE is_favorite = 1 ORDER BY added_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfCoverImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "cover_image_path");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "added_at");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_opened_at");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpCoverImagePath;
            if (_cursor.isNull(_cursorIndexOfCoverImagePath)) {
              _tmpCoverImagePath = null;
            } else {
              _tmpCoverImagePath = _cursor.getString(_cursorIndexOfCoverImagePath);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpPublisher;
            if (_cursor.isNull(_cursorIndexOfPublisher)) {
              _tmpPublisher = null;
            } else {
              _tmpPublisher = _cursor.getString(_cursorIndexOfPublisher);
            }
            final String _tmpIsbn;
            if (_cursor.isNull(_cursorIndexOfIsbn)) {
              _tmpIsbn = null;
            } else {
              _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpFormat,_tmpFilePath,_tmpCoverImagePath,_tmpFileSize,_tmpTotalPages,_tmpLanguage,_tmpPublisher,_tmpIsbn,_tmpDescription,_tmpIsFavorite,_tmpFirebaseId,_tmpAddedAt,_tmpLastOpenedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> getRecentlyReadBooks(final int limit) {
    final String _sql = "SELECT * FROM books ORDER BY last_opened_at DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfCoverImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "cover_image_path");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "added_at");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_opened_at");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpCoverImagePath;
            if (_cursor.isNull(_cursorIndexOfCoverImagePath)) {
              _tmpCoverImagePath = null;
            } else {
              _tmpCoverImagePath = _cursor.getString(_cursorIndexOfCoverImagePath);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpPublisher;
            if (_cursor.isNull(_cursorIndexOfPublisher)) {
              _tmpPublisher = null;
            } else {
              _tmpPublisher = _cursor.getString(_cursorIndexOfPublisher);
            }
            final String _tmpIsbn;
            if (_cursor.isNull(_cursorIndexOfIsbn)) {
              _tmpIsbn = null;
            } else {
              _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpFormat,_tmpFilePath,_tmpCoverImagePath,_tmpFileSize,_tmpTotalPages,_tmpLanguage,_tmpPublisher,_tmpIsbn,_tmpDescription,_tmpIsFavorite,_tmpFirebaseId,_tmpAddedAt,_tmpLastOpenedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BookEntity>> searchBooks(final String query) {
    final String _sql = "SELECT * FROM books WHERE title LIKE '%' || ? || '%' OR author LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<List<BookEntity>>() {
      @Override
      @NonNull
      public List<BookEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfCoverImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "cover_image_path");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "language");
          final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
          final int _cursorIndexOfIsbn = CursorUtil.getColumnIndexOrThrow(_cursor, "isbn");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "added_at");
          final int _cursorIndexOfLastOpenedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_opened_at");
          final List<BookEntity> _result = new ArrayList<BookEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpAuthor;
            _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            final String _tmpFormat;
            _tmpFormat = _cursor.getString(_cursorIndexOfFormat);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpCoverImagePath;
            if (_cursor.isNull(_cursorIndexOfCoverImagePath)) {
              _tmpCoverImagePath = null;
            } else {
              _tmpCoverImagePath = _cursor.getString(_cursorIndexOfCoverImagePath);
            }
            final long _tmpFileSize;
            _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final String _tmpLanguage;
            if (_cursor.isNull(_cursorIndexOfLanguage)) {
              _tmpLanguage = null;
            } else {
              _tmpLanguage = _cursor.getString(_cursorIndexOfLanguage);
            }
            final String _tmpPublisher;
            if (_cursor.isNull(_cursorIndexOfPublisher)) {
              _tmpPublisher = null;
            } else {
              _tmpPublisher = _cursor.getString(_cursorIndexOfPublisher);
            }
            final String _tmpIsbn;
            if (_cursor.isNull(_cursorIndexOfIsbn)) {
              _tmpIsbn = null;
            } else {
              _tmpIsbn = _cursor.getString(_cursorIndexOfIsbn);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Long _tmpLastOpenedAt;
            if (_cursor.isNull(_cursorIndexOfLastOpenedAt)) {
              _tmpLastOpenedAt = null;
            } else {
              _tmpLastOpenedAt = _cursor.getLong(_cursorIndexOfLastOpenedAt);
            }
            _item = new BookEntity(_tmpId,_tmpTitle,_tmpAuthor,_tmpFormat,_tmpFilePath,_tmpCoverImagePath,_tmpFileSize,_tmpTotalPages,_tmpLanguage,_tmpPublisher,_tmpIsbn,_tmpDescription,_tmpIsFavorite,_tmpFirebaseId,_tmpAddedAt,_tmpLastOpenedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getBookCount() {
    final String _sql = "SELECT COUNT(*) FROM books";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"books"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
