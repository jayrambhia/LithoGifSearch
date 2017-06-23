package com.fenchtose.lithogifsearch.dagger;

import android.content.Context;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;

import dagger.Component;

@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
	Context context();
	ComponentContext componentContext();
	RequestManager glide();
}
