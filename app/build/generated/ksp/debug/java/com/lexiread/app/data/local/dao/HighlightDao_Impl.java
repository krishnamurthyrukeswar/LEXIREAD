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
import com.lexiread.app.data.local.entity.HighlightEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class HighlightDao_Impl implements HighlightDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HighlightEntity> __insertionAdapterOfHighlightEntity;

  private final EntityDeletionOrUpdateAdapter<HighlightEntity> __deletionAdapterOfHighlightEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteHighlightsByBook;

  public HighlightDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHighlightEntity = new EntityInsertionAdapter<HighlightEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `highlights` (`id`,`book_id`,`text`,`color`,`page_number`,`char_start`,`char_end`,`chapter`,`firebase_id`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HighlightEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBookId());
        statement.bindString(3, entity.getText());
        statement.bindString(4, entity.getColor());
        statement.bindLong(5, entity.getPageNumber());
        statement.bindLong(6, entity.getCharStart());
        statement.bindLong(7, entity.getCharEnd());
        if (entity.getChapter() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getChapter());
        }
        if (entity.getFirebaseId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getFirebaseId());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfHighlightEntity = new EntityDeletionOrUpdateAdapter<HighlightEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `highlights` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final HighlightEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteHighlightsByBook = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM highlights WHERE book_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertHighlight(final HighlightEntity highlight,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfHighlightEntity.insert(highlight);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHighlight(final HighlightEntity highlight,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHighlightEntity.handle(highlight);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteHighlightsByBook(final String bookId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteHighlightsByBook.acquire();
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
          __preparedStmtOfDeleteHighlightsByBook.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<HighlightEntity>> getHighlightsByBook(final String bookId) {
    final String _sql = "SELECT * FROM highlights WHERE book_id = ? ORDER BY page_number ASC, char_start ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"highlights"}, new Callable<List<HighlightEntity>>() {
      @Override
      @NonNull
      public List<HighlightEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfCharStart = CursorUtil.getColumnIndexOrThrow(_cursor, "char_start");
          final int _cursorIndexOfCharEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "char_end");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<HighlightEntity> _result = new ArrayList<HighlightEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighlightEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final int _tmpCharStart;
            _tmpCharStart = _cursor.getInt(_cursorIndexOfCharStart);
            final int _tmpCharEnd;
            _tmpCharEnd = _cursor.getInt(_cursorIndexOfCharEnd);
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
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new HighlightEntity(_tmpId,_tmpBookId,_tmpText,_tmpColor,_tmpPageNumber,_tmpCharStart,_tmpCharEnd,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<HighlightEntity>> getAllHighlights() {
    final String _sql = "SELECT * FROM highlights ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"highlights"}, new Callable<List<HighlightEntity>>() {
      @Override
      @NonNull
      public List<HighlightEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfCharStart = CursorUtil.getColumnIndexOrThrow(_cursor, "char_start");
          final int _cursorIndexOfCharEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "char_end");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<HighlightEntity> _result = new ArrayList<HighlightEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighlightEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final int _tmpCharStart;
            _tmpCharStart = _cursor.getInt(_cursorIndexOfCharStart);
            final int _tmpCharEnd;
            _tmpCharEnd = _cursor.getInt(_cursorIndexOfCharEnd);
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
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new HighlightEntity(_tmpId,_tmpBookId,_tmpText,_tmpColor,_tmpPageNumber,_tmpCharStart,_tmpCharEnd,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getHighlightById(final String highlightId,
      final Continuation<? super HighlightEntity> $completion) {
    final String _sql = "SELECT * FROM highlights WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, highlightId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<HighlightEntity>() {
      @Override
      @Nullable
      public HighlightEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfCharStart = CursorUtil.getColumnIndexOrThrow(_cursor, "char_start");
          final int _cursorIndexOfCharEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "char_end");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final HighlightEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final int _tmpCharStart;
            _tmpCharStart = _cursor.getInt(_cursorIndexOfCharStart);
            final int _tmpCharEnd;
            _tmpCharEnd = _cursor.getInt(_cursorIndexOfCharEnd);
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
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new HighlightEntity(_tmpId,_tmpBookId,_tmpText,_tmpColor,_tmpPageNumber,_tmpCharStart,_tmpCharEnd,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<HighlightEntity>> getHighlightsByPage(final String bookId,
      final int pageNumber) {
    final String _sql = "SELECT * FROM highlights WHERE book_id = ? AND page_number = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, pageNumber);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"highlights"}, new Callable<List<HighlightEntity>>() {
      @Override
      @NonNull
      public List<HighlightEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "color");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfCharStart = CursorUtil.getColumnIndexOrThrow(_cursor, "char_start");
          final int _cursorIndexOfCharEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "char_end");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<HighlightEntity> _result = new ArrayList<HighlightEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final HighlightEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpColor;
            _tmpColor = _cursor.getString(_cursorIndexOfColor);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final int _tmpCharStart;
            _tmpCharStart = _cursor.getInt(_cursorIndexOfCharStart);
            final int _tmpCharEnd;
            _tmpCharEnd = _cursor.getInt(_cursorIndexOfCharEnd);
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
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new HighlightEntity(_tmpId,_tmpBookId,_tmpText,_tmpColor,_tmpPageNumber,_tmpCharStart,_tmpCharEnd,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Integer> getHighlightCountByBook(final String bookId) {
    final String _sql = "SELECT COUNT(*) FROM highlights WHERE book_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"highlights"}, new Callable<Integer>() {
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
