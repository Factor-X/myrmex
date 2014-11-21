/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.factorx.myrmex.kernel.itests;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;

import java.io.File;
import java.util.Collection;

import javax.inject.Inject;

import eu.factorx.myrmex.kernel.model.TaskService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.osgi.framework.BundleContext;

@RunWith(PaxExam.class)
public class KarafTest {
    @Inject
    protected BundleContext bundleContext;

    @Inject
    protected TaskService taskService;
    
    public File getConfigFile(String path) {
        return new File(this.getClass().getResource(path).getFile());
    }

    @Configuration
    public Option[] config() {
        MavenArtifactUrlReference karafUrl = maven()
            .groupId("org.apache.karaf")
            .artifactId("apache-karaf")
            .version("3.0.0").type("tar.gz");
        MavenArtifactUrlReference tasklistRepo = maven()
            .groupId("eu.factorx.myrmex")
            .artifactId("kernel-features")
            .versionAsInProject()
            .type("xml");
        return new Option[] {
            karafDistributionConfiguration().frameworkUrl(karafUrl).name("Apache Karaf")
                .unpackDirectory(new File("target/exam")),
            keepRuntimeFolder(),
            features(tasklistRepo, "example-kernel-persistence", "example-kernel-ui")
            // KarafDistributionOption.debugConfiguration("5005", true),
        };
    }
    
    @Test
    public void test1() throws Exception {
        Collection<eu.factorx.myrmex.kernel.model.Task> tasks = taskService.getTasks();
        Assert.assertEquals(2, tasks.size());
    }

}
