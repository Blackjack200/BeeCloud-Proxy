package net.fap.beecloud.utils;

import net.fap.beecloud.BeeCloud;
import net.fap.beecloud.console.ServerLogger;
import net.fap.beecloud.network.mcpe.protocol.DisconnectPacket;
import net.fap.beecloud.plugin.PluginBase;
import net.fap.beecloud.plugin.PluginCase;
import sun.misc.Signal;
import sun.misc.SignalHandler;

public class Shutdown {

    public static void shutdownTask()
    {
        Signal sg = new Signal("TERM"); // kill -15 pid
        Signal.handle(sg, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                System.exit(0);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                ServerLogger.info("- Disabling plugins... -");
                for (PluginBase plugin : PluginBase.pluginList)
                {
                    ServerLogger.info("关闭插件: "+plugin.getName());
                    PluginBase.pluginList.remove(plugin);
                }
                DisconnectPacket packet = new DisconnectPacket();
                BeeCloud.server.send(packet);
                ServerLogger.info("- Closing your BeeCloud server... -");
            }
        });
    }

    private static void mockRuntimeException() {
        throw new RuntimeException("This is a mock runtime ex");
    }

}
