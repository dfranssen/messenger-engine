messenger-engine
================
![](https://img.shields.io/shippable/56609a0f1895ca44744bd9e5.svg)

A Java 8 messenger toolkit for sending messages. Currrently only SMS is supported to be sent via a third-party MMP server ([Mobistar](https://business.mobistar.be/nl/professionals/business-oplossingen/opties-en-diensten/communiceer-met-uw-correspondenten/sms-api-toolkit/) by default).

In order to integrate (with Maven) the messenger engine in your own project:
```
    <dependency>
        <groupId>com.ditavision</groupId>
        <artifactId>messenger-engine</artifactId>
        <version>LATEST_VERSION</version>
        <scope>compile</scope>
    </dependency>
```

Alternatively this engine is also used in the projects:
* [messenger-cli](https://github.com/dfranssen/messenger-cli): a standalone [CLI](https://nl.wikipedia.org/wiki/Command-line-interface)
* a microservice: TODO link to messenger-microservice