package com.example.databasetest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.databasetest.data.WordConstant.DEFORM_NULL

@Entity(
    tableName = "word",
//    foreignKeys = [
//        ForeignKey(
//            entity = DeformVerb::class,
//            parentColumns = ["id"],
//            childColumns = ["deformVerb"]
//        ),
//        ForeignKey(
//            entity = DeformAdj::class,
//            parentColumns = ["id"],
//            childColumns = ["deformAdj"]
//        )
//    ]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "kanji") var kanji: String,
    var gana: String,
    var meaning: String,
    var notice: String,
    var type: Int = WordConstant.NONE,
    var deformVerb: Int = -1,
    var deformAdj: Int = -1,
    var sentenceKey: Int = -1,
)


@Entity("deform_verb")
data class DeformVerb(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var deform1: String = DEFORM_NULL,
    var deform2: String = DEFORM_NULL,
    var deform3: String = DEFORM_NULL,
    var deform4: String = DEFORM_NULL,
    var deform5: String = DEFORM_NULL,
    var deform6: String = DEFORM_NULL,
    var deform7: String = DEFORM_NULL,
    var deform8: String = DEFORM_NULL,
    var deform9: String = DEFORM_NULL,
    var deform10: String = DEFORM_NULL,
    var deform11: String = DEFORM_NULL,
    var deform12: String = DEFORM_NULL
)

@Entity("deform_adj")
data class DeformAdj(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var deform1: String = DEFORM_NULL,
    var deform2: String = DEFORM_NULL,
    var deform3: String = DEFORM_NULL,
    var deform4: String = DEFORM_NULL
)

@Entity("sentence")
data class Sentence(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var key: Int,
    var content: String
) {
    override fun toString(): String {
        return "Sentence(id=$id, key=$key, content='$content')"
    }
}