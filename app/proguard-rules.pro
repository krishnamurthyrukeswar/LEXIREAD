# Add project specific ProGuard rules here.

# ── Retrofit ──
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# ── Gson ──
-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class com.lexiread.app.data.remote.dto.** { *; }

# ── Room ──
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *

# ── Glide ──
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}

# ── Firebase ──
-keep class com.google.firebase.** { *; }

# ── Coroutines ──
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
