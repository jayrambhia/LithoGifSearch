package com.fenchtose.lithogifsearch.components;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
import com.fenchtose.lithogifsearch.R;

@MountSpec
public class GlideImageSpec {

	public static final String TAG = GlideImageSpec.class.getSimpleName();

	@OnPrepare
	static void onPrepare(ComponentContext context, @Prop int width, @Prop int height, Output<Float> ratio) {
		ratio.set((float) width / height);
	}

	@OnMeasure
	protected static void onMeasure(ComponentContext c, ComponentLayout layout, int widthSpec, int heightSpec, Size size, @FromPrepare float ratio) {
		MeasureUtils.measureWithAspectRatio(widthSpec, heightSpec, ratio, size);
	}

	@OnCreateMountContent
	protected static ImageView onCreateMountContent(ComponentContext c) {
		ImageView view = new ImageView(c.getApplicationContext());
		view.setScaleType(ImageView.ScaleType.FIT_XY);
		view.setAdjustViewBounds(true);
		view.setBackgroundColor(Color.WHITE);
		return view;
	}

	@OnMount
	static void onMount(ComponentContext c, ImageView view, @Prop String image) {
		view.setImageResource(R.mipmap.ic_launcher_round);
		view.setScaleType(ImageView.ScaleType.CENTER);
		Glide.with(c.getBaseContext()).load(image).asGif().into(view);

	}
}
