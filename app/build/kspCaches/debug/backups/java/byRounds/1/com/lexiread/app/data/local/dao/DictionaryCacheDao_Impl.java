package com.lexiread.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.lexiread.app.data.local.entity.DictionaryCacheEntity;
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
public final class DictionaryCacheDao_Impl implements DictionaryCacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DictionaryCacheEntity> __insertionAdapterOfDictionaryCacheEntity;

  private final SharedSQLiteStatement __preparedStmtOfPruneOldEntries;

  private final SharedSQLiteStatement __preparedStmtOfClearCache;

  public DictionaryCacheDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDictionaryCacheEntity = new EntityInsertionAdapter<DictionaryCacheEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `dictionary_cache` (`word`,`definition`,`part_of_speech`,`phonetic`,`timestamp`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DictionaryCacheEntity entity) {
        statement.bindString(1, entity.getWord());
        statement.bindString(2, entity.getDefinition());
        if (entity.getPartOfSpeech() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPartOfSpeech());
        }
        if (entity.getPhonetic() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPhonetic());
        }
        statement.bindLong(5, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfPruneOldEntries = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM dictionary_cache WHERE word NOT IN (SELECT word FROM dictionary_cache ORDER BY timestamp DESC LIMIT 100)";
        return _query;
      }
    };
    this.__preparedStmtOfClearCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM dictionary_cache";
        return _query;
      }
    };
  }

  @Override
  public Object insertCache(final DictionaryCacheEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDictionaryCacheEntity.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object pruneOldEntries(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfPruneOldEntries.acquire();
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
          __preparedStmtOfPruneOldEntries.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearCache(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearCache.acquire();
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
          __preparedStmtOfClearCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getCachedDefinition(final String word,
      final Continuation<? super DictionaryCacheEntity> $completion) {
    final String _sql = "SELECT * FROM dictionary_cache WHERE word = ? COLLATE NOCASE LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, word);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DictionaryCacheEntity>() {
      @Override
      @Nullable
      public DictionaryCacheEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfDefinition = CursorUtil.getColumnIndexOrThrow(_cursor, "definition");
          final int _cursorIndexOfPartOfSpeech = CursorUtil.getColumnIndexOrThrow(_cursor, "part_of_speech");
          final int _cursorIndexOfPhonetic = CursorUtil.getColumnIndexOrThrow(_cursor, "phonetic");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final DictionaryCacheEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpWord;
            _tmpWord = _cursor.getString(_cursorIndexOfWord);
            final String _tmpDefinition;
            _tmpDefinition = _cursor.getString(_cursorIndexOfDefinition);
            final String _tmpPartOfSpeech;
            if (_cursor.isNull(_cursorIndexOfPartOfSpeech)) {
              _tmpPartOfSpeech = null;
            } else {
              _tmpPartOfSpeech = _cursor.getString(_cursorIndexOfPartOfSpeech);
            }
            final String _tmpPhonetic;
            if (_cursor.isNull(_cursorIndexOfPhonetic)) {
              _tmpPhonetic = null;
            } else {
              _tmpPhonetic = _cursor.getString(_cursorIndexOfPhonetic);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _result = new DictionaryCacheEntity(_tmpWord,_tmpDefinition,_tmpPartOfSpeech,_tmpPhonetic,_tmpTimestamp);
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
  public Flow<List<DictionaryCacheEntity>> getRecentLookups(final int limit) {
    final String _sql = "SELECT * FROM dictionary_cache ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"dictionary_cache"}, new Callable<List<DictionaryCacheEntity>>() {
      @Override
      @NonNull
      public List<DictionaryCacheEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfWord = CursorUtil.getColumnIndexOrThrow(_cursor, "word");
          final int _cursorIndexOfDefinition = CursorUtil.getColumnIndexOrThrow(_cursor, "definition");
          final int _cursorIndexOfPartOfSpeech = CursorUtil.getColumnIndexOrThrow(_cursor, "part_of_speech");
          final int _cursorIndexOfPhonetic = CursorUtil.getColumnIndexOrThrow(_cursor, "phonetic");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<DictionaryCacheEntity> _result = new ArrayList<DictionaryCacheEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DictionaryCacheEntity _item;
            final String _tmpWord;
            _tmpWord = _cursor.getString(_cursorIndexOfWord);
            final String _tmpDefinition;
            _tmpDefinition = _cursor.getString(_cursorIndexOfDefinition);
            final String _tmpPartOfSpeech;
            if (_cursor.isNull(_cursorIndexOfPartOfSpeech)) {
              _tmpPartOfSpeech = null;
            } else {
              _tmpPartOfSpeech = _cursor.getString(_cursorIndexOfPartOfSpeech);
            }
            final String _tmpPhonetic;
            if (_cursor.isNull(_cursorIndexOfPhonetic)) {
              _tmpPhonetic = null;
            } else {
              _tmpPhonetic = _cursor.getString(_cursorIndexOfPhonetic);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new DictionaryCacheEntity(_tmpWord,_tmpDefinition,_tmpPartOfSpeech,_tmpPhonetic,_tmpTimestamp);
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
  public Object getCacheCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM dictionary_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
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
