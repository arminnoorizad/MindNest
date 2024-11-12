package com.example.purpleNote.model.di;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = NoteApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface NoteApplication_GeneratedInjector {
  void injectNoteApplication(NoteApplication noteApplication);
}
