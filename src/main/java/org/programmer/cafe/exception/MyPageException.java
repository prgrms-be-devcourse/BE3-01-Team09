package org.programmer.cafe.exception;

import org.programmer.cafe.domain.user.entity.MyPageStatus;

public class MyPageException extends RuntimeException {
    public final MyPageStatus myPageStatus;
    public MyPageException(MyPageStatus myPageStatus) {
        super(myPageStatus.getMessage());
        this.myPageStatus = myPageStatus;
  }
  public MyPageStatus getMyPageLoadStatus() {return myPageStatus;}
}
