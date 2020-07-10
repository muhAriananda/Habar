package id.aria.habar.utils.external

import androidx.room.TypeConverter
import id.aria.habar.domain.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) =
        source.name

    @TypeConverter
    fun toSource(name: String): Source =
        Source(name, name)
}