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
import com.lexiread.app.data.local.entity.NoteEntity;
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
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteEntity> __insertionAdapterOfNoteEntity;

  private final EntityDeletionOrUpdateAdapter<NoteEntity> __deletionAdapterOfNoteEntity;

  private final EntityDeletionOrUpdateAdapter<NoteEntity> __updateAdapterOfNoteEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteNotesByBook;

  public NoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteEntity = new EntityInsertionAdapter<NoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notes` (`id`,`book_id`,`highlight_id`,`content`,`page_number`,`chapter`,`firebase_id`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NoteEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBookId());
        if (entity.getHighlightId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getHighlightId());
        }
        statement.bindString(4, entity.getContent());
        statement.bindLong(5, entity.getPageNumber());
        if (entity.getChapter() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getChapter());
        }
        if (entity.getFirebaseId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFirebaseId());
        }
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfNoteEntity = new EntityDeletionOrUpdateAdapter<NoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `notes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NoteEntity entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfNoteEntity = new EntityDeletionOrUpdateAdapter<NoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `notes` SET `id` = ?,`book_id` = ?,`highlight_id` = ?,`content` = ?,`page_number` = ?,`chapter` = ?,`firebase_id` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NoteEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getBookId());
        if (entity.getHighlightId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getHighlightId());
        }
        statement.bindString(4, entity.getContent());
        statement.bindLong(5, entity.getPageNumber());
        if (entity.getChapter() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getChapter());
        }
        if (entity.getFirebaseId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFirebaseId());
        }
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getUpdatedAt());
        statement.bindString(10, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteNotesByBook = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notes WHERE book_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertNote(final NoteEntity note, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNoteEntity.insert(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteNote(final NoteEntity note, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNoteEntity.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateNote(final NoteEntity note, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNoteEntity.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteNotesByBook(final String bookId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteNotesByBook.acquire();
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
          __preparedStmtOfDeleteNotesByBook.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NoteEntity>> getNotesByBook(final String bookId) {
    final String _sql = "SELECT * FROM notes WHERE book_id = ? ORDER BY page_number ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, bookId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfHighlightId = CursorUtil.getColumnIndexOrThrow(_cursor, "highlight_id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpHighlightId;
            if (_cursor.isNull(_cursorIndexOfHighlightId)) {
              _tmpHighlightId = null;
            } else {
              _tmpHighlightId = _cursor.getString(_cursorIndexOfHighlightId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
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
            _item = new NoteEntity(_tmpId,_tmpBookId,_tmpHighlightId,_tmpContent,_tmpPageNumber,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<NoteEntity>> getAllNotes() {
    final String _sql = "SELECT * FROM notes ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfHighlightId = CursorUtil.getColumnIndexOrThrow(_cursor, "highlight_id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpHighlightId;
            if (_cursor.isNull(_cursorIndexOfHighlightId)) {
              _tmpHighlightId = null;
            } else {
              _tmpHighlightId = _cursor.getString(_cursorIndexOfHighlightId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
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
            _item = new NoteEntity(_tmpId,_tmpBookId,_tmpHighlightId,_tmpContent,_tmpPageNumber,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getNoteById(final String noteId,
      final Continuation<? super NoteEntity> $completion) {
    final String _sql = "SELECT * FROM notes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, noteId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NoteEntity>() {
      @Override
      @Nullable
      public NoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfHighlightId = CursorUtil.getColumnIndexOrThrow(_cursor, "highlight_id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final NoteEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpHighlightId;
            if (_cursor.isNull(_cursorIndexOfHighlightId)) {
              _tmpHighlightId = null;
            } else {
              _tmpHighlightId = _cursor.getString(_cursorIndexOfHighlightId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
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
            _result = new NoteEntity(_tmpId,_tmpBookId,_tmpHighlightId,_tmpContent,_tmpPageNumber,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getNoteByHighlight(final String highlightId,
      final Continuation<? super NoteEntity> $completion) {
    final String _sql = "SELECT * FROM notes WHERE highlight_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, highlightId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<NoteEntity>() {
      @Override
      @Nullable
      public NoteEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfHighlightId = CursorUtil.getColumnIndexOrThrow(_cursor, "highlight_id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final NoteEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpHighlightId;
            if (_cursor.isNull(_cursorIndexOfHighlightId)) {
              _tmpHighlightId = null;
            } else {
              _tmpHighlightId = _cursor.getString(_cursorIndexOfHighlightId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
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
            _result = new NoteEntity(_tmpId,_tmpBookId,_tmpHighlightId,_tmpContent,_tmpPageNumber,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<NoteEntity>> searchNotes(final String query) {
    final String _sql = "SELECT * FROM notes WHERE content LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notes"}, new Callable<List<NoteEntity>>() {
      @Override
      @NonNull
      public List<NoteEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBookId = CursorUtil.getColumnIndexOrThrow(_cursor, "book_id");
          final int _cursorIndexOfHighlightId = CursorUtil.getColumnIndexOrThrow(_cursor, "highlight_id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "page_number");
          final int _cursorIndexOfChapter = CursorUtil.getColumnIndexOrThrow(_cursor, "chapter");
          final int _cursorIndexOfFirebaseId = CursorUtil.getColumnIndexOrThrow(_cursor, "firebase_id");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<NoteEntity> _result = new ArrayList<NoteEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NoteEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpBookId;
            _tmpBookId = _cursor.getString(_cursorIndexOfBookId);
            final String _tmpHighlightId;
            if (_cursor.isNull(_cursorIndexOfHighlightId)) {
              _tmpHighlightId = null;
            } else {
              _tmpHighlightId = _cursor.getString(_cursorIndexOfHighlightId);
            }
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
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
            _item = new NoteEntity(_tmpId,_tmpBookId,_tmpHighlightId,_tmpContent,_tmpPageNumber,_tmpChapter,_tmpFirebaseId,_tmpCreatedAt,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
