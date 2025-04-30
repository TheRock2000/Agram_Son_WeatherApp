package com.cs377.weatherapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a\b\u0010\u0007\u001a\u00020\u0001H\u0003\u001a\u0010\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0003H\u0003\u001a\u001e\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001a*\u0010\u000f\u001a\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0013H\u0003\u001a\b\u0010\u0014\u001a\u00020\u0001H\u0003\u001a\u001e\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001a*\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0010\u001a\u0004\u0018\u00010\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000eH\u0003\u001a\u0010\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001aH\u0003\u001a\u001c\u0010\u001e\u001a\u00020\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\b\u0002\u0010!\u001a\u00020\"H\u0003\u001a \u0010#\u001a\u00020\u00012\b\b\u0002\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a\u0010\u0010\'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020)H\u0002\u001a\u0010\u0010*\u001a\u00020\u00032\u0006\u0010(\u001a\u00020)H\u0002\u001a\f\u0010+\u001a\u00020\u0003*\u00020\u0003H\u0002\u00a8\u0006,"}, d2 = {"DetailItem", "", "label", "", "value", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "EmptyState", "ErrorState", "message", "ForecastItem", "forecast", "Lcom/cs377/weatherapp/data/ForecastItem;", "onClick", "Lkotlin/Function0;", "ForecastList", "forecastData", "", "onForecastClick", "Lkotlin/Function1;", "LoadingState", "SuggestionItem", "suggestion", "Lcom/cs377/weatherapp/data/SearchSuggestion;", "WeatherContent", "weatherData", "Lcom/cs377/weatherapp/data/WeatherResponse;", "Lcom/cs377/weatherapp/data/ForecastResponse;", "onBackClick", "WeatherDetails", "WeatherIcon", "weather", "Lcom/cs377/weatherapp/data/Weather;", "modifier", "Landroidx/compose/ui/Modifier;", "WeatherScreen", "viewModel", "Lcom/cs377/weatherapp/ui/WeatherViewModel;", "onNavigateToBookmarks", "getDateOnly", "timestamp", "", "getFormattedDate", "capitalize", "app_debug"})
public final class WeatherScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void WeatherScreen(@org.jetbrains.annotations.NotNull()
    com.cs377.weatherapp.ui.WeatherViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToBookmarks) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WeatherContent(com.cs377.weatherapp.data.WeatherResponse weatherData, com.cs377.weatherapp.data.ForecastResponse forecastData, kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WeatherDetails(com.cs377.weatherapp.data.WeatherResponse weatherData) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DetailItem(java.lang.String label, java.lang.String value, androidx.compose.ui.graphics.vector.ImageVector icon) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SuggestionItem(com.cs377.weatherapp.data.SearchSuggestion suggestion, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingState() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorState(java.lang.String message) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ForecastList(java.util.List<com.cs377.weatherapp.data.ForecastItem> forecastData, kotlin.jvm.functions.Function1<? super com.cs377.weatherapp.data.ForecastItem, kotlin.Unit> onForecastClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ForecastItem(com.cs377.weatherapp.data.ForecastItem forecast, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void WeatherIcon(com.cs377.weatherapp.data.Weather weather, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String getDateOnly(long timestamp) {
        return null;
    }
    
    private static final java.lang.String getFormattedDate(long timestamp) {
        return null;
    }
    
    private static final java.lang.String capitalize(java.lang.String $this$capitalize) {
        return null;
    }
}