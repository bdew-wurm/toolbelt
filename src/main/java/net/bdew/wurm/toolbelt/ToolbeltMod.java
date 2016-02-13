package net.bdew.wurm.toolbelt;

import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmMod;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ToolbeltMod implements WurmMod, Initable, PreInitable {
    private static Logger logger = Logger.getLogger("ToolbeltMod");

    @Override
    public void init() {
        logger.fine("Initializing");

        try {
            HookManager.getInstance().registerHook("com.wurmonline.client.renderer.gui.ToolBeltComponent", "isAvailable", "()Z", () ->
                    (proxy, method, args) -> true
            );

            HookManager.getInstance().registerHook("com.wurmonline.client.renderer.gui.ToolBeltComponent", "setQl", "(I)V", () ->
                    (proxy, method, args) -> {
                        args[0] = 99;
                        method.invoke(proxy, args);
                        return null;
                    }
            );

            HookManager.getInstance().registerHook("com.wurmonline.client.renderer.gui.HeadsUpDisplay", "init", "(II)V", () ->
                    (proxy, method, args) -> {
                        method.invoke(proxy, args);
                        HeadsUpDisplay hud = (HeadsUpDisplay) proxy;
                        hud.setToolbeltQl(99);
                        return null;
                    }
            );

            logger.fine("Loaded");
        } catch (Throwable e) {
            logger.log(Level.SEVERE, "Error loading mod", e);
        }
    }

    @Override
    public void preInit() {

    }
}
