function checkdata()
{
	//var ssn = form.username.value.toLowerCase();
	//if(!checkUserName(ssn)) return false; //用户名检查
	if(form.username.value == "")
	{
		alert("\请输入用户名！")
		form.username.focus()
		return false;
	}
	
	if(form.password.value == "")
	{
		alert("\请输入密码！")
		form.password.focus()
		return false;
	}
	
	if(form.password2.value == "")
	{
		alert("\请输入确认密码！")
		form.password2.focus()
		return false;
	}
	
	if(form.phone.value == "")
	{
		alert("\请输入电话！")
		form.phone.focus()
		return false;
	}
	
	if(form.addr.value == "")
	{
		alert("\请输入送货地址！")
		form.addr.focus()
		return false;
	}
	
	if(form.password2.value != form.password.value)
	{
		alert("\两次密码输入不一致！")
		form.password.focus()
		return false;
	}
	
	if(strlen(form.password.value)<6 || strlen(form.password.value)>16)
	{
		alert("\正确地登录密码长度为 6-6 位，仅可用英文、数字、特殊字符！")
		form.password.focus()
		return false;
	}
	
	if(strlen2(form.password.value))
	{
		alert("\您的密码中包含了非法字符，仅可使用英文、数字、特殊字符！")
		form.password.focus()
		return false;
	}
	
	if(form.password.value == form.username.value)
	{
		alert("\用户名和密码不能相同！")
		form.password.focus()
		return false;
	}
}

function checkUserName(ssn)
{
	if( ssn.length < 3 || ssn.length > 18 )
	{
		alert("\请输入正确的用户名，长度为3-18位！")
		form.username.focus()
		return false;
	}
	
	if(isWhiteWpace(ssn))
	{
		alert("\请输入正确的用户名，用户名中不能包含空格！")
		form.username.focus()
		return false;
	}
	return true;
}

function isWhiteWpace(s)
{
    var whitespace = "\t\n\r";
    var i;
    for(i=0;i<s.length;i++)
    {
    	var c = s.charAt(i);
    	if(whitespace.indexof(c) >= 0)
		{
    		return true;
		}
    }
    return false;
}

function strlen(str)
{
	var len;
	var i;
	len = 0;
	for(i=0;i<str.length;i++)
	{
		if(str.charCodeAt(i)>255)
			len += 2;
		else
			len++;
	}
	return len;
}

function strlen2(str)
{
	var len;
	var i;
	len = 0;
	for(i=0;i<str.length;i++)
	{
		if(str.charCodeAt(i)>255)
			return true;
	}
	return false;
}