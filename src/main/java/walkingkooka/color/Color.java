/*
 * Copyright 2018 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */

package walkingkooka.color;

import walkingkooka.Cast;
import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.build.tostring.ToStringBuilderOption;
import walkingkooka.build.tostring.UsesToStringBuilder;
import walkingkooka.test.HashCodeEqualsDefined;

import java.io.Serializable;
import java.util.Objects;

/**
 * Holds an immutable {@link Color}.
 */
abstract public class Color implements UsesToStringBuilder, HashCodeEqualsDefined, Serializable {

    /**
     * A constant holding black
     */
    public final static Color BLACK = Color.fromRgb(0);

    /**
     * A constant holding white
     */
    public final static Color WHITE = Color.fromRgb(0xFFFFFF);

    /**
     * Creates a {@link Color} from the given RGB value.
     */
    public static Color fromRgb(final int rgb) {
        return OpaqueColor.with( //
                ColorComponent.red(Color.shiftRight(rgb, Color.RED_SHIFT)), //
                ColorComponent.green(Color.shiftRight(rgb, Color.GREEN_SHIFT)), //
                ColorComponent.blue(Color.shiftRight(rgb, Color.BLUE_SHIFT)), //
                rgb & Color.WITHOUT_ALPHA);
    }

    final static int WITHOUT_ALPHA = 0x00FFFFFF;

    /**
     * Creates a {@link Color} from the given ARGB value.
     */
    public static Color fromArgb(final int argb) {
        return (argb & Color.ALPHA_MASK) == Color.ALPHA_MASK ? Color.fromRgb(argb)
                : AlphaColor.createAlphaColorFromArgb(argb);
    }

    /**
     * Boring helper that shifts and returns the lowest 8 bits.
     */
    static byte shiftRight(final int value, final int shift) {
        return (byte) (value >> shift);
    }

    /**
     * Used to extract 8 alpha bits from a 32 bit ARGB value.
     */
    final static int ALPHA_SHIFT = 24;

    /**
     * A mask that may be used to extract only the alpha component of a ARGB value.
     */
    final static int ALPHA_MASK = 0xFF << Color.ALPHA_SHIFT;

    /**
     * Used to extract 8 red bits from a 32 bit ARGB value.
     */

    final static int RED_SHIFT = 16;

    /**
     * Used to extract 8 green bits from a 32 bit ARGB value.
     */
    final static int GREEN_SHIFT = 8;

    /**
     * Used to extract 8 blue bits from a 32 bit ARGB value.
     */
    final static int BLUE_SHIFT = 0;

    /**
     * Creates a new {@link Color} with the provided components.
     */
    public static Color with(final RedColorComponent red,
                             final GreenColorComponent green,
                             final BlueColorComponent blue) {
        return OpaqueColor.createOpaqueColor(red, green, blue);
    }

    /**
     * Factory used by {@link Hsl#toColor()} and {@link Hsv#toColor()}
     */
    static Color with(final float red, final float green, final float blue) {
        return Color.with( //
                RedColorComponent.with(ColorComponent.toByte(red)), //
                GreenColorComponent.with(ColorComponent.toByte(green)), //
                BlueColorComponent.with(ColorComponent.toByte(blue))//
        );
    }

    /**
     * Package private constructor to limit sub classing.
     */
    Color(final RedColorComponent red, final GreenColorComponent green, final BlueColorComponent blue) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Tests if this color has an alpha component.
     */
    abstract public boolean hasAlpha();

    /**
     * Would be setter that returns a {@link Color} holding the new component. If the component is not new this will be returned.
     */
    public Color set(final ColorComponent component) {
        Objects.requireNonNull(component, "component");

        return component.setComponent(this);
    }

    /**
     * Factory that creates a new {@link Color} with the new {@link RedColorComponent}.
     */
    Color setRed(final RedColorComponent red) {
        return this.red.equals(red) ?
               this :
               this.replace(red, this.green, this.blue);
    }

    /**
     * Factory that creates a new {@link Color} with the new {@link GreenColorComponent}.
     */
    Color setGreen(final GreenColorComponent green) {
        return this.green.equals(green) ?
               this :
               this.replace(this.red, green, this.blue);
    }

