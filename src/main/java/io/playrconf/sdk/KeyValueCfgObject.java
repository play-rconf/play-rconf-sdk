/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 The Play Remote Configuration Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.playrconf.sdk;

/**
 * Represents a simple key / value object. Key must be a simple
 * string and value could be a string representing anything (ie:
 * [1,2,3]).
 *
 * @author Thibault Meyer
 * @version 13.03.31
 */
public final class KeyValueCfgObject {

    /**
     * The configuration key.
     */
    private final String key;

    /**
     * The configuration value.
     */
    private Object value;

    /**
     * Build a new instance.
     *
     * @param key   The configuration key
     * @param value The configuration value
     */
    public KeyValueCfgObject(final String key, final String value) {
        this.key = key.trim();
        final String cleanedValue = value.trim();

        if (!cleanedValue.startsWith("\"")
            && !cleanedValue.startsWith("[")
            && !cleanedValue.startsWith("{")
            && cleanedValue.compareToIgnoreCase("null") != 0) {

            // Check if value is a boolean
            if (cleanedValue.compareToIgnoreCase("true") == 0
                || cleanedValue.compareToIgnoreCase("false") == 0) {
                this.value = Boolean.parseBoolean(cleanedValue);
            } else {
                try {

                    // Check if value is a number
                    this.value = Long.parseLong(cleanedValue);
                } catch (final NumberFormatException ignore) {
                    try {
                        this.value = Double.parseDouble(cleanedValue);
                    } catch (final NumberFormatException ignore2) {

                        // Fallback to a quoted value
                        this.value = "\"" + cleanedValue + "\"";
                    }
                }
            }
        } else {
            this.value = cleanedValue;
        }
    }

    /**
     * Tries to add this configuration object into
     * the application configuration.
     *
     * @param appConfig The application configuration content
     */
    public void apply(final StringBuilder appConfig) {
        appConfig
            .append(this.key)
            .append(" = ")
            .append(this.value == null ? "null" : this.value)
            .append("\n");
    }

    @Override
    public String toString() {
        return KeyValueCfgObject.class.getName()
            + "["
            + this.key
            + " <- "
            + (this.value == null ? "null" : this.value)
            + "]";
    }
}
