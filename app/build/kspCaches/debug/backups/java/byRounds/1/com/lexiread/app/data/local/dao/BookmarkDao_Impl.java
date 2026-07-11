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
import com.lexiread.app.data.local.entity.BookmarkEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class BookmarkDao_Impl implements BookmarkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BookmarkEntity> __insertionAdapterOfBookmarkEntity;

  private final EntityDeletionOrUpdateAdapter<BookmarkEntity> __deletionAdapterOfBookmarkEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteBookmarksByBook;

  public BookmarkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookmarkEntity = new EntityInsertionAdapter<BookmarkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bookmarks` (`id`,`book_id`,`page_number`,`title`,`chapter`,`firebase_id`,`created_at`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookmarkEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBookId());
        statement.bindLong(3, entity.getPageNumber());
        statement.bindString(4, entity.getTitle());
        if (entity.getChapter() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getChapter());
        }
        if (entity.getFirebaseId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getFirebaseId());
        }
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfBookmarkEntity = new EntityDeletionOrUpdateAdapter<BookmarkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bookmarks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BookmarkEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteBookmarksByBook = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bookmarks WHERE book_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertBookmark(final BookmarkEntity bookmark,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBookmarkEntity.insert(bookmark);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBookmark(final BookmarkEntity bookmark,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBookmarkEntity.handle(bookmark);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteBookmarksByBook(final String bookId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteBookmarksByBook.acquire();
        int _argIndex = 1;
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
          __preparedStmtOfDeleteBookmarksByBook.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BookmarkEntity>> getBookmarksByBook(final String bookId) {
    final String _sql = "SELECT * FROM bookmarks WHERE book_id = ? ORDER BY page_number ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bookmarks"}, new Callable<List<BookmarkEntity>>() {
      @Override
      @NonNull
      public List<BookmarkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<BookmarkEntity> _result = new ArrayList<BookmarkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookmarkEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpChapter;
            if (_cursor.isNull(_cursorIndexOfChapter)) {
              _tmpChapter = null;
            } else {
              _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            }
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new BookmarkEntity(_tmpId,_tmpBookId,_tmpPageNumber,_tmpTitle,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt);
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
  public Flow<List<BookmarkEntity>> getAllBookmarks() {
    final String _sql = "SELECT * FROM bookmarks ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bookmarks"}, new Callable<List<BookmarkEntity>>() {
      @Override
      @NonNull
      public List<BookmarkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<BookmarkEntity> _result = new ArrayList<BookmarkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BookmarkEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpChapter;
            if (_cursor.isNull(_cursorIndexOfChapter)) {
              _tmpChapter = null;
            } else {
              _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            }
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new BookmarkEntity(_tmpId,_tmpBookId,_tmpPageNumber,_tmpTitle,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt);
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
  public Object getBookmarkById(final String bookmarkId,
      final Continuation<? super BookmarkEntity> $completion) {
    final String _sql = "SELECT * FROM bookmarks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookmarkId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BookmarkEntity>() {
      @Override
      @Nullable
      public BookmarkEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final BookmarkEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpChapter;
            if (_cursor.isNull(_cursorIndexOfChapter)) {
              _tmpChapter = null;
            } else {
              _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            }
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new BookmarkEntity(_tmpId,_tmpBookId,_tmpPageNumber,_tmpTitle,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt);
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
  public Object getBookmarkByPage(final String bookId, final int pageNumber,
      final Continuation<? super BookmarkEntity> $completion) {
    final String _sql = "SELECT * FROM bookmarks WHERE book_id = ? AND page_number = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, pageNumber);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BookmarkEntity>() {
      @Override
      @Nullable
      public BookmarkEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final BookmarkEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpChapter;
            if (_cursor.isNull(_cursorIndexOfChapter)) {
              _tmpChapter = null;
            } else {
              _tmpChapter = _cursor.getString(_cursorIndexOfChapter);
            }
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new BookmarkEntity(_tmpId,_tmpBookId,_tmpPageNumber,_tmpTitle,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
