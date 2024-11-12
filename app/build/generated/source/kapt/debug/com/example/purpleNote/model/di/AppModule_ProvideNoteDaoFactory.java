// Generated by Dagger (https://dagger.dev).
package com.example.purpleNote.model.di;

import com.example.purpleNote.model.database.DBHandler;
import com.example.purpleNote.model.database.NoteDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class AppModule_ProvideNoteDaoFactory implements Factory<NoteDao> {
  private final Provider<DBHandler> dbHandlerProvider;

  public AppModule_ProvideNoteDaoFactory(Provider<DBHandler> dbHandlerProvider) {
    this.dbHandlerProvider = dbHandlerProvider;
  }

  @Override
  public NoteDao get() {
    return provideNoteDao(dbHandlerProvider.get());
  }

  public static AppModule_ProvideNoteDaoFactory create(Provider<DBHandler> dbHandlerProvider) {
    return new AppModule_ProvideNoteDaoFactory(dbHandlerProvider);
  }

  public static NoteDao provideNoteDao(DBHandler dbHandler) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNoteDao(dbHandler));
  }
}
