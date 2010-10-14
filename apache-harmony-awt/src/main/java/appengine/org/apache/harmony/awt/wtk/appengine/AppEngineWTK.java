package org.apache.harmony.awt.wtk.appengine;

import org.apache.harmony.awt.gl.appengine.AppEngineGraphics2DFactory;
import org.apache.harmony.awt.wtk.CursorFactory;
import org.apache.harmony.awt.wtk.GraphicsFactory;
import org.apache.harmony.awt.wtk.NativeEventQueue;
import org.apache.harmony.awt.wtk.NativeIM;
import org.apache.harmony.awt.wtk.NativeMouseInfo;
import org.apache.harmony.awt.wtk.NativeRobot;
import org.apache.harmony.awt.wtk.SystemProperties;
import org.apache.harmony.awt.wtk.WTK;
import org.apache.harmony.awt.wtk.WindowFactory;

import com.google.code.appengine.awt.GraphicsDevice;


public class AppEngineWTK extends WTK
{
//    private final JavaSystemProperties systemProperties = new JavaSystemProperties();
    private final AppEngineEventQueue eventQueue = new AppEngineEventQueue();
    private final GraphicsFactory graphicsFactory = new AppEngineGraphics2DFactory();
//    private final CursorFactory cursorFactory = new WinCursorFactory(eventQueue);
//    private final NativeMouseInfo mouseInfo = new WinMouseInfo();
//    private WinRobot robot;
//    private WinIM im;
    
	@Override
	public CursorFactory getCursorFactory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphicsFactory getGraphicsFactory()
	{
		return graphicsFactory;
	}

	@Override
	public boolean getLockingState(int keyCode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NativeEventQueue getNativeEventQueue()
	{
		return eventQueue;
	}

	@Override
	public NativeIM getNativeIM()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeMouseInfo getNativeMouseInfo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NativeRobot getNativeRobot(GraphicsDevice screen)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SystemProperties getSystemProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WindowFactory getWindowFactory()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLockingState(int keyCode, boolean on)
	{
		// TODO Auto-generated method stub
		
	}
}
