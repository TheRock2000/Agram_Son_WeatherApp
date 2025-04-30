package com.cs377.weatherapp.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/cs377/weatherapp/data/ApiClient;", "", "()V", "GEO_BASE_URL", "", "WEATHER_BASE_URL", "okHttpClient", "Lokhttp3/OkHttpClient;", "createGeoApiService", "Lcom/cs377/weatherapp/data/GeoApiService;", "createWeatherApiService", "Lcom/cs377/weatherapp/data/WeatherApiService;", "app_debug"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String GEO_BASE_URL = "https://api.openweathermap.org/";
    @org.jetbrains.annotations.NotNull()
    private static final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cs377.weatherapp.data.ApiClient INSTANCE = null;
    
    private ApiClient() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cs377.weatherapp.data.WeatherApiService createWeatherApiService() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cs377.weatherapp.data.GeoApiService createGeoApiService() {
        return null;
    }
}