# Keep important classes
-keep class org.slf4j.** { *; }
-keep class org.koin.** { *; }
-keep class com.google.firebase.** { *; }
-keep class org.krediya.project.domain.model.** { *; }

# Specific rule for StaticLoggerBinder
-dontwarn org.slf4j.impl.StaticLoggerBinder