package micdoodle8.mods.galacticraft.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.regex.Pattern;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapCoord;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapInfo;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GCCoreUtil
{
	public static int convertTo32BitColor(int a, int r, int b, int g)
	{
        a = a << 24;
        r = r << 16;
        g = g << 8;

        return a | r | g | b;
	}

	public static void checkVersion(Side side)
    {
    	try
    	{
    		final URL url = new URL("http://micdoodle8.com/galacticraft/version.html");

    		final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
    		Pattern.compile("Version=");
    		String str;
    		String str2[] = null;

    		while ((str = in.readLine()) != null)
    		{
	    		if (str.contains("Version"))
	    		{
	        		str = str.replace("Version=", "");

		    		str2 = str.split("#");

		    		if (str2 != null && str2.length == 3)
		    		{
		    			GalacticraftCore.remoteMajVer = Integer.parseInt(str2[0]);
		    			GalacticraftCore.remoteMinVer = Integer.parseInt(str2[1]);
		    			GalacticraftCore.remoteBuildVer = Integer.parseInt(str2[2]);
		    		}
		    		
		    		if (GalacticraftCore.remoteBuildVer != 0 && GalacticraftCore.remoteBuildVer < GalacticraftCore.LOCALBUILDVERSION)
		    		{
		    			GalacticraftCore.usingDevVersion = true;
		    		}

		    		if (GalacticraftCore.remoteMajVer > GalacticraftCore.LOCALMAJVERSION || GalacticraftCore.remoteMajVer == GalacticraftCore.LOCALMAJVERSION && GalacticraftCore.remoteMinVer > GalacticraftCore.LOCALMINVERSION || GalacticraftCore.remoteMajVer == GalacticraftCore.LOCALMAJVERSION && GalacticraftCore.remoteMinVer == GalacticraftCore.LOCALMINVERSION && GalacticraftCore.remoteBuildVer > GalacticraftCore.LOCALBUILDVERSION)
		    		{
		    			if (side.equals(Side.CLIENT))
		    			{
		    				FMLClientHandler.instance().getClient().thePlayer.addChatMessage("\u00a77New \u00a73Galacticraft \u00a77version available! v" + String.valueOf(GalacticraftCore.remoteMajVer) + "." + String.valueOf(GalacticraftCore.remoteMinVer) + "." + String.valueOf(GalacticraftCore.remoteBuildVer) + " \u00a71http://micdoodle8.com/");
		    			}
		    			else if (side.equals(Side.SERVER))
		    			{
		    				FMLLog.severe("New Galacticraft version available! v" + String.valueOf(GalacticraftCore.remoteMajVer) + "." + String.valueOf(GalacticraftCore.remoteMinVer) + "." + String.valueOf(GalacticraftCore.remoteBuildVer) + " http://micdoodle8.com/");
		    			}
		    		}
	    		}
    		}
    	}
    	catch (final MalformedURLException e)
    	{
    		e.printStackTrace();

			if (side.equals(Side.CLIENT))
			{
	    		FMLClientHandler.instance().getClient().thePlayer.addChatMessage("[Galacticraft] Update Check Failed!");
			}

    		FMLLog.severe("Galacticraft Update Check Failure - MalformedURLException");
    	}
    	catch (final IOException e)
    	{
    		e.printStackTrace();

			if (side.equals(Side.CLIENT))
			{
	    		FMLClientHandler.instance().getClient().thePlayer.addChatMessage("[Galacticraft] Update Check Failed!");
			}

    		FMLLog.severe("Galacticraft Update Check Failure - IOException");
    	}
    	catch (final NumberFormatException e)
    	{
    		e.printStackTrace();

			if (side.equals(Side.CLIENT))
			{
	    		FMLClientHandler.instance().getClient().thePlayer.addChatMessage("[Galacticraft] Update Check Failed!");
			}

    		FMLLog.severe("Galacticraft Update Check Failure - NumberFormatException");
    	}
    }
}
