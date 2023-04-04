package com.technologyedge.docutracker.Models


import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*


data class Document(
    var id:String? ="",
    val title: String? = "",
    val reference:String?="",
    val description: String? = "",
    val instruction: String? = "",
    val type: String? ="",
    @ServerTimestamp
    val date_received: Date? = null,
    @ServerTimestamp
    val date_due: Date? = null,
    val refer: Refer? = null,
    val _return: Return? = null
        ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readSerializable() as Date?,
        parcel.readSerializable() as Date?,
        parcel.readSerializable() as Refer,
        parcel.readSerializable() as Return
    )


    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(id)
        p0?.writeString(title)
        p0?.writeString(reference)
        p0?.writeString(description)
        p0?.writeString(instruction)
        p0?.writeString(type)
        p0?.writeSerializable(date_received)
        p0?.writeSerializable(date_due)
        p0?.writeSerializable(refer)
        p0?.writeSerializable(_return)
    }

    companion object CREATOR : Parcelable.Creator<Document> {
        override fun createFromParcel(parcel: Parcel): Document {
            return Document(parcel)
        }

        override fun newArray(size: Int): Array<Document?> {
            return arrayOfNulls(size)
        }
    }
}

data class Refer(
    val refer_to:String?,
    @ServerTimestamp
    val date_refered: Date?,
    @ServerTimestamp
    val date_due: Date?
):Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable() as Date?,
        parcel.readSerializable() as Date?
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(refer_to)
        p0?.writeSerializable(date_refered)
        p0?.writeSerializable(date_due)
    }

    companion object CREATOR : Parcelable.Creator<Refer> {
        override fun createFromParcel(parcel: Parcel): Refer {
            return Refer(parcel)
        }

        override fun newArray(size: Int): Array<Refer?> {
            return arrayOfNulls(size)
        }
    }
}

data class Return(
    @ServerTimestamp
    val date_returned: Date?,
    val returned_by: String?
): Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as Date?,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
       parcel.writeSerializable(date_returned)
        parcel.writeString(returned_by)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Return> {
        override fun createFromParcel(parcel: Parcel): Return {
            return Return(parcel)
        }

        override fun newArray(size: Int): Array<Return?> {
            return arrayOfNulls(size)
        }
    }
}