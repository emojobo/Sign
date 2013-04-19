package me.emojobo.plugins.signs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Signs extends JavaPlugin implements Listener
{
    public void onDisable()
    {
        // TODO: Place any custom disable code here.
    }

    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
    }
    private static final char COLOR_CHAR = '\u00a7';

    @EventHandler
    public void signChange(SignChangeEvent event)
    {
        for (int signLineIndex = 0; signLineIndex < event.getLines().length; signLineIndex++)
        {
            final String signLineText = event.getLine(signLineIndex);
            final StringBuilder convertedLineText = new StringBuilder(15);
            if (signLineText.length() > 0)
            {
                for (int i = 0; i < signLineText.length() - 1 && i < 15; i++)
                {
                    if (signLineText.charAt(i) == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(signLineText.charAt(i + 1)) > -1)
                    {
                        // color code was detected
                        final char colorCode = Character.toLowerCase(signLineText.charAt(i + 1));
                        if ("0123456789abcdef".indexOf(colorCode) > -1 && event.getPlayer().hasPermission("signcolors.color." + colorCode))
                        {
                            // they have permissions for the color
                            convertedLineText.append(COLOR_CHAR);
                        }
                        else if ("klmnor".indexOf(colorCode) > -1 && event.getPlayer().hasPermission("signcolors.style." + colorCode))
                        {
                            // they have permissions for the style
                            convertedLineText.append(COLOR_CHAR);
                        }
                        else if ("hurka".indexOf(colorCode) > -1 && event.getPlayer().hasPermission("" + colorCode))
                        {

                        }
                        else {
                            convertedLineText.append(signLineText.charAt(i));
                        }
                    }
                    else
                    {
                        convertedLineText.append(signLineText.charAt(i));
                    }
                }
            }
            event.setLine(signLineIndex, convertedLineText.toString());
        }
    }
}