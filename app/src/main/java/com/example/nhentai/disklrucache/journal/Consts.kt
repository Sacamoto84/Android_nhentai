package com.example.nhentai.disklrucache.journal

internal const val JOURNAL_MAGIC = "JOURNAL"
internal const val JOURNAL_VERSION: Byte = 1

internal const val JOURNAL_FILE = "journal"
internal const val JOURNAL_FILE_TEMP = "$JOURNAL_FILE.tmp"
internal const val JOURNAL_FILE_BACKUP = "$JOURNAL_FILE.bkp"