package com.lexiread.app.di;

import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ApiModule_ProvideGoogleBooksRetrofitFactory implements Factory<Retrofit> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<Gson> gsonProvider;

  public ApiModule_ProvideGoogleBooksRetrofitFactory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<Gson> gsonProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public Retrofit get() {
    return provideGoogleBooksRetrofit(okHttpClientProvider.get(), gsonProvider.get());
  }

  public static ApiModule_ProvideGoogleBooksRetrofitFactory create(
      Provider<OkHttpClient> okHttpClientProvider, Provider<Gson> gsonProvider) {
    return new ApiModule_ProvideGoogleBooksRetrofitFactory(okHttpClientProvider, gsonProvider);
  }

  public static Retrofit provideGoogleBooksRetrofit(OkHttpClient okHttpClient, Gson gson) {
    return Preconditions.checkNotNullFromProvides(ApiModule.INSTANCE.provideGoogleBooksRetrofit(okHttpClient, gson));
  }
}
