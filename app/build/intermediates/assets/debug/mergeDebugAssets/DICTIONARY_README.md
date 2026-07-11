# Offline Dictionary Database

Place your `dictionary.db` SQLite file in this directory.

## Required Schema

```sql
CREATE TABLE dictionary (
    word TEXT PRIMARY KEY,
    definition TEXT
);
```

## Recommended Sources

1. **WordNet-based dictionary** (free, ~150k words):
   - Download from: https://github.com/lexiread/dictionary-db/releases
   - Or generate from WordNet: https://wordnet.princeton.edu/

2. **quick-dictionary project**:
   - https://github.com/nichuanfang/quick-dictionary
   - Extract the `dict.db` and rename to `dictionary.db`

3. **English Wiktionary dump**:
   - https://dumps.wikimedia.org/enwiktionary/

## How to Install

1. Download or create a `dictionary.db` file with the schema above
2. Place it in `app/src/main/assets/dictionary.db`
3. The app will automatically copy it to internal storage on first launch

## Definition Format

For best results, format definitions as:

```
(noun) The quality or state of being ...
(verb) To perform an action ...
```

The app will parse `(noun)`, `(verb)`, etc. as part-of-speech labels.
