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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import io.playrconf.sdk.exception.RemoteConfException;

import java.util.function.Consumer;

/**
 * @author Thibault Meyer
 * @since 18.03.31
 */
public interface Provider {

    /**
     * Retrieves the provider name.
     *
     * @return The provider name
     */
    String getName();

    /**
     * Retrieves the provider version.
     *
     * @return The provider version
     */
    String getVersion();

    /**
     * Retrieves the provider configuration object name.
     *
     * @return The configuration object name
     */
    String getConfigurationObjectName();

    /**
     * Retrieves data from the provider.
     *
     * @param config          The provider configuration
     * @param kvObjConsumer   The Key/Value object consumer
     * @param fileObjConsumer The File object consumer
     * @throws RemoteConfException If something goes wrong
     */
    void loadData(final Config config,
                  final Consumer<KeyValueCfgObject> kvObjConsumer,
                  final Consumer<FileCfgObject> fileObjConsumer) throws ConfigException, RemoteConfException;
}