    /**
     * Factory that creates a new {@link Color} with the new {@link BlueColorComponent}.
     */
    Color setBlue(final BlueColorComponent blue) {
        return this.blue.equals(blue) ?
               this :
               this.replace(this.red, this.green, blue);
    }

    /**
     * Factory that creates a new {@link Color} with the new {@link AlphaColorComponent}.
     */
    Color setAlpha(final AlphaColorComponent alpha) {
        return this.alpha().equals(alpha) ?
               this :
               AlphaColor.with(this.red, this.green, this.blue, alpha);
    }

    /**
     * Factory called by the various setters that creates a new {@link Color} with the given components.
     */
    abstract Color replace(final RedColorComponent red, final GreenColorComponent green, final BlueColorComponent blue);

    /**
     * Mixes the given {@link ColorComponent} by the provided amount and returns a {@link Color} with that amount.
     */
    public Color mix(final ColorComponent component, final float amount) {
        Objects.requireNonNull(component, "component");

        if ((amount < 0f) || (amount > 1.0f)) {
            throw new IllegalArgumentException("amount must be between 0.0 and 1.0 but was " + amount);
        }

        return amount <= Color.SMALL_AMOUNT ? //
                this : // amount of new component is too small ignore
                amount >= Color.LARGE_AMOUNT ? // amount results in replace.
                        component.setComponent(this) : //
                        component.mix(this, amount); // mix
    }

    /**
     * Any attempt to mix an amount less than this will return the original {@link Color}.
     */
    private final static float SMALL_AMOUNT = 1.0f / 512f;

    /**
     * Any attempt to mix an amount greater than this will return the new {@link Color} skipping the attempt to mix.
     */
    private final static float LARGE_AMOUNT = 1.0f - Color.SMALL_AMOUNT;

    // getters

    /**
     * Getter that returns only the {@link RedColorComponent}
     */
    public RedColorComponent red() {
        return this.red;
    }

    final RedColorComponent red;

    /**
     * Getter that returns only the {@link GreenColorComponent}
     */
    public GreenColorComponent green() {
        return this.green;
    }

    final GreenColorComponent green;

    /**
     * Getter that returns only the {@link BlueColorComponent}
     */
    public BlueColorComponent blue() {
        return this.blue;
    }

    final BlueColorComponent blue;

    /**
     * Always returns an opaque alpha.
     */
    abstract public AlphaColorComponent alpha();

    /**
     * Returns an integer with the RGB value.
     */
    abstract public int rgb();

    /**
     * Returns an integer holding the ARGB value.
     */
    abstract public int argb();

    /**
     * Returns either RGB or ARGB value.
     */
    abstract public int value();

    /**
     * Factory that creates a {@link java.awt.Color} holding the same color value.
     */
    abstract public java.awt.Color toAwtColor();

    /**
     * Returns a {@link Hsl} which is equivalent to this {@link Color} form, ignoring any {@link AlphaColorComponent}.<br>
     *
     * <pre>
     * Converts an RGB color value to HSL. Conversion formula
     * adapted from http://en.wikipedia.org/wiki/HSL_color_space.
     * Assumes r, g, and b are contained in the set [0, 255] and
     * returns h, s, and l in the set [0, 1].
     *
     * &#64;param   Number  r       The red color value
     * &#64;param   Number  g       The green color value
     * &#64;param   Number  b       The blue color value
     * &#64;return  Array           The HSL representation
     * function rgbToHsl(r, g, b){
     *     r /= 255, g /= 255, b /= 255;
     *     var max = Math.max(r, g, b), min = Math.min(r, g, b);
     *     var h, s, l = (max + min) / 2;
     *
     *     if(max == min){
     *         h = s = 0; // achromatic
     *     }else{
     *         var d = max - min;
     *         s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
     *         switch(max){
     *             case r: h = (g - b) / d + (g < b ? 6 : 0); break;
     *             case g: h = (b - r) / d + 2; break;
     *             case b: h = (r - g) / d + 4; break;
     *         }
     *         h /= 6;
     *     }
     *
     *     return [h, s, l];
     * }
     * <a>http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c</a>
     * </pre>
     */
    final public Hsl toHsl() {
        final float red = this.red.floatValue;
        final float green = this.green.floatValue;
        final float blue = this.blue.floatValue;

        final float max = Color.max(red, green, blue);
        final float min = Color.min(red, green, blue);
        final float sumMaxMin = max + min;

        float hue = 0;
        float saturation = 0;
        final float lightness = sumMaxMin / 2;

        final float diff = max - min;
        if (0 != diff) {
            saturation = Math.min(1.0f, diff / (lightness > 0.5f ? (2f - sumMaxMin) : sumMaxMin));

            if (max == red) {
                hue = (green - (blue / diff)) + (green < blue ? 6 : 0);
            } else {
                if (max == green) {
                    hue = ((blue - red) / diff) + 2;
                } else {
                    if (max == blue) {
                        hue = ((red - green) / diff) + 4;
                    }
                }
            }

            hue /= 6;
        }

        return Hsl.with(//
                HslComponent.hue(hue * HueHslComponent.MAX), //
                HslComponent.saturation(saturation), //
                HslComponent.lightness(lightness));
    }

