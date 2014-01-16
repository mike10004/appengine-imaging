package org.apache.harmony.awt.wtk.appengine;

import org.apache.harmony.awt.gl.appengine.AppEngineGraphics2DFactory;
import org.apache.harmony.awt.wtk.CursorFactory;
import org.apache.harmony.awt.wtk.GraphicsFactory;
import org.apache.harmony.awt.wtk.NativeEventQueue;
//import org.apache.harmony.awt.wtk.NativeIM;
import org.apache.harmony.awt.wtk.NativeMouseInfo;
import org.apache.harmony.awt.wtk.NativeRobot;
import org.apache.harmony.awt.wtk.SystemProperties;
import org.apache.harmony.awt.wtk.WTK;
import org.apache.harmony.awt.wtk.WindowFactory;

import com.google.code.appengine.awt.GraphicsDevice;


public class AppEngineWTK extends WTK
{
    private final AppEngineEventQueue eventQueue = new AppEngineEventQueue();
    private final GraphicsFactory graphicsFactory = new AppEngineGraphics2DFactory();
    
	@Override
	public CursorFactory getCursorFactory()
	{
		return null;
	}

	@Override
	public GraphicsFactory getGraphicsFactory()
	{
		return graphicsFactory;
	}

	@Override
	public NativeEventQueue getNativeEventQueue()
	{
		return eventQueue;
	}

//	@Override
//	public NativeIM getNativeIM()
//	{
//		return null;
//	}

	@Override
	public NativeMouseInfo getNativeMouseInfo()
	{
		return null;
	}

	@Override
	public NativeRobot getNativeRobot(GraphicsDevice screen)
	{
		return null;
	}

	@Override
	public SystemProperties getSystemProperties()
	{
		return null;
	}

	@Override
	public WindowFactory getWindowFactory()
	{
		return null;
	}

	@Override
	public void setLockingState(int keyCode, boolean on)
	{
	}

	@Override
	public boolean getLockingState(int keyCode)
	{
		return false;
	}
}
