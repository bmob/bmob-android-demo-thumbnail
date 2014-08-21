package com.bmob.thumbnaildemo;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Person extends BmobObject {
	private BmobFile icon;

	public BmobFile getIcon() {
		return icon;
	}

	public void setIcon(BmobFile icon) {
		this.icon = icon;
	}
	
}
