# Gradle End to End Test
```java
    def runner() {
        return GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withPluginClasspath()
    }
```

The above code does the trick. **java-gradle-plugin** actually generate the class path info, and withPluginClasspath
method make use of that information.

# Gradle Configuration Cache
**gradle --configuration-cache**, this options is very useful when:
1. Many end to end test cases that share the same configuration
2. When you run gradle task again and again without the change of configuration