    /**
     * Creates a {@link Hsv} holding the equivalent color, ignoring any {@link AlphaColorComponent}.
     */
    final public Hsv toHsv() {
        final float red = this.red.floatValue;
        final float green = this.green.floatValue;
        final float blue = this.blue.floatValue;

        final float max = Color.max(red, green, blue);
        final float min = Color.min(red, green, blue);

        final float value = max;
        float saturation = 0.0f;
        if (max != 0) {
            saturation = ((max - min)) / max;
        }
        float hue = 0;
        if (saturation != 0) {
            final float delta = max - min;
            final float maxRed = max - red;
            final float maxGreen = max - green;
            final float maxBlue = max - blue;

            if (red == max) {
                hue = ((maxBlue) - (maxGreen)) / delta;
            } else if (green == max) {
                hue = 2.0f + (((maxRed) - (maxBlue)) / delta);
            } else if (blue == max) {
                hue = 4.0f + (((maxGreen) - (maxRed)) / delta);
            }

            hue *= 60.0f;
            while (hue < 0.0f) {
                hue += HueHsvComponent.MAX;
            }
            while (hue >= HueHsvComponent.MAX) {
                hue -= HueHsvComponent.MAX;
            }
        }

        return Hsv.with(
                HsvComponent.hue(hue),
                HsvComponent.saturation(saturation),
                HsvComponent.value(value));
    }

    /**
     * Returns the max of 3 floats.
     */
    private static float max(final float a, final float b, final float c) {
        return Math.max(a, Math.max(b, c));
    }

    /**
     * Returns the min of 3 floats.
     */
    private static float min(final float a, final float b, final float c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * Lazily calculates the hash code and stores it for future retrieval.
     */
    @Override
    final public int hashCode() {
        return this.value();
    }

    /**
     * Performs some simple checks for nullness, identity and type using {@link #canBeEqual(Object)} before invoking
     * {@link #equals0(Color)} if types are compatible but different instances.
     */
    @Override
    final public boolean equals(final Object other) {
        return this == other ||
               this.canBeEqual(other) &&
               this.equals0(Cast.to(other));
    }

    /**
     * Tests if the argument to {@link #equals(Object)} is compatible for purposes of equality.Note the parameter may be null thus the best testing
     * includes an instance of X test.
     */
    abstract boolean canBeEqual(final Object other);

    /**
     * {@link Color colors} are equal if their values are the same.
     */
    private boolean equals0(final Color other) {
        return this.value() == other.value();
    }

    @Override
    final public String toString() {
        return ToStringBuilder.buildFrom(this);
    }

    @Override
    final public void buildToString(final ToStringBuilder builder) {
        builder.disable(ToStringBuilderOption.ESCAPE);
        builder.disable(ToStringBuilderOption.QUOTE);
        builder.disable(ToStringBuilderOption.SKIP_IF_DEFAULT_VALUE);
        builder.label("#");
        builder.labelSeparator("");
        builder.separator("");

        this.buildColorComponentsToString(builder);
    }

    abstract void buildColorComponentsToString(ToStringBuilder builder);

    final void addRedGreenBlueComponents(final ToStringBuilder builder) {
        builder.value(this.red);
        builder.value(this.green);
        builder.value(this.blue);
    }

    // Serializable
    private static final long serialVersionUID = 1;
}