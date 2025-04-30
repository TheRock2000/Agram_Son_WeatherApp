package com.cs377.weatherapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J4\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\f0\u000e2\u0016\u0010\u0010\u001a\u0012\u0012\b\u0012\u00060\u0011j\u0002`\u0012\u0012\u0004\u0012\u00020\f0\u000eH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/cs377/weatherapp/ui/LocationManager;", "", "context", "Landroid/content/Context;", "locationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "(Landroid/content/Context;Lcom/google/android/gms/location/FusedLocationProviderClient;)V", "hasLocationPermission", "", "getHasLocationPermission", "()Z", "getCurrentLocation", "", "onSuccess", "Lkotlin/Function1;", "Landroid/location/Location;", "onFailure", "Ljava/lang/Exception;", "Lkotlin/Exception;", "app_debug"})
public final class LocationManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.FusedLocationProviderClient locationClient = null;
    
    public LocationManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.google.android.gms.location.FusedLocationProviderClient locationClient) {
        super();
    }
    
    public final boolean getHasLocationPermission() {
        return false;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    public final void getCurrentLocation(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.location.Location, kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> onFailure) {
    }
}