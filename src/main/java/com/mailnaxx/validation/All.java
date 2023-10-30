package com.mailnaxx.validation;

import javax.validation.GroupSequence;

@GroupSequence({ ValidGroup1.class, CreateUser.class, ValidGroup2.class, ValidGroup3.class })
public interface All {

}
