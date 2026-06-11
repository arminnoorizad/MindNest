# ۱. رهگیری کرش‌ها (بسیار حیاتی)
-keepattributes SourceFile,LineNumberTable,*Annotation*,Signature,InnerClasses,EnclosingMethod
-renamesourcefileattribute SourceFile

# ۲. کاتلین و کورتینز
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# ۳. روم و انتیتی‌ها (با حفظ فیلدها برای جلوگیری از کرش دیتابیس)
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-dontwarn androidx.room.**


-keep class ir.armin.mindnest.data.local.database.entity.** { *; }





-keep class net.zetetic.** { *; }
-dontwarn net.zetetic.**

-dontwarn com.google.errorprone.annotations.**
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectStreamField);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-dontwarn coil.**