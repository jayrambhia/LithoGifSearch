package com.fenchtose.lithogifsearch.components;

import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.annotations.FromPrepare;
import com.facebook.litho.annotations.MountSpec;
import com.facebook.litho.annotations.OnCreateMountContent;
import com.facebook.litho.annotations.OnMeasure;
import com.facebook.litho.annotations.OnMount;
import com.facebook.litho.annotations.OnPrepare;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.utils.MeasureUtils;
import com.fenchtose.lithogifsearch.models.GifItem;

@MountSpec
public class GifItemViewSpec {

	public static final String TAG = GifItemViewSpec.class.getSimpleName();

	@OnPrepare
	static void onPrepare(ComponentContext context, @Prop GifItem gif, Output<Float> ratio) {
		ratio.set((float) gif.getWidth() / gif.getHeight());
	}

	@OnMeasure
	static void onMeasure(ComponentContext c, ComponentLayout layout, int widthSpec, int heightSpec, Size size, @FromPrepare float ratio) {
		MeasureUtils.measureWithAspectRatio(widthSpec, heightSpec, ratio, size);
	}

	@OnCreateMountContent
	static ImageView onCreateMountContent(ComponentContext c) {
		ImageView view = new ImageView(c.getApplicationContext());
		view.setAdjustViewBounds(true);
		view.setBackgroundColor(Color.WHITE);
		view.setScaleType(ImageView.ScaleType.CENTER);
		return view;
	}

	@OnMount
	static void onMount(ComponentContext c, ImageView view, @Prop RequestManager glide, @Prop GifItem gif) {
		glide.load(gif.getImage()).asGif().into(view);
	}
}
