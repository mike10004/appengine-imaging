package org.apache.harmony.awt.wtk.appengine;

import org.apache.harmony.awt.wtk.NativeEventQueue;

public class AppEngineEventQueue extends NativeEventQueue
{
	@Override
	public void awake()
	{
	}

	@Override
	public void dispatchEvent()
	{
	}

	@Override
	public long getJavaWindow()
	{
		return 0;
	}

	@Override
	public void performLater(Task task)
	{
		task.perform();
	}

	@Override
	public void performTask(Task task)
	{
		task.perform();
	}

	@Override
	public boolean waitEvent()
	{
		return false;
	}
}
