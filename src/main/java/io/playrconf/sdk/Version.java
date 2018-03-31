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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper to retrieve the SDK version.
 *
 * @author Thibault Meyer
 * @since 18.03.31
 */
public final class Version {

    /**
     * Contains the SDK version.
     */
    private static String sdkVersion;

    /**
     * Returns the SDK version (ie: 18.03-SNAPSHOT).
     *
     * @return The SDK version
     */
    public static String getVersion() {
        if (Version.sdkVersion == null) {
            synchronized (Version.class) {
                final Properties properties = new Properties();
                final InputStream is = Version.class.getClassLoader()
                    .getResourceAsStream("/playrconf-sdk.properties");
                try {
                    properties.load(is);
                    Version.sdkVersion = properties.getProperty("playrconf.sdk.version", "unknown");
                    properties.clear();
                    is.close();
                } catch (final IOException ignore) {
                }
            }
        }
        return Version.sdkVersion;
    }
}
