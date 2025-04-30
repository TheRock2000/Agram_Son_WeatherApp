package com.cs377.weatherapp.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aL\u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u000e"}, d2 = {"BookmarkItem", "", "location", "Lcom/cs377/weatherapp/data/BookmarkedLocation;", "onClick", "Lkotlin/Function0;", "onRemove", "BookmarkSidebar", "bookmarkedLocations", "", "onLocationClick", "Lkotlin/Function1;", "onRemoveBookmark", "onClose", "app_debug"})
public final class BookmarkSidebarKt {
    
    @androidx.compose.runtime.Composable()
    public static final void BookmarkSidebar(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cs377.weatherapp.data.BookmarkedLocation> bookmarkedLocations, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cs377.weatherapp.data.BookmarkedLocation, kotlin.Unit> onLocationClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.cs377.weatherapp.data.BookmarkedLocation, kotlin.Unit> onRemoveBookmark, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClose) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void BookmarkItem(@org.jetbrains.annotations.NotNull()
    com.cs377.weatherapp.data.BookmarkedLocation location, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
}