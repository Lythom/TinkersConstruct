package slimeknights.tconstruct.world.client;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.Util;

public class SlimeColorizer implements IResourceManagerReloadListener {

  private static final ResourceLocation LOC_SLIME_BLUE_PNG = Util.getResource("textures/colormap/slimegrasscolor.png");
  private static final ResourceLocation LOC_SLIME_PURPLE_PNG =
      Util.getResource("textures/colormap/purplegrasscolor.png");
  private static final ResourceLocation LOC_SLIME_ORANGE_PNG =
      Util.getResource("textures/colormap/orangegrasscolor.png");

  private static int[] colorBufferBlue = new int[65536];
  private static int[] colorBufferPurple = new int[65536];
  private static int[] colorBufferOrange = new int[65536];

  private static float loop = 256; // after how many blocks the pattern loops

  public static int getColorBlue(int x, int z) {
    return getColor(x,z, colorBufferBlue);
  }

  public static int getColorPurple(int x, int z) {
    return getColor(x,z, colorBufferPurple);
  }

  public static int getColorOrange(int x, int z) {
    return getColor(x,z, colorBufferOrange);
  }

  private static int getColor(int posX, int posZ, int[] buffer) {
    float x = Math.abs((loop - (Math.abs(posX)%(2*loop)))/loop);
    float z = Math.abs((loop - (Math.abs(posZ)%(2*loop)))/loop);

    if(x < z) {
      float tmp = x;
      x = z;
      z = tmp;
    }

    return buffer[(int) (x * 255f) << 8 | (int) (z * 255f)];
  }

  @Override
  public void onResourceManagerReload(IResourceManager resourceManager) {
    try {
      colorBufferBlue = TextureUtil.readImageData(resourceManager, LOC_SLIME_BLUE_PNG);
      colorBufferPurple = TextureUtil.readImageData(resourceManager, LOC_SLIME_PURPLE_PNG);
      colorBufferOrange = TextureUtil.readImageData(resourceManager, LOC_SLIME_ORANGE_PNG);
    } catch(IOException e) {
      TConstruct.log.error(e);
    }
  }
}