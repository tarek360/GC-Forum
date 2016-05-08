package io.tarek360.gcforum.ui.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.TextView;
import io.tarek360.gcforum.App;
import io.tarek360.gcforum.R;
import io.tarek360.gcforum.R.styleable;

public class TypefaceTextView extends TextView {

  /*
   * Caches typefaces based on their file path and name, so that they don't have to be created
   * every time when they are referenced.
   */
  private static SparseArray<Typeface> mTypefaces;

  public TypefaceTextView(final Context context) {
    this(context, null);
  }

  public TypefaceTextView(final Context context, final AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TypefaceTextView(final Context context, final AttributeSet attrs, final int defStyle) {
    super(context, attrs, defStyle);
    if (mTypefaces == null) {
      mTypefaces = new SparseArray<>();
    }

    // prevent exception in Android Studio / ADT interface builder
    if (this.isInEditMode()) {
      return;
    }

    final TypedArray array = context.obtainStyledAttributes(attrs, styleable.TypefaceTextView);
    if (array != null) {
      final int typefaceEnum = array.getInt(R.styleable.TypefaceTextView_customTypeface, 0);

      if (typefaceEnum != 0) {
        Typeface typeface = null;

        if (null == (typeface = mTypefaces.get(typefaceEnum))) {
          typeface = getTypeface(typefaceEnum);
          mTypefaces.put(typefaceEnum, typeface);
        }

        setTypeface(typeface);
      }
      array.recycle();
    }
  }

  private Typeface getTypeface(int typefaceEnum) {
    switch (typefaceEnum) {
      case 1:
        return App.HELVETICANEUELT_ARABIC_75_BOLD;

      default:
        return Typeface.DEFAULT;
    }
  }
}
