package org.taitasciore.android.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by roberto on 27/07/17.
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomScope {

}
