package io.tarek360.gcforum.gcm;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by tarek on 4/30/16.
 */
public class NotificationModel {

  private PublishSubject<Notification> subject = PublishSubject.create();

  public Observable<Notification> getNotification() {
    return subject;
  }

  public void setNotification(Notification notification) {
    subject.onNext(notification);
  }

  public boolean hasObservers() {
    return subject.hasObservers();
  }
}
