package org.example

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class BuildLogicFunctionalTest extends Specification {

    @Rule
    TemporaryFolder testProjectDir = new TemporaryFolder()
    File rootBuildFile
    File subBuildFile

    def setup() {
        testProjectDir.newFile('settings.gradle') << ""
        def folder = testProjectDir.newFolder("abc")
        subBuildFile = new File(folder.getAbsolutePath() + '/build.gradle')
        rootBuildFile = testProjectDir.newFile('build.gradle')
        subBuildFile << """
            plugins {
                id 'java'
            }
        """
    }

    // tag::functional-test-configuration-cache[]
    def "my task can be loaded from the configuration cache"() {
        given:
        rootBuildFile << """
            plugins {
                id 'org.example.my-plugin'
            }
        """

        when:
        runner()
            .withArguments('--configuration-cache', 'ON')    // <1>
            .build()

        and:
        def result = runner()
            .withArguments('--configuration-cache', 'ON')    // <2>
            .build()

        then:
        result.output.contains('Reusing configuration cache.')      // <3>
        // ... more assertions on your task behavior
    }
    // end::functional-test-configuration-cache[]

    def runner() {
        return GradleRunner.create()
            .withProjectDir(testProjectDir.root)
            .withPluginClasspath()
    }
}
