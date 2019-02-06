package com.pontusvision.jpostal;

import java.io.*;
import java.lang.reflect.Field;
import java.util.logging.Logger;

public class NativeLoader
{

  public static final Logger LOG = Logger.getLogger(NativeLoader.class.getName());

  public NativeLoader()
  {
  }

  public static void loadLibrary(String library)
  {
    try
    {
      LOG.info("Loading Library " + library);
      System.load(saveLibrary(library));
      LOG.info("Loaded Library " + library);

    }
    catch (IOException e)
    {
      LOG.warning(
          "Could not find library " + library + " as resource, trying fallback lookup through System.loadLibrary");
      System.loadLibrary(getOSSpecificLibraryName(library,false,false));
    }
  }

  private static String getOSSpecificLibraryName(String library, boolean includePath,boolean includeSuffix)
  {
    String osArch = System.getProperty("os.arch");
    String osName = System.getProperty("os.name").toLowerCase();
    String name;
    String path;

    if (osName.startsWith("win"))
    {
      if (osArch.equalsIgnoreCase("x86") || osArch.equalsIgnoreCase("amd64"))
      {
        name = "lib"+library +"-1"+(includeSuffix?".dll":"");
        path = "win-"+osArch.toLowerCase() + "/";
      }
      else
      {
        throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
      }
    }
    else if (osName.startsWith("linux"))
    {
      if (osArch.equalsIgnoreCase("amd64"))
      {
        name = "lib" + library +(includeSuffix?".so":"");
        path = "linux-x86_64/";
      }
      else if (osArch.equalsIgnoreCase("ia64"))
      {
        name = "lib" + library + (includeSuffix?".so":"");
        path = "linux-ia64/";
      }
      else if (osArch.equalsIgnoreCase("i386"))
      {
        name = "lib" + library + (includeSuffix?".so":"");
        path = "linux-x86/";
      }
      else
      {
        throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
      }
    }
    else
    {
      throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
    }

    return includePath ? path + name : name;
  }

  private static boolean updatedPath = false;
  private synchronized static void updateJavaLibraryPath(String tempDirPath) {
    if (!updatedPath)
    {
      String originalJavaLibraryPath = System.getProperty("java.library.path");
      System.setProperty("java.library.path", tempDirPath + ";" + originalJavaLibraryPath);
      // force reload of sys_path
      try
      {
        final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set(null, null);
      }
      catch (NoSuchFieldException | IllegalAccessException e)
      {
        System.out.println("unexpected exception while updating java.library.path");
      }
      updatedPath = true;
    }
  }

  private static String saveLibrary(String library) throws IOException
  {
    InputStream in = null;
    OutputStream out = null;

    try
    {
      String libraryName = getOSSpecificLibraryName(library, true,true);
      in = NativeLoader.class.getClassLoader().getResourceAsStream("lib/" + libraryName);
      String tmpDirName = System.getProperty("java.io.tmpdir");
      File tmpDir = new File(tmpDirName);
      updateJavaLibraryPath(tmpDirName);
      if (!tmpDir.exists())
      {
        tmpDir.mkdir();
      }

      File file = new File(tmpDir,getOSSpecificLibraryName(library ,false,true));
      // Clean up the file when exiting
      file.deleteOnExit();
      out = new FileOutputStream(file);

      int cnt;
      byte buf[] = new byte[16 * 1024];
      // copy until done.
      while ((cnt = in.read(buf)) >= 1)
      {
        out.write(buf, 0, cnt);
      }
      LOG.info("Saved libfile: " + file.getAbsoluteFile());
      return file.getAbsolutePath();
    }
    finally
    {
      if (in != null)
      {
        try
        {
          in.close();
        }
        catch (IOException ignore)
        {
        }
      }
      if (out != null)
      {
        try
        {
          out.close();
        }
        catch (IOException ignore)
        {
        }
      }
    }
  }
}
