package net.hypercubemc.runtimetransformer;

import com.sun.tools.attach.VirtualMachine;
import net.hypercubemc.runtimetransformer.agent.Agent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TransformerUtils {

    static void attachAgent(File agentFile, Class<?>[] transformers) {
        try {
            String pid = ManagementFactory.getRuntimeMXBean().getName();
            VirtualMachine vm = VirtualMachine.attach(pid.substring(0, pid.indexOf('@')));
            vm.loadAgent(agentFile.getAbsolutePath());
            vm.detach();

            Agent.getInstance().process(transformers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static File saveAgentJar() {
        try (InputStream is = RuntimeTransformer.class.getResourceAsStream("/agent.jar")) {
            File agentFile = File.createTempFile("agent", ".jar");
            agentFile.deleteOnExit();

            Files.copy(is, agentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return agentFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
