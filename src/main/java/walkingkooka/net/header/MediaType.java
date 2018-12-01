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

package walkingkooka.net.header;

import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.collect.map.Maps;
import walkingkooka.net.HasQFactorWeight;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CharSequences;
import walkingkooka.text.Whitespace;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * A {@link Value} that represents a MIME Type with possible optional parameters.
 * Note parameter order is not important when comparing for equality or calculating the hash code.
 * Note any suffix that may be present in the sub type is not validated in anyway except for valid characters.
 * <a href="https://en.wikipedia.org/wiki/Media_type"></a>
 */
final public class MediaType implements HeaderValue,
        Value<String>,
        HasQFactorWeight,
        Serializable {

    /**
     * Wildcard
     */
    public final static String WILDCARD = "*";

    /**
     * The separator character that separates the type and secondary portions within a mime type {@link String}.
     */
    public final static char TYPE_SUBTYPE_SEPARATOR = '/';

    /**
     * The separator between parameter name and value.
     */
    public final static char PARAMETER_NAME_VALUE_SEPARATOR = '=';

    /**
     * The separator character that separates parameters from the main mime type
     */
    public final static char PARAMETER_SEPARATOR = ';';

    /**
     * The separator character that separates header types within a header value.
     */
    public final static char MEDIATYPE_SEPARATOR = ',';

    /**
     * No parameters.
     */
    public final static Map<MediaTypeParameterName, String> NO_PARAMETERS = Maps.empty();

    // MediaType constants.................................................................................................

    /**
     * Holds all constants.
     */
    private final static Map<String, MediaType> CONSTANTS = Maps.sorted();

    /**
     * Holds a {@link MediaType} that matches all {@link MediaType text types}.
     */
    public final static MediaType WILDCARD_WILDCARD = registerConstant(WILDCARD, WILDCARD);

    /**
     * Holds a {@link MediaType} for binary.
     */
    public final static MediaType BINARY = registerConstant("application", "octet-stream");

    /**
     * Holds a {@link MediaType} that matches all {@link MediaType text types}.
     */
    public final static MediaType ANY_TEXT = registerConstant("text", WILDCARD);

    /**
     * Holds a {@link MediaType} for plain text.
     */
    public final static MediaType TEXT_PLAIN = registerConstant("text", "plain");

    /**
     * Holds a {@link MediaType} for text/richtext
     */
    public final static MediaType TEXT_RICHTEXT = registerConstant("text", "richtext");

    /**
     * Holds a {@link MediaType} for HTML text/html
     */
    public final static MediaType TEXT_HTML = registerConstant("text", "html");

    /**
     * Holds a {@link MediaType} for XML text/xml
     */
    public final static MediaType TEXT_XML = registerConstant("text", "xml");

    /**
     * Holds a {@link MediaType} for MIME MULTIPART FORM DATA that contains <code>multipart/form-data</code>.
     */
    public final static MediaType MIME_MULTIPART_FORM_DATA = registerConstant("multipart", "form-data");

    /**
     * Holds a {@link MediaType} that matches all {@link MediaType image types}.
     */
    public final static MediaType ANY_IMAGE = registerConstant("image", WILDCARD);

    /**
     * Holds a {@link MediaType} that contains <code>image/bmp</code>
     */
    public final static MediaType IMAGE_BMP = registerConstant("image", "bmp");

    /**
     * Holds a {@link MediaType} that contains <code>image/gif</code>
     */
    public final static MediaType IMAGE_GIF = registerConstant("image", "gif");

    /**
     * Holds a {@link MediaType} that contains <code>image/jpeg</code>
     */
    public final static MediaType IMAGE_JPEG = registerConstant("image", "jpeg");

    /**
     * Holds a {@link MediaType} that contains <code>image/vnd.microsoft.icon</code>
     */
    public final static MediaType IMAGE_MICROSOFT_ICON = registerConstant("image", "vnd.microsoft.icon");
    /**
     * Holds a {@link MediaType} that contains <code>image/png</code>
     */
    public final static MediaType IMAGE_PNG = registerConstant("image", "png");

    /**
     * Holds a {@link MediaType} that contains <code>image/text</code>
     */
    public final static MediaType IMAGE_TEXT = registerConstant("image", "text");

    /**
     * Holds a {@link MediaType} that contains <code>image/x-bmp</code>
     */
    public final static MediaType IMAGE_XBMP = registerConstant("image", "x-bmp");
    /**
     * Holds a {@link MediaType} that contains PDF <code>application/pdf</code>
     */
    public final static MediaType APPLICATION_PDF = registerConstant("application", "pdf");

    /**
     * Holds a {@link MediaType} that contains ZIP <code>application/zip</code>
     */
    public final static MediaType APPLICATION_ZIP = registerConstant("application", "zip");

    /**
     * Holds a {@link MediaType} that contains EXCEL <code>application/ms-excel</code>
     */
    public final static MediaType APPLICATION_MICROSOFT_EXCEL = registerConstant("application", "ms-excel");

    /**
     * Holds a {@link MediaType} that contains EXCEL <code>application/vnd.openxmlformats-officedocument.spreadsheetml.sheet</code>
     */
    public final static MediaType APPLICATION_MICROSOFT_EXCEL_XML = registerConstant(
            "application",
            "vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    /**
     * Holds a {@link MediaType} that contains POWERPOINT <code>application/ms-powerpoint</code>
     */
    public final static MediaType APPLICATION_MICROSOFT_POWERPOINT = registerConstant("application", "ms-powerpoint");

    /**
     * Holds a {@link MediaType} that contains WORD <code>application/ms-word</code>
     */
    public final static MediaType APPLICATION_MICROSOFT_WORD = registerConstant("application", "ms-word");

    /**
     * Holds a {@link MediaType} that contains OUTLOOK <code>application/vnd.ms-outlook</code>
     */
    public final static MediaType APPLICATION_MICROSOFT_OUTLOOK = registerConstant("application", "vnd.ms-outlook");

    /**
     * Holds a {@link MediaType} that contains JAVASCRIPT <code>application/javascript</code>
     */
    public final static MediaType APPLICATION_JAVASCRIPT = registerConstant("application", "javascript");

    /**
     * Creates and then registers the constant.
     */
    static private MediaType registerConstant(final String type, final String subType) {
        final String toString = type + TYPE_SUBTYPE_SEPARATOR + subType;
        final MediaType mimeType = new MediaType(type, subType, NO_PARAMETERS, toString);
        CONSTANTS.put(toString, mimeType);

        return mimeType;
    }

    /**
     * Creates a {@link MediaType} breaking up the {@link String text} into type and sub types, ignoring any optional
     * parameters if they are present.
     */
    public static MediaType parse(final String text) {
        checkText(text);

        return MediaTypeParser.one(text);
    }


    /**
     * Creates a list of {@link MediaType}. If the text contains a single header type the results of this will be
     * identical to {@link #parse(String)} except the result will be in a list, which will also be
     * sorted by the q factor of each header type.
     */
    public static List<MediaType> parseList(final String text) {
        checkText(text);

        return MediaTypeParser.many(text);
    }

    private static void checkText(final String text) {
        Whitespace.failIfNullOrWhitespace(text, "text");
    }

    static boolean isTokenCharacter(final char c) {
        return TOKEN.test(c);
    }

    private final static CharPredicate TOKEN = CharPredicates.rfc2045Token();

    /**
     * Creates a {@link MediaType} using the already broken type and sub types. It is not possible to pass parameters with or without values.
     */
    public static MediaType with(final String type, final String subType) {
        checkType(type);
        checkSubType(subType);

        return new MediaType(type, subType, NO_PARAMETERS, type + TYPE_SUBTYPE_SEPARATOR + subType);
    }

    /**
     * Factory method called by various setters that tries to match constants before creating an actual new instance.
     */
    // @VisibleForTesting
    static MediaType with(final String type,
                          final String subType,
                          final Map<MediaTypeParameterName, String> parameters,
                          final String toString) {
        final MediaType result = CONSTANTS.get(type + TYPE_SUBTYPE_SEPARATOR + subType);
        return null != result ?
                result :
                new MediaType(type,
                        subType,
                        parameters,
                        toString);
    }

    /**
     * Recomputes the toString for a {@link MediaType} from its components.
     */
    private static String toStringMimeType(final String type,
                                           final String subType,
                                           final Map<MediaTypeParameterName, String> parameters) {
        return type + TYPE_SUBTYPE_SEPARATOR + subType + parameters.entrySet()
                .stream()
                .map(MediaType::toStringParameter)
                .collect(Collectors.joining());
    }

    private static String toStringParameter(final Entry<MediaTypeParameterName, String> nameAndValue) {
        return PARAMETER_SEPARATOR + " " + nameAndValue.getKey().value() + PARAMETER_NAME_VALUE_SEPARATOR + toStringParameterValue(nameAndValue.getValue());
    }

    /**
     * <a href="https://tools.ietf.org/html/rfc1341"></a>
     * <pre>
     * tspecials :=  "(" / ")" / "<" / ">" / "@"  ; Must be in
     *                        /  "," / ";" / ":" / "\" / <">  ; quoted-string,
     *                        /  "/" / "[" / "]" / "?" / "."  ; to use within
     *                        /  "="                        ; parameter values
     * </pre>
     * Backslashes and double quote characters are escaped.
     */
    private static String toStringParameterValue(final String value) {
        StringBuilder b = new StringBuilder();
        b.append('"');
        boolean quoteRequired = false;

        for (char c : value.toCharArray()) {
            if ('\\' == c || '"' == c) {
                b.append('\\');
                b.append(c);
                quoteRequired = true;
                continue;
            }
            if (!isTokenCharacter(c)) {
                b.append(c);
                quoteRequired = true;
                continue;
            }
            b.append(c);
        }

        return quoteRequired ?
                b.append('"').toString() :
                value;
    }

    /**
     * Formats or converts a list of media types back to a String. Basically
     * an inverse of {@link #parseList(String)}.
     */
    public static String format(final List<MediaType> mediaTypes) {
        Objects.requireNonNull(mediaTypes, "mediaTypes");

        return mediaTypes.stream()
                .map(m -> m.toString())
                .collect(Collectors.joining(TOSTRING_MEDIATYPE_SEPARATOR));
    }

    private final static String TOSTRING_MEDIATYPE_SEPARATOR = MEDIATYPE_SEPARATOR + " ";

    // ctor ...................................................................................................

    /**
     * Private constructor
     */
    private MediaType(final String type,
                      final String subType,
                      final Map<MediaTypeParameterName, String> parameters,
                      final String toString) {
        super();

        this.type = type;
        this.subType = subType;
        this.parameters = parameters;
        this.toString = toString;
    }

    // type .......................................................................................................

    /**
     * Getter that returns the primary component.
     */
    public String type() {
        return this.type;
    }

    /**
     * Would be setter that returns an instance with the new type, creating a new instance if required.
     */
    public MediaType setType(final String type) {
        checkType(type);
        return this.type.equals(type) ?
                this :
                this.replace(type, this.subType, this.parameters);
    }

    private final transient String type;

    static String checkType(final String type) {
        return check(type, "type");
    }

    // sub type ...................................................................................................

    /**
     * Getter that returns the sub component.
     */
    public String subType() {
        return this.subType;
    }

    /**
     * Would be setter that returns an instance with the new subType, creating a new instance if required.
     */
    public MediaType setSubType(final String subType) {
        checkSubType(subType);
        return this.subType.equals(subType) ?
                this :
                this.replace(this.type, subType, this.parameters);
    }

    private final transient String subType;

    static String checkSubType(final String subType) {
        return check(subType, "subType");
    }

    /**
     * Checks that the value contains valid token characters.
     */
    static String check(final String value, final String label) {
        CharPredicates.failIfNullOrEmptyOrFalse(value, label, TOKEN);
        return value;
    }

    /**
     * Builds a message to report an invalid or unexpected character.
     */
    static String invalidCharacter(final char c, final int i, final String text) {
        return "Invalid character " + CharSequences.quoteIfChars(c) + " at " + i + " in " + CharSequences.quoteAndEscape(text);
    }

    // parameters ...............................................................................................

    /**
     * Retrieves the parameters.
     */
    public Map<MediaTypeParameterName, String> parameters() {
        return this.parameters;
    }

    public MediaType setParameters(final Map<MediaTypeParameterName, String> parameters) {
        final Map<MediaTypeParameterName, String> copy = checkParameters(parameters);
        return this.parameters.equals(copy) ?
                this :
                this.replace(this.type, this.subType, copy);
    }

    /**
     * Package private for testing.
     */
    private transient final Map<MediaTypeParameterName, String> parameters;

    private static Map<MediaTypeParameterName, String> checkParameters(final Map<MediaTypeParameterName, String> parameters) {
        Objects.requireNonNull(parameters, "parameters");

        final Map<MediaTypeParameterName, String> copy = Maps.ordered();
        copy.putAll(parameters);
        return Maps.readOnly(copy);
    }

    // replace .......................................................................

    private MediaType replace(final String type, final String subType, final Map<MediaTypeParameterName, String> parameters) {
        return new MediaType(type,
                subType,
                parameters,
                toStringMimeType(type, subType, parameters));
    }

    // value ...................................................................

    @Override
    public String value() {
        return this.type + TYPE_SUBTYPE_SEPARATOR + this.subType;
    }

    // qWeight ...................................................................

    /**
     * Retrieves the q-weight for this value. If the value is not a number a {@link IllegalStateException} will be thrown.
     */
    public Optional<Float> qFactorWeight() {
        final String value = this.parameters().get(MediaTypeParameterName.Q_FACTOR);
        return Optional.ofNullable(null == value ?
                null :
                qFactorWeightOrFail(value));
    }

    private Float qFactorWeightOrFail(final String value) {
        try {
            return Float.parseFloat(value);
        } catch ( final IllegalArgumentException cause) {
            throw new IllegalStateException("Invalid q weight parameter " + value + " in " + this, cause);
        }
    }

    // misc .......................................................................

    /**
     * Tests if the given {@link MediaType} is compatible and understand wildcards that may appear in the type or sub type components. The
     * {@link MediaType#WILDCARD_WILDCARD} will of course be compatible with any other {@link MediaType}.
     */
    public boolean isCompatible(final MediaType mimeType) {
        Objects.requireNonNull(mimeType, "mimeType");

        boolean compatible = true;

        if (this != mimeType) {
            final String primary = this.type();
            if (false == WILDCARD.equals(primary)) {
                compatible = primary.equals(mimeType.type);
                if (compatible) {
                    final String subType = this.subType;
                    if (false == WILDCARD.equals(subType)) {
                        compatible = subType.equals(mimeType.subType);
                    }
                }
            }
        }

        return compatible;
    }

    // HeaderValue................................................................................................................

    @Override
    public String headerValue() {
        return this.toString();
    }

    // Object................................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.subType, this.parameters);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                other instanceof MediaType &&
                        this.equals0(Cast.to(other));
    }

    private boolean equals0(final MediaType other) {
        return this.type.equals(other.type) && //
                this.subType.equals(other.subType) && //
                this.parameters.equals(other.parameters);
    }

    /**
     * If sourced or created by parsing, the original text is returned, if built using setters a toString is constructed.
     */
    @Override
    public String toString() {
        return this.toString;
    }

    private final String toString;

    // Serializable............................................................................

    /**
     * Only the {@link #toString()} is serialized thus on deserialization we need to parse to reconstruct other fields.
     */
    private Object readResolve() {
        return MediaTypeParser.one(this.toString);
    }

    private final static long serialVersionUID = 1L;
}