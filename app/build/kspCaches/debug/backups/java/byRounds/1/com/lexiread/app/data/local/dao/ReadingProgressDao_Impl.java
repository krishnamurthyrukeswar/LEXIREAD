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
import com.lexiread.app.data.local.entity.ReadingProgressEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class ReadingProgressDao_Impl implements ReadingProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReadingProgressEntity> __insertionAdapterOfReadingProgressEntity;

  private final EntityDeletionOrUpdateAdapter<ReadingProgressEntity> __updateAdapterOfReadingProgressEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteProgressByBook;

  public ReadingProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReadingProgressEntity = new EntityInsertionAdapter<ReadingProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reading_progress` (`id`,`book_id`,`current_page`,`total_pages`,`percentage`,`last_read_at`,`total_reading_time_ms`,`firebase_id`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReadingProgressEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBookId());
        statement.bindLong(3, entity.getCurrentPage());
        statement.bindLong(4, entity.getTotalPages());
        statement.bindDouble(5, entity.getPercentage());
        statement.bindLong(6, entity.getLastReadAt());
        statement.bindLong(7, entity.getTotalReadingTimeMs());
        if (entity.getFirebaseId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFirebaseId());
        }
        statement.bindLong(9, entity.getUpdatedAt());
      }
    };
    this.__updateAdapterOfReadingProgressEntity = new EntityDeletionOrUpdateAdapter<ReadingProgressEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reading_progress` SET `id` = ?,`book_id` = ?,`current_page` = ?,`total_pages` = ?,`percentage` = ?,`last_read_at` = ?,`total_reading_time_ms` = ?,`firebase_id` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReadingProgressEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBookId());
        statement.bindLong(3, entity.getCurrentPage());
        statement.bindLong(4, entity.getTotalPages());
        statement.bindDouble(5, entity.getPercentage());
        statement.bindLong(6, entity.getLastReadAt());
        statement.bindLong(7, entity.getTotalReadingTimeMs());
        if (entity.getFirebaseId() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getFirebaseId());
        }
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindString(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteProgressByBook = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reading_progress WHERE book_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertProgress(final ReadingProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReadingProgressEntity.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateProgress(final ReadingProgressEntity progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReadingProgressEntity.handle(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProgressByBook(final String bookId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteProgressByBook.acquire();
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
          __preparedStmtOfDeleteProgressByBook.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getProgressByBook(final String bookId,
      final Continuation<? super ReadingProgressEntity> $completion) {
    final String _sql = "SELECT * FROM reading_progress WHERE book_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ReadingProgressEntity>() {
      @Override
      @Nullable
      public ReadingProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfCurrentPage = CursorUtil.getColumnIndexOrThrow(_cursor, "current_page");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "percentage");
          final int _cursorIndexOfLastReadAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_read_at");
          final int _cursorIndexOfTotalReadingTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "total_reading_time_ms");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final ReadingProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpCurrentPage;
            _tmpCurrentPage = _cursor.getInt(_cursorIndexOfCurrentPage);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final float _tmpPercentage;
            _tmpPercentage = _cursor.getFloat(_cursorIndexOfPercentage);
            final long _tmpLastReadAt;
            _tmpLastReadAt = _cursor.getLong(_cursorIndexOfLastReadAt);
            final long _tmpTotalReadingTimeMs;
            _tmpTotalReadingTimeMs = _cursor.getLong(_cursorIndexOfTotalReadingTimeMs);
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ReadingProgressEntity(_tmpId,_tmpBookId,_tmpCurrentPage,_tmpTotalPages,_tmpPercentage,_tmpLastReadAt,_tmpTotalReadingTimeMs,_tmpFirebaseId,_tmpUpdatedAt);
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
  public Flow<ReadingProgressEntity> getProgressByBookFlow(final String bookId) {
    final String _sql = "SELECT * FROM reading_progress WHERE book_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reading_progress"}, new Callable<ReadingProgressEntity>() {
      @Override
      @Nullable
      public ReadingProgressEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfCurrentPage = CursorUtil.getColumnIndexOrThrow(_cursor, "current_page");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "percentage");
          final int _cursorIndexOfLastReadAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_read_at");
          final int _cursorIndexOfTotalReadingTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "total_reading_time_ms");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final ReadingProgressEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpCurrentPage;
            _tmpCurrentPage = _cursor.getInt(_cursorIndexOfCurrentPage);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final float _tmpPercentage;
            _tmpPercentage = _cursor.getFloat(_cursorIndexOfPercentage);
            final long _tmpLastReadAt;
            _tmpLastReadAt = _cursor.getLong(_cursorIndexOfLastReadAt);
            final long _tmpTotalReadingTimeMs;
            _tmpTotalReadingTimeMs = _cursor.getLong(_cursorIndexOfTotalReadingTimeMs);
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new ReadingProgressEntity(_tmpId,_tmpBookId,_tmpCurrentPage,_tmpTotalPages,_tmpPercentage,_tmpLastReadAt,_tmpTotalReadingTimeMs,_tmpFirebaseId,_tmpUpdatedAt);
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
  public Flow<List<ReadingProgressEntity>> getAllProgress() {
    final String _sql = "SELECT * FROM reading_progress ORDER BY last_read_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reading_progress"}, new Callable<List<ReadingProgressEntity>>() {
      @Override
      @NonNull
      public List<ReadingProgressEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfCurrentPage = CursorUtil.getColumnIndexOrThrow(_cursor, "current_page");
          final int _cursorIndexOfTotalPages = CursorUtil.getColumnIndexOrThrow(_cursor, "total_pages");
          final int _cursorIndexOfPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "percentage");
          final int _cursorIndexOfLastReadAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_read_at");
          final int _cursorIndexOfTotalReadingTimeMs = CursorUtil.getColumnIndexOrThrow(_cursor, "total_reading_time_ms");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<ReadingProgressEntity> _result = new ArrayList<ReadingProgressEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReadingProgressEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final int _tmpCurrentPage;
            _tmpCurrentPage = _cursor.getInt(_cursorIndexOfCurrentPage);
            final int _tmpTotalPages;
            _tmpTotalPages = _cursor.getInt(_cursorIndexOfTotalPages);
            final float _tmpPercentage;
            _tmpPercentage = _cursor.getFloat(_cursorIndexOfPercentage);
            final long _tmpLastReadAt;
            _tmpLastReadAt = _cursor.getLong(_cursorIndexOfLastReadAt);
            final long _tmpTotalReadingTimeMs;
            _tmpTotalReadingTimeMs = _cursor.getLong(_cursorIndexOfTotalReadingTimeMs);
            final String _tmpFirebaseId;
            if (_cursor.isNull(_cursorIndexOfFirebaseId)) {
              _tmpFirebaseId = null;
            } else {
              _tmpFirebaseId = _cursor.getString(_cursorIndexOfFirebaseId);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new ReadingProgressEntity(_tmpId,_tmpBookId,_tmpCurrentPage,_tmpTotalPages,_tmpPercentage,_tmpLastReadAt,_tmpTotalReadingTimeMs,_tmpFirebaseId,_tmpUpdatedAt);
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
  public Flow<Long> getTotalReadingTime() {
    final String _sql = "SELECT SUM(total_reading_time_ms) FROM reading_progress";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reading_progress"}, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
            }
            _result = _tmp;
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
