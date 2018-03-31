# Play Remote Configuration - SDK


[![Latest release](https://img.shields.io/badge/latest_release-18.04-orange.svg)](https://github.com/play-rconf/play-rconf-sdk/releases)
[![JitPack](https://jitpack.io/v/play-rconf/play-rconf-sdk.svg)](https://jitpack.io/#play-rconf/play-rconf-sdk)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/play-rconf/play-rconf-sdk/master/LICENSE)

SDK to implement new remote configuration providers
*****

## About this project
In production, it is not always easy to manage the configuration files of a
Play Framework application, especially when it running on multiple servers.
The purpose of this project is to provide a simple way to use a remote
configuration with a Play Framework application.



## How to implement you own provider

The implementation of a new provider is very simple. Just use this SDK and
create a class implementing the interface `Provider`.


```maven
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.play-rconf</groupId>
  <artifactId>play-rconf-sdk</artifactId>
  <version>release~YY.MM</version>
</dependency>
```

```java
public class MyCustomProvider extends AbstractProvider {

    @Override
    public String getName() {
        return "My Custom Provider";
    }

    @Override
    public String getVersion() {
        throw new NotImplementedException();
    }

    @Override
    public String getConfigurationObjectName() {
        return "custom-provider";
    }

    @Override
    public void loadData(final Config config,
                         final Consumer<KeyValueCfgObject> kvObjConsumer,
                         final Consumer<FileCfgObject> fileObjConsumer) throw RemoteConfException {
        throw new NotImplementedException();
    }
}
```



## License
This project is released under terms of the [MIT license](https://raw.githubusercontent.com/play-rconf/play-rconf-sdk/master/LICENSE).
